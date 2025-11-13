package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class ConfigReader {
    private static final Map<String, String> config = new HashMap<>();

    public static void load(String env, String brand, String region, String tag) {

        System.out.println("==================================================");
        System.out.println("-------------Hey im in ConfigReader class----------");
        System.out.println("env : "+env);
        System.out.println("tag : "+tag);
        System.out.println("brand : "+brand);
        System.out.println("region : "+region);
        System.out.println("==================================================");

        String filePath = Paths.get(System.getProperty("user.dir"),
                "src", "test", "resources","config_files",env , brand, brand+".properties").toString();
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: "
                    + filePath, e);
        }

        // Load all key-value pairs from the properties file into config map
        for (String key : properties.stringPropertyNames()) {
            config.put(key, properties.getProperty(key));
        }

        // Optionally, store env, brand, and region
        config.put("env", env);
        config.put("brand", brand);
        config.put("region", region);
        config.put("tag", tag);

        // Special case: if region-specific value is present, store it with key `url`
        String regionUrl = properties.getProperty(region+"_url");
        String browser= properties.getProperty("browser");
        if (regionUrl != null) {
            config.put("url", regionUrl);
            System.out.println("URL:"+regionUrl);
        } else {
            throw new RuntimeException("Region '" + region + "' not found in " + filePath);
        }
    }

    public static String get(String key) {

        return config.get(key);
    }
}
