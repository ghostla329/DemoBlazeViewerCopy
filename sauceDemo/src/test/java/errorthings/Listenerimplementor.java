package errorthings;

import java.io.File;
import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utility.screenshotTaker;

public class Listenerimplementor implements ITestListener {
	ExtentReports extnt = new ExtentReports();//object for extenet reporrts
	ExtentTest test; //for the test relatted opration
	WebDriver driver;//the driver of the file

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("teststart");
		test = extnt.createTest(result.getName());//created a reporter tes object
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, result.getTestName());
		extnt.flush();
		System.out.println("sucess");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		try {
			Object testInstance = result.getInstance();
			// Step 2: Get the class of the test
			Class<?> testClass = testInstance.getClass().getSuperclass();
			// Step 3: Access the "driver" field
			Field driverField = testClass.getDeclaredField("driver");
			// Step 4: Make the field accessible (important for private or package-private
			// change access
			driverField.setAccessible(true);
			// Step 5: Get the WebDriver from the field
			this.driver = (WebDriver) driverField.get(testInstance);
			screenshotTaker st = new screenshotTaker(driver);
			String out_path = st.capture(result.getName().toString());
			test.addScreenCaptureFromPath(out_path);
			// close acess
			driverField.setAccessible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		test.log(Status.FAIL, "test Failed");
		test.log(Status.INFO, "failed class name->  " + result.getInstance().getClass().toString());
		test.log(Status.INFO, "methodName/testResultName->   " + result.getName());
		test.log(Status.WARNING, result.getThrowable());

		extnt.flush();//flushed
		System.out.println("onrestfailed executed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extnt.flush();
		System.out.println("skipped");
		
	}
	@Override
	public void onStart(ITestContext context) {
		File path = new File(System.getProperty("user.dir") + File.separator + "report");
		ExtentSparkReporter porter = new ExtentSparkReporter(path);
		porter.config().setDocumentTitle("test report");
		porter.config().setReportName("functionality report");
		porter.config().setTheme(Theme.DARK);
		extnt.attachReporter(porter);
		System.out.println("firststart");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("end");
	}

}
