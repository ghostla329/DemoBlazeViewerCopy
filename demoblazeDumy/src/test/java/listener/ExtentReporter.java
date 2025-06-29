package listener;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * the class for extent report implemetation
 */
public class ExtentReporter {
	static protected ExtentReports extnt = new ExtentReports();
	static private ExtentSparkReporter reporter;
	static {
		File loc = new File(System.getProperty("user.dir") + File.separator + "reports");
		if (!loc.exists()) {
			loc.mkdir();
		}
		reporter = new ExtentSparkReporter(loc);
		reporter.config().setDocumentTitle("Demoblaze Test results");
		reporter.config().setTheme(Theme.DARK);
		reporter.config().setReportName("Demoblaze Test Report");
		// connect the reporter
		extnt.attachReporter(reporter);
	}

}
