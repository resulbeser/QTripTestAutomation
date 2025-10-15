package utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {

    public static WebDriverWait getWait(WebDriver driver) {
        int timeout = Integer.parseInt(ConfigLoader.getProperty("timeout"));
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickability(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static Alert waitForAlert(WebDriver driver) {
        return getWait(driver).until(ExpectedConditions.alertIsPresent());
    }

    public static boolean waitForUrlContains(WebDriver driver, String partialUrl) {
        return getWait(driver).until(ExpectedConditions.urlContains(partialUrl));
    }
}