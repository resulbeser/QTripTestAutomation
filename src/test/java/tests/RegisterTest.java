package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HeaderFragment;
import pages.RegisterPage;
import utils.*;

/**
 * QTrip Uygulaması Register Fonksiyonalitesi Test Sınıfı
 * Bu sınıf kullanıcı kayıt işlemlerini test eder
 */
public class RegisterTest extends BaseTest {

    /**
     * TC-01: Register sayfasına başarılı navigasyon testi
     */
    @Test(priority = 1)
    public void testRegisterNavigation() {
        // Test başlat ve bilgileri kaydet
        test = extent.createTest("TC-01: Register Sayfası Navigasyon", "Ana sayfadan register sayfasına geçiş testi")
                .assignCategory("Register Tests - Navigation")
                .assignCategory("Positive Tests")
                .assignAuthor("QA Team");

        // Ana sayfadan register sayfasına git
        HeaderFragment header = new HeaderFragment(driver);
        header.navigateToRegisterPage();
        test.pass("Register linkine başarıyla tıklandı");

        // Register sayfasında olduğunu doğrula
        RegisterPage registerPage = new RegisterPage(driver);
        Assert.assertTrue(registerPage.isCurrentPage(), "Register sayfasına geçilemedi!");
        test.pass("Register sayfasına başarıyla yönlendirildi");
    }

    /**
     * TC-02: Yeni kullanıcı kaydı - Pozitif senaryo
     */
    @Test(priority = 2)
    public void testNewUserRegistration() {
        // Test başlat
        test = extent.createTest("TC-02: Yeni Kullanıcı Kaydı", "Geçerli bilgilerle yeni kullanıcı kaydı")
                .assignCategory("Register Tests - Registration")
                .assignCategory("Positive Tests")
                .assignAuthor("QA Team");

        // Test verilerini hazırla
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = DataGenerator.generateSecurePassword();
        test.info("Email: " + email);

        // Register sayfasına git
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();

        // Kayıt formunu doldur ve gönder
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(email, password);
        test.pass("Kayıt formu başarıyla dolduruldu");

        // Kayıt başarılı mı kontrol et
        Assert.assertTrue(registerPage.isRegistrationSuccessful(), "Kayıt işlemi başarısız!");
        test.pass("Yeni kullanıcı başarıyla kaydedildi");
    }

    /**
     * TC-03: Duplicate email testi - Negatif senaryo
     */
    @Test(priority = 3)
    public void testDuplicateEmailRegistration() {
        // Test başlat
        test = extent.createTest("TC-03: Duplicate Email Testi", "Mevcut email ile kayıt deneme")
                .assignCategory("Register Tests - Validation")
                .assignCategory("Negative Tests")
                .assignAuthor("QA Team");

        // Mevcut kullanıcı verilerini al
        User existingUser = TestDataReader.getRegisterUsers().get(0);
        test.info("Mevcut Email: " + existingUser.getEmail());

        // Register sayfasına git ve mevcut email ile kayıt dene
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(existingUser.getEmail(), existingUser.getPassword());

        // Duplicate email hata mesajını kontrol et
        String errorMessage = registerPage.getErrorMessage();
        test.info("Hata Mesajı: " + errorMessage);

        Assert.assertTrue(errorMessage.contains(Constants.ErrorMessages.EMAIL_EXISTS),
                "Duplicate email hatası alınmadı!");
        test.pass("Sistem duplicate email için doğru hata gösterdi");
    }

    /**
     * TC-04: Kısa şifre testi - Negatif senaryo
     */
    @Test(priority = 4)
    public void testShortPasswordRegistration() {
        // Test başlat
        test = extent.createTest("TC-04: Kısa Şifre Testi", "6 karakterden kısa şifre ile kayıt deneme")
                .assignCategory("Register Tests - Validation")
                .assignCategory("Negative Tests")
                .assignAuthor("QA Team");

        // Test verilerini hazırla
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String shortPassword = ConfigLoader.getProperty("weak.password");
        test.info("Email: " + email);
        test.info("Kısa Şifre: " + shortPassword + " (" + shortPassword.length() + " karakter)");

        // Register sayfasına git ve kısa şifre ile kayıt dene
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(email, shortPassword);

        // Kısa şifre hata mesajını kontrol et
        String errorMessage = registerPage.getErrorMessage();
        test.info("Hata Mesajı: " + errorMessage);

        Assert.assertTrue(errorMessage.contains(Constants.ErrorMessages.PASSWORD_SHORT),
                "Kısa şifre hatası alınmadı!");
        test.pass("Sistem kısa şifre için doğru hata gösterdi");
    }

    /**
     * TC-05: Boş form validasyon testi - Negatif senaryo
     */
    @Test(priority = 5)
    public void testEmptyFormValidation() {
        // Test başlat
        test = extent.createTest("TC-05: Boş Form Validasyon Testi", "Tüm alanlar boş bırakıldığında validasyon kontrolü")
                .assignCategory("Register Tests - Validation")
                .assignCategory("Negative Tests")
                .assignAuthor("QA Team");

        test.info("Test Senaryosu: Tüm alanlar boş bırakılarak register butonuna tıklanacak");

        // Register sayfasına git
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();
        test.pass("Register sayfasına başarıyla geçildi");

        RegisterPage registerPage = new RegisterPage(driver);

        // Hiçbir alan doldurmadan direkt register butonuna tıkla
        registerPage.clickRegister();
        test.info("Boş form ile register butonuna tıklandı");

        // Hala register sayfasında olduğunu kontrol et (form gönderilmemeli)
        Assert.assertTrue(registerPage.isCurrentPage(),
                "Form gönderildi! Boş alanlarla form gönderimi engellenmeli.");
        test.pass("Form gönderimi başarıyla engellendi");

        // Validasyon mesajını kontrol et
        String errorMessage = registerPage.getErrorMessage();
        if (!errorMessage.isEmpty()) {
            test.info("Validasyon Mesajı: " + errorMessage);
            test.pass("Sistem boş form için validasyon mesajı gösterdi");
        } else {
            test.pass("HTML5 validasyonu ile form gönderimi engellendi");
        }
    }



}