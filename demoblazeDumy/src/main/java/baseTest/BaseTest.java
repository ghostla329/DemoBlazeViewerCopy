package baseTest;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class BaseTest {
	protected WebDriver driver;
	private String browser;
	protected WebDriverWait explicitWait;
	public SoftAssert softAsrt;

	public BaseTest() {
		browser = "CHROME";
		softAsrt = new SoftAssert();
	}

	@BeforeMethod(alwaysRun = true)
	public void browserLaunch() {
//		if browser ischromew
		if (browser.equals("chrome".toUpperCase())) {
			driver = new ChromeDriver();
//			if browser is edge
		} else if (browser.equals("edge".toUpperCase())) {
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		explicitWait = new WebDriverWait(driver, Duration.ofSeconds(9));
	}

	@AfterMethod(alwaysRun=true)
	public void browserClose() {
		driver.quit();
	}

}
