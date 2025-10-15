package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static Properties config;

    static {
        config = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            config.load(fis);
        } catch (IOException e) {
            System.out.println("Config dosyası yüklenemedi: " + e.getMessage());
        }
    }

    public static String getBaseUrl() {
        return config.getProperty("baseUrl");
    }

    public static String getBrowser() {
        return config.getProperty("browser", "chrome");
    }

    public static String getProperty(String key) {
        return config.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }
}