package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * ExtentReports yapılandırması ve yönetimi
 * Test raporlarının oluşturulmasını sağlar
 */
public class ExtentManager {

    private static ExtentReports extent;

    /**
     * ExtentReports instance'ını oluştur ve yapılandır
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            // Türkçe karakter desteği için
            Locale.setDefault(Locale.US);

            // Rapor klasörünü oluştur
            String reportDir = System.getProperty("user.dir") + "/test-output/";
            File dir = new File(reportDir);
            if (!dir.exists()) dir.mkdirs();

            // Zaman damgalı rapor dosyası oluştur
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String reportPath = reportDir + "QTripTestRaporu_" + timestamp + ".html";

            // Rapor ayarlarını yapılandır
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("QTrip Test Raporu");
            spark.config().setDocumentTitle("QTrip Automation Test Sonuçları");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setEncoding("UTF-8");

            // ExtentReports oluştur ve rapor bilgilerini ekle
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Temel sistem bilgileri
            extent.setSystemInfo("Uygulama", "QTrip Dynamic");
            extent.setSystemInfo("Test Framework", "Selenium + TestNG");
            extent.setSystemInfo("Rapor Tarihi", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        }
        return extent;
    }
}
