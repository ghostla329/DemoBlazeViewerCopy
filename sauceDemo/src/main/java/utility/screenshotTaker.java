package utility;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class screenshotTaker {
	WebDriver driver;

	public screenshotTaker(WebDriver driver) {
		this.driver = driver;

	}
/**
 * 
 * @param ImageName- the name of the image file that you want to save
 * @return the path where the image was saved 
 */
	public String capture(String ImageName) {
		String path = System.getProperty("user.dir") + File.separator + "screenshots";
		
		// if file not present then creartes one
		File screenshot_dir = new File(path);
		if (!screenshot_dir.exists()) {
			screenshot_dir.mkdir();
		}
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File temp = ts.getScreenshotAs(OutputType.FILE);
		String permpath = path + File.separator + ImageName + ".png";
		File perm = new File(permpath);
		try {
			FileHandler.copy(temp, perm);
		} catch (IOException e) {
		}
		return permpath;//returns the path to the screenshot taken
	}
}
