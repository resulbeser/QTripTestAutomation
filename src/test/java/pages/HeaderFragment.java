package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitHelper;

public class HeaderFragment {
    private final WebDriver driver;

    public HeaderFragment(WebDriver driver) {
        this.driver = driver;
    }

    private final By registerLink = By.linkText("Register");

    public void clickRegister() {
        WebElement element = WaitHelper.waitForClickability(driver, registerLink);
        element.click();
    }

    public void navigateToRegisterPage() {
        clickRegister();
        WaitHelper.waitForUrlContains(driver, "/pages/register");
    }

    public boolean isOnRegisterPage() {
        return driver.getCurrentUrl().contains("/pages/register");
    }
}