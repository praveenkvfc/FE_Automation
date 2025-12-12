package utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Proxy;

import java.util.Arrays;

public class PlaywrightFactory {
    private static final ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThread = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThread = new ThreadLocal<>();

    public static void initBrowser(String browserName) {
        try {
            Playwright playwright = Playwright.create();
            playwrightThread.set(playwright);

            Browser browser;
            BrowserContext context;
            Page page;

            switch (browserName.toLowerCase()) {
                case "chrome":
                    browser = playwright.chromium().launch(new
                            BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setArgs(Arrays.asList("--start-maximized",
                                    "--disable-blink-features=AutomationControlled"))
                            .setChannel("chrome"));
                    break;
                case "firefox":
                    browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                    break;
                case "webkit":
                    browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                    break;
                default:
                    throw new RuntimeException("Unsupported browser: "
                            + browserName);
            }

            browserThread.set(browser);

            context = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(null)
                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"));

            // Remove webdriver property
            context.addInitScript("Object.defineProperty(navigator, 'webdriver', { get: () => undefined })");

            contextThread.set(context);
            page = context.newPage();
            pageThread.set(page);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize browser: " + browserName, e);
        }
    }

    public static Page getPage() {
        Page page = pageThread.get();
        if (page == null) {
            throw new IllegalStateException("Page is not initialized. Call initBrowser first.");
        }
        return page;
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