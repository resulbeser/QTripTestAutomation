package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WaitHelper;

public class RegisterPage {
    private final WebDriver driver;

    // Constructor
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By emailInput = By.id("floatingInput");
    private final By passwordInput = By.xpath("(//input[@id='floatingPassword'])[1]");
    private final By confirmPasswordInput = By.xpath("(//input[@id='floatingPassword'])[2]");
    private final By registerButton = By.cssSelector("#registerForm > div.d-grid > button");
    private final By errorMessageBox = By.className("alert");

    // Actions
    public void enterEmail(String email) {
        WaitHelper.waitForVisibility(driver, emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        WaitHelper.waitForVisibility(driver, passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        WaitHelper.waitForVisibility(driver, confirmPasswordInput).clear();
        driver.findElement(confirmPasswordInput).sendKeys(confirmPassword);
    }

    public void clickRegister() {
        WaitHelper.waitForClickability(driver, registerButton).click();
    }

    public void register(String email, String password) {
        register(email, password, password);
    }

    public void register(String email, String password, String confirmPassword) {
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        clickRegister();
    }

    public boolean isRegistrationSuccessful() {
        return WaitHelper.waitForUrlContains(driver, "/pages/login");
    }

    public String getErrorMessage() {
        try {
            Alert alert = WaitHelper.waitForAlert(driver);
            String text = alert.getText();
            alert.accept();
            return text;
        } catch (TimeoutException e) {
            try {
                return driver.findElement(errorMessageBox).getText();
            } catch (Exception ex) {
                return "";
            }
        }
    }

    public boolean isRegisterButtonEnabled() {
        return driver.findElement(registerButton).isEnabled();
    }

    public boolean isCurrentPage() {
        return driver.getCurrentUrl().contains("/pages/register");
    }
}