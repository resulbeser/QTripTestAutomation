package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigLoader;
import utils.DriverFactory;
import utils.ExtentManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Tüm test sınıfları için temel sınıf
 * WebDriver ve ExtentReports yönetimini sağlar
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected ExtentTest test;
    protected static ExtentReports extent;

    /**
     * Test suite başlamadan önce rapor sistemini başlat
     */
    @BeforeSuite
    public void setupSuite() {
        extent = ExtentManager.getInstance();
        // Sistem bilgilerini ekle
        extent.setSystemInfo("Test Engineer", "QA Automation Team");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Test Date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }

    /**
     * Her test öncesi WebDriver'ı başlat ve ana sayfayı aç
     */
    @BeforeMethod
    public void setupMethod() {
        driver = DriverFactory.getDriver();
        driver.get(ConfigLoader.getBaseUrl());
    }

    /**
     * Her test sonrası temizlik yap ve raporu güncelle
     */
    @AfterMethod
    public void tearDownMethod(ITestResult result) {
        if (test != null) {
            // Test başarısız olduysa ekran görüntüsü al
            if (result.getStatus() == ITestResult.FAILURE) {
                takeScreenshotOnFailure(result);
            }

            // Test süresini hesapla ve kaydet
            long duration = result.getEndMillis() - result.getStartMillis();
            test.info("Test Süresi: " + duration + " ms");
        }

        // Driver'ı kapat
        DriverFactory.quitDriver();
    }

    /**
     * Tüm testler bittikten sonra raporu kaydet
     */
    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
    }

    /**
     * Test başarısız olduğunda ekran görüntüsü al
     */
    private void takeScreenshotOnFailure(ITestResult result) {
        try {
            // Ekran görüntüsü al
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);

            // Dosya adı oluştur
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmms"));
            String fileName = result.getMethod().getMethodName() + "_" + timestamp + ".png";
            String screenshotDir = "test-output/screenshots/";

            // Klasör oluştur ve dosyayı kaydet
            Files.createDirectories(Paths.get(screenshotDir));
            String filePath = screenshotDir + fileName;
            Files.write(Paths.get(filePath), screenshotBytes);

            // Raporda hata ve ekran görüntüsünü göster
            test.fail("Test Hatası: " + result.getThrowable().getMessage())
                .addScreenCaptureFromPath(filePath);

        } catch (IOException e) {
            test.fail("Test Hatası: " + result.getThrowable().getMessage());
            test.fail("Ekran görüntüsü alınamadı: " + e.getMessage());
        }
    }
}
