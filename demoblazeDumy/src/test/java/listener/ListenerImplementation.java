package listener;

import static listener.ExtentReporter.extnt;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import customErrorThings.Errrors;
import utility.ScreenshotTaker;

public class ListenerImplementation implements ITestListener {

	static String loggable;
	ExtentTest test;

	@Override
	public void onTestStart(ITestResult result) {
//		metod exceution start
		// create the extenrt report extenttest
		test = extnt.createTest(result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String loggableMessage = Errrors.getErrorMsg();
		test.log(Status.PASS,"the test passed");
		if(!loggableMessage.isBlank()) {
		test.log(Status.INFO, loggableMessage);}
		extnt.flush();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("fail");
		test.log(Status.FAIL, "test failed");
		test.log(Status.WARNING, result.getThrowable());
		attachScreenCaptureToReport(result);
		extnt.flush();
	}

	
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("skipped");
		test.log(Status.SKIP, "test failed");
		test.log(Status.WARNING, result.getThrowable());
		test.log(Status.WARNING, result.getSkipCausedBy().toString());
		extnt.flush();
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("tests started");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("tests ended");
	}
//	-------------------------------------helper----------------------------------------------------
	private void attachScreenCaptureToReport(ITestResult result) {
		WebDriver driver=null;
		//get the test objecton which it is wrapped
		Object instnace=result.getInstance();
		//get theclass where driver exists
		Class<?> driverClass = instnace.getClass().getSuperclass();
		//get the field
		try{Field declaredField = driverClass.getDeclaredField("driver");
		declaredField.setAccessible(true);
		 driver =(WebDriver)declaredField.get(instnace);
		declaredField.setAccessible(false);}
		catch(NoSuchFieldException e) {
			e.printStackTrace();
		}
		catch(IllegalAccessException f) {
			f.printStackTrace();
		}
		new ScreenshotTaker(driver).capture(test, result.getName());
	}

}
