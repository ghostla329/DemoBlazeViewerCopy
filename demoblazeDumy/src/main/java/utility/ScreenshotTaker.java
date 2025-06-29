package utility;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import com.aventstack.extentreports.ExtentTest;

public class ScreenshotTaker {
	private TakesScreenshot ts;

	private static String screnshotPath = System.getProperty("user.dir") + File.separator + "screenShots";
	static {
		File screenshotFolder = new File(screnshotPath);
		if (!screenshotFolder.exists()) {
			screenshotFolder.mkdir();// create a folder if it is not present
		}
	}

	public ScreenshotTaker(WebDriver driver) {
		// initialz driver and typecast

		ts = (TakesScreenshot) driver;
	}

	/**
	 * 
	 * @param test      the extentTest variable used for the test.
	 * @param imageName the name of the screenshot file .
	 * @implNote This method captures the screenshot and adds it to the report.
	 */
	public void capture(ExtentTest test, String imageName) {
		String permPath = screnshotPath + File.separator + imageName + ".png";
		File tempLoc = ts.getScreenshotAs(OutputType.FILE);
		File permLoc = new File(permPath);

		try {
			FileHandler.copy(tempLoc, permLoc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// addthe screenshot to the report
		test.addScreenCaptureFromPath(permPath);
	}
}
