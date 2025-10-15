package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {
    private static WebDriver driver;
    private static Properties config;

    // Config dosyasını yükle
    static {
        config = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            config.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // WebDriver başlat
    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = config.getProperty("browser", "chrome");

            switch (browser.toLowerCase()) {
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                default:
                    driver = new ChromeDriver();
                    break;
            }

            driver.manage().window().maximize();
        }
        return driver;
    }

    // WebDriver kapat
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}