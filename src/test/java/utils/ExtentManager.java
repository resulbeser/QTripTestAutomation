package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.util.Locale;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {

            Locale.setDefault(Locale.US);


            String reportDir = System.getProperty("user.dir") + "/test-output/";
            File dir = new File(reportDir);
            if (!dir.exists()) dir.mkdirs();


            String reportPath = reportDir + "ExtentReport.html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Resul");
        }
        return extent;
    }
}
