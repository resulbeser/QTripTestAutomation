package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WaitHelper;

import java.time.Duration;


public class HeaderFragment {
    private WebDriver driver;

    public HeaderFragment(WebDriver driver) {
        this.driver = driver;
    }

    // Register link selector
    private By registerLink = By.cssSelector("#navbarNavDropdown > ul > li:nth-child(4) > a");

    public void clickRegister() {
        driver.findElement(registerLink).click();
    }

    public boolean isOnRegisterPage() {
        WaitHelper.getWait(driver).until(ExpectedConditions.urlContains("/pages/register/"));
        return driver.getCurrentUrl().contains("/pages/register/");
    }


}
