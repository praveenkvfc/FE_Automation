package utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Proxy;

import java.util.Arrays;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlaywrightFactory {
    private static final ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThread = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThread = new ThreadLocal<>();

    /**
     * Initialize Playwright + Browser + Context + Page for the given browser name.
     * Supported: "chrome" (recommended), "firefox", "webkit"
     *
     * Runtime overrides (no code change):
     *  - Chrome path:  JVM -Dchrome.path="C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"
     *                  ENV CHROME_PATH="/usr/bin/google-chrome"
     *  - Headless:     JVM -Dheadless=true
     *  - Web proxy:    ENV PLAYWRIGHT_WEB_PROXY="http://proxy.host:port"
     *                  JVM -Dplaywright.web.proxy="http://proxy.host:port"
     */
    public static void initBrowser(String browserName) {
        try {
            Playwright playwright = Playwright.create();
            playwrightThread.set(playwright);

            Browser browser;

            switch (browserName.toLowerCase()) {
                case "chrome":
                    browser = launchChrome(playwright);
                    break;

                case "firefox":
                    browser = playwright.firefox().launch(baseLaunchOptions());
                    break;

                case "webkit":
                    browser = playwright.webkit().launch(baseLaunchOptions());
                    break;

                default:
                    throw new RuntimeException("Unsupported browser: " + browserName);
            }

            browserThread.set(browser);

            // --- Context options ---
            Browser.NewContextOptions ctxOptions = new Browser.NewContextOptions()
                    .setViewportSize(null); // Use native window size; maximize via launch args

            // Optional: generic UA (avoid hard-coded Chrome version)
            ctxOptions.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome Safari/537.36");

            // Optional: set web proxy if provided
            String webProxy = getEnvOrProp("PLAYWRIGHT_WEB_PROXY", "playwright.web.proxy");
            if (webProxy != null && !webProxy.isBlank()) {
                ctxOptions.setProxy(new Proxy(webProxy));
            }

            BrowserContext context = browser.newContext(ctxOptions);

            // Remove webdriver flag to reduce detection
            context.addInitScript("Object.defineProperty(navigator, 'webdriver', { get: () => undefined })");

            contextThread.set(context);
            Page page = context.newPage();
            pageThread.set(page);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize browser: " + browserName, e);
        }
    }

    /**
     * Robust Chrome launcher:
     * 1) Try system Chrome via channel("chrome") — avoids downloads.
     * 2) Fallback to executablePath — from sysprop/env or sensible OS defaults.
     */
    private static Browser launchChrome(Playwright playwright) {
        BrowserType chromium = playwright.chromium();

        // First attempt: use Chrome channel
        BrowserType.LaunchOptions channelOptions = baseLaunchOptions().setChannel("chrome");
        try {
            return chromium.launch(channelOptions);
        } catch (Exception channelErr) {
            // If channel isn't available, fall back to explicit path
        }

        // Fallback: use explicit chrome executable path (Path, not String)
        String chromePathStr = resolveChromePath();
        Path chromePath = Paths.get(chromePathStr);

        BrowserType.LaunchOptions pathOptions = baseLaunchOptions().setExecutablePath(chromePath);
        return chromium.launch(pathOptions);
    }

    /**
     * Base launch options, built fresh each time.
     */
    private static BrowserType.LaunchOptions baseLaunchOptions() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        return new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setArgs(Arrays.asList(
                        "--start-maximized",
                        "--disable-blink-features=AutomationControlled"
                ));
    }

    /**
     * Resolve Chrome executable path in this order:
     * - JVM system property "chrome.path"
     * - ENV "CHROME_PATH"
     * - OS-specific common defaults
     */
    private static String resolveChromePath() {
        String configured = System.getProperty("chrome.path");
        if (configured != null && !configured.isBlank()) return configured;

        configured = System.getenv("CHROME_PATH");
        if (configured != null && !configured.isBlank()) return configured;

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            String[] candidates = new String[] {
                    "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                    "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"
            };
            for (String c : candidates) {
                if (new File(c).exists()) return c;
            }
            // Return the most common one; if it doesn't exist, Playwright will error and show the path
            return candidates[0];
        } else if (os.contains("mac")) {
            return "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";
        } else {
            // Linux
            String[] candidates = new String[] {
                    "/usr/bin/google-chrome",
                    "/usr/bin/chromium-browser",
                    "/snap/bin/chromium"
            };
            for (String c : candidates) {
                if (new File(c).exists()) return c;
            }
            return candidates[0];
        }
    }

    /**
     * Utility: read ENV first, then JVM system property.
     */
    private static String getEnvOrProp(String envName, String propName) {
        String val = System.getenv(envName);
        if (val == null || val.isBlank()) {
            val = System.getProperty(propName);
        }
        return val;
    }

    // --- Accessors for Hooks/Steps ---

    public static Page getPage() {
        Page page = pageThread.get();
        if (page == null) {
            throw new IllegalStateException("Page is not initialized. Call initBrowser first.");
        }
        return page;
    }

    public static BrowserContext getContext() {
        BrowserContext ctx = contextThread.get();
        if (ctx == null) {
            throw new IllegalStateException("Context is not initialized. Call initBrowser first.");
        }
        return ctx;
    }

    public static Browser getBrowser() {
        Browser br = browserThread.get();
        if (br == null) {
            throw new IllegalStateException("Browser is not initialized. Call initBrowser first.");
        }
        return br;
    }

    public static void close() {
        try {
            if (contextThread.get() != null) {
                contextThread.get().close();
            }
            if (browserThread.get() != null) {
                browserThread.get().close();
            }
            if (playwrightThread.get() != null) {
                playwrightThread.get().close();
            }
        } finally {
            pageThread.remove();
            contextThread.remove();
            browserThread.remove();
            playwrightThread.remove();
        }
    }
}