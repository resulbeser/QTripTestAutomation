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
     * TC-02: Boş form validasyon testi - Negatif senaryo
     */
    @Test(priority = 2)
    public void testEmptyFormValidation() {
        // Test başlat
        test = extent.createTest("TC-02: Boş Form Validasyon Testi", "Tüm alanlar boş bırakıldığında validasyon kontrolü")
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

    /**
     * TC-03: Sadece Email Dolu Testi - Negatif senaryo
     */
    @Test(priority = 3)
    public void testOnlyEmailFilledValidation() {
        // Test başlat
        test = extent.createTest("TC-03: Sadece Email Dolu Testi", "Sadece email doldurulup diğer alanlar boş bırakıldığında validasyon kontrolü")
                .assignCategory("Register Tests - Validation")
                .assignCategory("Negative Tests")
                .assignAuthor("QA Team");

        // Test verilerini hazırla
        String email = "user" + System.currentTimeMillis() + "@example.com";
        test.info("Email: " + email);
        test.info("Şifre ve Şifre Tekrar: (boş bırakıldı)");

        // Register sayfasına git
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();
        test.pass("Register sayfasına başarıyla geçildi");

        RegisterPage registerPage = new RegisterPage(driver);

        // Sadece email doldur, diğer alanları boş bırak
        registerPage.enterEmail(email);
        test.pass("Sadece email alanı dolduruldu");

        // Register butonuna tıkla (şifre alanları boşken)
        registerPage.clickRegister();
        test.info("Şifre alanları boş bırakılarak register butonuna tıklandı");

        // Hala register sayfasında olduğunu kontrol et (form gönderilmemeli)
        Assert.assertTrue(registerPage.isCurrentPage(),
                "Form gönderildi! Şifre alanları boşken form gönderimi engellenmeli.");
        test.pass("Form gönderimi başarıyla engellendi");

        // Validasyon mesajını kontrol et
        String errorMessage = registerPage.getErrorMessage();
        if (!errorMessage.isEmpty()) {
            test.info("Validasyon Mesajı: " + errorMessage);
            test.pass("Sistem boş şifre alanları için validasyon mesajı gösterdi");
        } else {
            test.pass("HTML5 validasyonu ile form gönderimi engellendi");
        }
    }

    /**
     * TC-04: Email ve şifre dolu, confirm password boş testi - Negatif senaryo
     */
    @Test(priority = 4)
    public void testEmailPasswordFilledConfirmPasswordEmpty() {
        // Test başlat
        test = extent.createTest("TC-04: Confirm Password Boş Validasyon Testi", "Email ve şifre doldurulup confirm password boş bırakıldığında validasyon kontrolü")
                .assignCategory("Register Tests - Validation")
                .assignCategory("Negative Tests")
                .assignAuthor("QA Team");

        // Test verilerini hazırla
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = DataGenerator.generateSecurePassword();
        test.info("Email: " + email);
        test.info("Şifre Tekrar: (boş bırakıldı)");

        // Register sayfasına git
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();
        test.pass("Register sayfasına başarıyla geçildi");

        RegisterPage registerPage = new RegisterPage(driver);

        // Email ve şifre doldur, confirm password boş bırak
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        test.pass("Email ve şifre alanları dolduruldu, confirm password boş bırakıldı");

        // Register butonuna tıkla (confirm password boşken)
        registerPage.clickRegister();
        test.info("Confirm password boş bırakılarak register butonuna tıklandı");

        // Hala register sayfasında olduğunu kontrol et (form gönderilmemeli)
        Assert.assertTrue(registerPage.isCurrentPage(),
                "Form gönderildi! Confirm password boşken form gönderimi engellenmeli.");
        test.pass("Form gönderimi başarıyla engellendi");

        // Validasyon mesajını kontrol et
        String errorMessage = registerPage.getErrorMessage();
        if (!errorMessage.isEmpty()) {
            test.info("Validasyon Mesajı: " + errorMessage);
            test.pass("Sistem boş confirm password alanı için validasyon mesajı gösterdi");
        } else {
            test.pass("HTML5 validasyonu ile form gönderimi engellendi");
        }
    }

    /**
     * TC-05: Şifre uyumsuzluğu testi - Negatif senaryo
     */
    @Test(priority = 5)
    public void testPasswordMismatchValidation() {
        // Test başlat
        test = extent.createTest("TC-05: Şifre Uyumsuzluğu Testi", "Farklı şifreler girildiğinde validasyon kontrolü")
                .assignCategory("Register Tests - Validation")
                .assignCategory("Negative Tests")
                .assignAuthor("QA Team");

        // Test verilerini hazırla
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = DataGenerator.generateSecurePassword();
        String confirmPassword = DataGenerator.generateSecurePassword(); // Farklı şifre
        test.info("Email: " + email);
        test.info("Şifre: " + password);
        test.info("Şifre Tekrar: " + confirmPassword);

        // Register sayfasına git
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();
        test.pass("Register sayfasına başarıyla geçildi");

        RegisterPage registerPage = new RegisterPage(driver);

        // Form alanlarını farklı şifrelerle doldur
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.enterConfirmPassword(confirmPassword);
        test.pass("Form alanları farklı şifrelerle dolduruldu");

        // Register butonuna tıkla
        registerPage.clickRegister();
        test.info("Farklı şifrelerle register butonuna tıklandı");

        // ÖNCELİKLE alert kontrolü yap (alert varsa hemen yakala)
        String errorMessage = registerPage.getErrorMessage();
        if (!errorMessage.isEmpty()) {
            test.info("Hata Mesajı: " + errorMessage);
            Assert.assertTrue(errorMessage.contains("Password and confirm Password do not match") ||
                            errorMessage.contains("Passwords do not match") ||
                            errorMessage.contains("Şifreler eşleşmiyor"),
                    "Şifre uyumsuzluğu hatası alınmadı!");
            test.pass("Sistem şifre uyumsuzluğu için doğru hata gösterdi");
        }

        // Alert kapandıktan sonra sayfa kontrolü yap
        Assert.assertTrue(registerPage.isCurrentPage(),
                "Form gönderildi! Şifre uyumsuzluğunda form gönderimi engellenmeli.");
        test.pass("Form gönderimi başarıyla engellendi");
    }

    /**
     * TC-06: Kısa şifre testi - Negatif senaryo
     */
    @Test(priority = 6)
    public void testShortPasswordRegistration() {
        // Test başlat
        test = extent.createTest("TC-06: Kısa Şifre Testi", "6 karakterden kısa şifre ile kayıt deneme")
                .assignCategory("Register Tests - Validation")
                .assignCategory("Negative Tests")
                .assignAuthor("QA Team");

        // Test verilerini hazırla
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String shortPassword = ConfigLoader.getProperty("weak.password");
        test.info("Email: " + email);
        test.info("Kısa Şifre: " + shortPassword + " (" + shortPassword.length() + " karakter)");

        // Register sayfasına git
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);

        // Form alanlarını tek tek doldur
        registerPage.enterEmail(email);
        registerPage.enterPassword(shortPassword);
        registerPage.enterConfirmPassword(shortPassword);
        test.pass("Form alanları dolduruldu");

        // Register butonuna tıkla
        registerPage.clickRegister();
        test.info("Register butonuna tıklandı");

        // Hala register sayfasında olduğunu kontrol et
        Assert.assertTrue(registerPage.isCurrentPage(),
                "Register sayfasından ayrıldı! Kısa şifre ile kayıt engellenmeliydi.");
        test.pass("Form gönderimi başarıyla engellendi");

        // Kısa şifre hata mesajını kontrol et (timeout ile)
        String errorMessage = registerPage.getErrorMessage();
        if (!errorMessage.isEmpty()) {
            test.info("Hata Mesajı: " + errorMessage);
            test.pass("Sistem kısa şifre için validasyon mesajı gösterdi");
        } else {
            test.pass("HTML5 validasyonu ile form gönderimi engellendi");
        }
    }

    /**
     * TC-07: Duplicate email testi - Negatif senaryo
     */
    @Test(priority = 7)
    public void testDuplicateEmailRegistration() {
        // Test başlat
        test = extent.createTest("TC-07: Duplicate Email Testi", "Mevcut email ile kayıt deneme")
                .assignCategory("Register Tests - Validation")
                .assignCategory("Negative Tests")
                .assignAuthor("QA Team");

        // Mevcut kullanıcı verilerini al
        User existingUser = TestDataReader.getRegisterUsers().get(0);
        test.info("Mevcut Email: " + existingUser.getEmail());

        // Register sayfasına git
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);

        // Form alanlarını tek tek doldur
        registerPage.enterEmail(existingUser.getEmail());
        registerPage.enterPassword(existingUser.getPassword());
        registerPage.enterConfirmPassword(existingUser.getPassword());
        test.pass("Form alanları dolduruldu");

        // Register butonuna tıkla
        registerPage.clickRegister();
        test.info("Register butonuna tıklandı");

        // Duplicate email hata mesajını kontrol et (timeout ile)
        String errorMessage = registerPage.getErrorMessage();
        test.info("Hata Mesajı: " + errorMessage);

        Assert.assertTrue(errorMessage.contains(Constants.ErrorMessages.EMAIL_EXISTS),
                "Duplicate email hatası alınmadı!");
        test.pass("Sistem duplicate email için doğru hata gösterdi");
    }

    /**
     * TC-08: Random Email ile Başarılı Kayıt - Pozitif senaryo
     */
    @Test(priority = 8)
    public void testRandomEmailSuccessfulRegistration() {
        // Test başlat
        test = extent.createTest("TC-08: Random Email ile Başarılı Kayıt", "Random email ile geçerli bilgilerle yeni kullanıcı kaydı ve login sayfası kontrolü")
                .assignCategory("Register Tests - Registration")
                .assignCategory("Positive Tests")
                .assignAuthor("QA Team");

        // Test verilerini hazırla
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = DataGenerator.generateSecurePassword();
        test.info("Email: " + email);
        test.info("Şifre: " + password);

        // Register sayfasına git
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();
        test.pass("Register sayfasına başarıyla geçildi");

        RegisterPage registerPage = new RegisterPage(driver);

        // Form alanlarını tek tek doldur
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.enterConfirmPassword(password);
        test.pass("Kayıt formu random email ve güvenli şifre ile dolduruldu");

        // Register butonuna tıkla
        registerPage.clickRegister();
        test.info("Register butonuna tıklandı");

        // Kayıt başarılı mı kontrol et (timeout ile)
        Assert.assertTrue(registerPage.isRegistrationSuccessful(), "Kayıt işlemi başarısız!");
        test.pass("Random email ile kullanıcı başarıyla kaydedildi");

        // Login sayfasına yönlendirildiğini doğrula
        Assert.assertTrue(driver.getCurrentUrl().contains("/pages/login"),
                "Login sayfasına yönlendirilmedi!");
        test.pass("Kayıt sonrası login sayfasına başarıyla yönlendirildi - Login formu görüntülendi");

        test.pass("Test başarıyla tamamlandı - Login formu görüntülendi");
    }

    /**
     * TC-09: Email Case Sensitivity Testi - Negatif senaryo
     */
    @Test(priority = 9)
    public void testEmailCaseSensitivityValidation() {
        // Test başlat
        test = extent.createTest("TC-09: Email Case Sensitivity Testi", "Aynı email adresinin büyük/küçük harf versiyonlarıyla kayıt kontrolü")
                .assignCategory("Register Tests - Validation")
                .assignCategory("Negative Tests")
                .assignAuthor("QA Team");

        // Test verilerini hazırla
        String baseEmail = "testuser" + System.currentTimeMillis();
        String upperCaseEmail = baseEmail.toUpperCase() + "@EXAMPLE.COM";
        String lowerCaseEmail = baseEmail.toLowerCase() + "@example.com";
        String password = DataGenerator.generateSecurePassword();

        test.info("Büyük Harfli Email: " + upperCaseEmail);
        test.info("Küçük Harfli Email: " + lowerCaseEmail);
        test.info("Şifre: " + password);

        // İLK KAYIT: Büyük harfli email ile kayıt ol
        HeaderFragment header = new HeaderFragment(driver);
        header.clickRegister();
        test.pass("Register sayfasına başarıyla geçildi");

        RegisterPage registerPage = new RegisterPage(driver);

        // Büyük harfli email ile kayıt yap
        registerPage.enterEmail(upperCaseEmail);
        registerPage.enterPassword(password);
        registerPage.enterConfirmPassword(password);
        test.pass("Form büyük harfli email ile dolduruldu");

        registerPage.clickRegister();
        test.info("Büyük harfli email ile kayıt yapıldı");

        // İlk kayıt başarılı mı kontrol et
        Assert.assertTrue(registerPage.isRegistrationSuccessful(), "İlk kayıt işlemi başarısız!");
        test.pass("Büyük harfli email ile başarıyla kayıt olundu");

        // İKİNCİ KAYIT DENEMESİ: Aynı email'in küçük harfli versiyonu ile kayıt olmaya çalış
        // Tekrar register sayfasına git
        driver.get(ConfigLoader.getBaseUrl());
        header = new HeaderFragment(driver);
        header.clickRegister();
        test.info("İkinci kayıt için register sayfasına gidildi");

        registerPage = new RegisterPage(driver);

        // Küçük harfli email ile kayıt yapmaya çalış
        registerPage.enterEmail(lowerCaseEmail);
        registerPage.enterPassword(password);
        registerPage.enterConfirmPassword(password);
        test.pass("Form küçük harfli email ile dolduruldu");

        registerPage.clickRegister();
        test.info("Küçük harfli email ile kayıt denendi");

        // Case sensitivity kontrolü - Bu kayıt engellenmelidir
        String errorMessage = registerPage.getErrorMessage();
        if (!errorMessage.isEmpty()) {
            test.info("Hata Mesajı: " + errorMessage);

            // Email exists hatası alınmalı (case sensitivity varsa)
            Assert.assertTrue(errorMessage.contains(Constants.ErrorMessages.EMAIL_EXISTS) ||
                            errorMessage.contains("User already exists") ||
                            errorMessage.contains("Email already registered"),
                    "Case sensitivity hatası alınmadı! Sistem aynı email'in farklı case versiyonunu kabul etti.");
            test.pass("✓ Case Sensitivity ÇALIŞIYOR - Sistem aynı email'in farklı case versiyonunu reddetti");
        } else {
            // Eğer hata mesajı yoksa ve kayıt başarılı olduysa, case sensitivity yok
            if (registerPage.isRegistrationSuccessful()) {
                test.warning("⚠ Case Sensitivity ÇALIŞMIYOR - Sistem aynı email'in farklı case versiyonunu kabul etti");
                Assert.fail("Case sensitivity çalışmıyor! Sistem '" + upperCaseEmail +
                        "' ile kayıtlı kullanıcı varken '" + lowerCaseEmail + "' ile kayıt olmaya izin verdi.");
            } else {
                // Register sayfasında kaldıysa, HTML5 validasyonu engellemiş olabilir
                test.pass("Form gönderimi engellendi - Case sensitivity kontrolü geçerli");
            }
        }

        test.pass("Email case sensitivity testi tamamlandı");
    }

}