package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitHelper {
    public static WebDriverWait getWait(WebDriver driver) {
        int timeout = Integer.parseInt(ConfigLoader.getProperty("timeout"));
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }
}
