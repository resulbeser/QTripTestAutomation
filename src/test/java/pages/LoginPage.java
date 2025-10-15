package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Element locator'lar
    private By emailInput = By.id("floatingInput");
    private By passwordInput = By.id("floatingPassword");
    private By loginButton = By.xpath("//button[text()='Login to QTrip']");

    // Sayfa işlemleri
    public void enterEmail(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}