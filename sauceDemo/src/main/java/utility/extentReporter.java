package utility;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class extentReporter {

	public static void m1() {
		File path = new File(System.getProperty("user.dir") + File.separator + "repoert");
		ExtentSparkReporter porter = new ExtentSparkReporter(path);
		porter.config().setDocumentTitle("test report");
		porter.config().setReportName("functionality report");
		porter.config().setTheme(Theme.DARK);
		ExtentReports extnt = new ExtentReports();
		extnt.attachReporter(porter);
	}
}