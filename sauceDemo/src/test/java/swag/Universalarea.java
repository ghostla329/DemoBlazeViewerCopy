package swag;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pomClasses.UniversalareaPom;

public class Universalarea extends BaseTest {
	UniversalareaPom uniter;
	private String expectedUrl = pred.getProperty("cartpageUrl");// the expected verfying url

	@BeforeMethod(dependsOnMethods = { "Browser_launch" })
	public void prequisite() {
		uniter = new UniversalareaPom(driver);
	}

	@Test(dataProvider = "data")
	public void cartbutton_test(String url) {
		driver.get(url);
		WebElement element = uniter.cart_icon();// got on cart icon element
		assertTrue(element.isDisplayed());
		element.click();// clicked on element
		assertEquals(driver.getCurrentUrl(), expectedUrl);
		asrt.assertAll();
	}

	@DataProvider
	public Object[][] data() {

		Object[][] pageurls = new Object[12][1];
		pageurls[0][0] = pred.getProperty("cartpageUrl");
		pageurls[1][0] = pred.getProperty("homepageUrl");
		pageurls[2][0] = pred.getProperty("BackpackUrl");
		pageurls[3][0] = pred.getProperty("Fleece_JacketUrl");
		pageurls[4][0] = pred.getProperty("Bolt_T-ShirtUrl");
		pageurls[5][0] = pred.getProperty("Bike_LightUrl");
		pageurls[6][0] = pred.getProperty("Test.allTheThings()_T-Shirt_(Red)Url");
		pageurls[7][0] = pred.getProperty("OnesieUrl");
		pageurls[8][0] = pred.getProperty("checkoutpageUrl");
		pageurls[9][0] = pred.getProperty("cartpageUrl");
		pageurls[10][0] = pred.getProperty("cartpageUrl");
		pageurls[11][0] = pred.getProperty("finalpageUrl");

		return pageurls;
	}

	@Test(dataProvider = "data")
	public void drawerSidebar_test(String url) {
		driver.get(url);
		WebElement ref_element = uniter.drawerButton();

		asrt.assertTrue(ref_element.isDisplayed());
		ref_element.click();
		logoutcheck();
		// negative scenario
		driver.navigate().back();
		asrt.assertTrue(driver.getCurrentUrl().equals(url));// make it assertfalse
		asrt.assertAll();
	}

	@Test(dataProvider = "data")
	public void drawerSidebar(String url) {
		driver.get(url);
		WebElement ref_element = uniter.drawerButton();
		// drawerbutton webelemen
		ref_element.click();// clicked on drawer

		ref_element = uniter.sidebarOption_allItemt();// set allitem button sidebar element

		asrt.assertTrue(uniter.give_VisibilityStatus(ref_element));// checking the if element is visible or not
		ref_element.click();// clicked on alliem option
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("the thread sleep used here. remove it");
		}
		asrt.assertTrue(driver.getCurrentUrl().equals("homepageUrl"));

		try {
			ref_element.isDisplayed();
		} catch (StaleElementReferenceException e) {
			ref_element = null;
		}
		asrt.assertNull(ref_element);
		asrt.assertAll();
	}
//--------------------------------------------------------------------------------------------
	private void logoutcheck() {
		WebElement ref_element;
		ref_element = uniter.sidebarOption_logout();
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {

		}
		asrt.assertTrue(ref_element.isDisplayed());
		ref_element.click();
		asrt.assertEquals(driver.getCurrentUrl(), pred.getProperty("loginpageUrl"));
	}
}
