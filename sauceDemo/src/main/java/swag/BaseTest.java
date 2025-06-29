package swag;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import utility.properties_Reader;

public class BaseTest {
	protected WebDriver driver;
	/**
	 * original imlicit wait time
	 */
	public long ImplicitWait_Time;
	/**
	 * wait object
	 */
	public WebDriverWait wait;
	/**
	 * object for properties file reader
	 */
	protected properties_Reader pred = new properties_Reader();// global level creation to read properties file
	private String Browsername;// name of the browser to test
	/**
	 * SoftAssertion Object
	 */
	public SoftAssert asrt;// global level creaation to implemeent assertions

	public BaseTest() {
		Browsername = pred.getProperty("Browser").toUpperCase();
		asrt = new SoftAssert();
		ImplicitWait_Time = Long.valueOf(pred.getProperty("Originalimplicitwait"));

	}

	@BeforeMethod(alwaysRun = true)
	public void Browser_launch() {
		

		if (Browsername.equals("CHROME")) {
			driver = new ChromeDriver();
		} else if (Browsername == "EDGE") {
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ImplicitWait_Time));
		wait = new WebDriverWait(driver, Duration.ofSeconds(8));

	}

	@AfterMethod(alwaysRun = true)
	public void Browser_close() {

		driver.quit();
		System.out.println("browser closed");
	}

}
