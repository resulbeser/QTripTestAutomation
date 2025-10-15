package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = ConfigLoader.getProperty("browser", "chrome");

            switch (browser.toLowerCase()) {
                case "firefox":
                    driver.set(new FirefoxDriver());
                    break;
                case "chrome":
                default:
                    driver.set(new ChromeDriver());
                    break;
            }

            driver.get().manage().window().maximize();
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}