package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HeaderFragment;
import pages.RegisterPage;
import utils.*;

import static utils.DataGenerator.generateSecurePassword;
import utils.ExtentManager;


public class RegisterTest {
    WebDriver driver;
    RegisterPage registerPage;
    ExtentReports extent;
    ExtentTest test;


    @BeforeMethod
    public void setup() {
        extent = ExtentManager.getInstance();
        test = extent.createTest("Register Test");
        driver = DriverFactory.getDriver();
        driver.get(ConfigLoader.getBaseUrl());
        registerPage = new RegisterPage(driver);
    }


    @Test(priority = 1)
    public void testRegisterNavigation() {
        test = extent.createTest(TestCaseMeta.TC01);
        test.info("Register linkine tıklanıyor");

        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();

        boolean isOnRegisterPage = header.isOnRegisterPage();
        test.info("Sayfa durumu kontrol ediliyor");

        Assert.assertTrue(isOnRegisterPage, "Register linkine tıklanınca kayıt formu açılmadı!");
        test.pass("Kayıt formu başarıyla açıldı");
    }



    @Test(priority = 2)
    public void testDynamicUserRegistrationFromJson() {
        test = extent.createTest(TestCaseMeta.TC02);

        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = generateSecurePassword();
        test.info("Yeni kullanıcı oluşturuluyor: " + email);

        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();
        test.info("Register sayfasına geçildi");

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(email, password);
        test.info("Kullanıcı formu dolduruldu ve gönderildi");

        boolean isRegistered = registerPage.isRegistrationSuccessful();
        Assert.assertTrue(isRegistered, "Kayıt başarısız oldu!");
        test.pass("Kullanıcı başarıyla kaydedildi");
    }



    @Test(priority = 3)
    public void testDuplicateEmailRegistration() {
        test = extent.createTest(TestCaseMeta.TC03);

        User user = TestDataReader.getRegisterUsers().get(0);
        test.info("Var olan kullanıcıyla kayıt deneniyor: " + user.getEmail());

        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();
        test.info("Register sayfasına geçildi");

        registerPage.register(user.getEmail(), user.getPassword());
        test.info("Kayıt formu dolduruldu ve gönderildi");

        String errorMessage = registerPage.getErrorMessage();
        test.info("Alınan hata mesajı: " + errorMessage);

        Assert.assertTrue(errorMessage.contains("Email already exists"), "Beklenen hata mesajı alınmadı!");
        test.pass("Sistem, tekrar kayıt denemesinde doğru hata mesajını gösterdi");
    }


    @AfterMethod
    public void tearDown() {
        extent.flush();
        DriverFactory.quitDriver();
    }
}