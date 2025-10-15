package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;

    // Constructor
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By emailInput = By.id("floatingInput");
    private By passwordInput = By.xpath("(//input[@id='floatingPassword'])[1]");
    private By confirmPasswordInput = By.xpath("(//input[@id='floatingPassword'])[2]");
    private By registerButton = By.cssSelector("#registerForm > div.d-grid > button");

    // Actions
    public void enterEmail(String email) {
        WaitHelper.getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }


    public void enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void confirmPassword(String password) {
        driver.findElement(confirmPasswordInput).clear();
        driver.findElement(confirmPasswordInput).sendKeys(password);
    }

    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    public void register(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        confirmPassword(password);
        clickRegister();
    }

    public boolean isRegistrationSuccessful() {
        WaitHelper.getWait(driver).until(ExpectedConditions.urlContains("/pages/login"));
        return driver.getCurrentUrl().contains("/pages/login");
    }


    private By errorMessage = By.className("alert"); // DOM'daki gerçek class neyse onu kullan

    public String getErrorMessage() {
        try {
            Alert alert = WaitHelper.getWait(driver).until(ExpectedConditions.alertIsPresent());
            String text = alert.getText();
            alert.accept(); // “Tamam”a basar
            return text;
        } catch (TimeoutException e) {
            return "";
        }
    }



    public boolean isRegisterButtonEnabled() {
        return driver.findElement(registerButton).isEnabled();
    }


}