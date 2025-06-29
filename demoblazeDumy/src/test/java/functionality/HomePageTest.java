package functionality;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WindowType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import baseTest.BaseTest;
import customErrorThings.Errrors;
import pomClasses.HomepagePom;
import utility.ExcelReader;
import utility.PropertiesReader;

public class HomePageTest extends BaseTest {

	// check if the categories areas eement is visble or not

	HomepagePom homepage;
	String homeUrl = PropertiesReader.getProperty("homepageurl");

	@BeforeMethod(alwaysRun=true)
	public void siteLaunch() {
		driver.get(homeUrl);
		homepage = new HomepagePom(driver, explicitWait);
	}

	@Test(groups = { "smoke" }, priority = 2)
	public void numberOfItemTest() {// check if the total number of item matches or not
		int expected = ExcelReader.numberOfitems("Sheet1");
		int actual = 0;
		actual += homepage.getCardsContainers().size();

		homepage.getNextButton().click();
		actual += homepage.getCardsContainers().size();

		softAsrt.assertFalse(homepage.getNextButton().isDisplayed(),
				"the behaviour is unexpected.this button should notbe visible.severity-,minor,priority-low");
		softAsrt.assertEquals(actual, expected, "the number of tem are not preset as exected");
	}

	@Test
	public void intialPrevAndNextButtonTests() {// check if the prev button and next button work or not

		softAsrt.assertTrue(homepage.getNextButton().isDisplayed(), "a maor bug.Severity- blocker,priority-high");
		softAsrt.assertFalse(homepage.getPreviousButton().isDisplayed(),
				"this is a minor defect.severity-minor priority-low");

//		/get the number of card on thsi page
		int cardCount = Integer.valueOf(PropertiesReader.getProperty("cardCount"));
		// click next
		homepage.getNextButton().click();
		// then cklick previous
		homepage.getPreviousButton().click();
		softAsrt.assertEquals(homepage.getCardsContainers().size(), cardCount);// incomplete
		softAsrt.assertAll();

		// this line is acustom error designed by me
		Errrors.setErrorMessages(
				"refrernece for enhancement.should implement the page number navigation for better ux");
	}

	@Test(groups = { "smoke" })
	public void priceTally() {// check itf the item price tally with each other
		HashMap<String, String> map = homepage.getCardItemNameAndPrices();
		for (String Strng : map.keySet()) {
			String expected = ExcelReader.itemDetailsfnderByProdName("Sheet1", 2, Strng);
			String actual = map.get(Strng);

			softAsrt.assertEquals(actual.trim(), expected.trim());
			softAsrt.assertAll();
		}
	}

	@Test(groups = { "smoke" })
	public void productTitleFunctionalityCheck() {// check if clicking on the title takes to the desired product page

//		get the product names
		Set<String> productTitles = homepage.getCardItemNameAndPrices().keySet();

		for (String eli : productTitles) {
//			 click the name of the product
			homepage.findElementByName(eli).click();

			// match the assertions
			String expectedUrl = ExcelReader.itemDetailsfnderByProdName("Sheet1", 3, eli);
			softAsrt.assertEquals(driver.getCurrentUrl(), expectedUrl, "did not navigate to correct page");
			// open a new tab
			driver.switchTo().newWindow(WindowType.TAB);
			// open the homepage
			driver.get(homeUrl);
		}
		softAsrt.assertAll();
	}

	@Test
	public void productImageFunctionalitycheck() {// check if clicking on the image redirect propetrly
//		get the product names
		Set<String> productTitles = homepage.getCardItemNameAndPrices().keySet();

		for (String eli : productTitles) {
			homepage.getCardImages(eli).click();
			String expectedUrl = ExcelReader.itemDetailsfnderByProdName("Sheet1", 3, eli);
			softAsrt.assertEquals(driver.getCurrentUrl(), expectedUrl, "did not navigate to correct page");
			// open a new tab
			driver.switchTo().newWindow(WindowType.TAB);
			// open the homepage
			driver.get(homeUrl);
		}
		softAsrt.assertAll();
	}

	// test out rthe filters
	@Test
	public void phonefiltercheck() {// check phone filter works properly or not
		// click on the phone category filter
		homepage.getphoneCategoryFilter().click();
		filterCompare("phone");
		softAsrt.assertAll();
		while (homepage.getNextButton().isDisplayed()) {
			homepage.getNextButton().click();
			filterCompare("phone");
		}
		softAsrt.assertAll("the filter do not work properly");
	}

	@Test
	public void laptopFilterCheck() {// check if the laptop filter owrkss properly or not
		homepage.getlaptopCategoryFilter().click();
		filterCompare("laptop");
		softAsrt.assertAll("the filter do not work properly");
		while (homepage.getNextButton().isDisplayed()) {
			homepage.getNextButton().click();
			filterCompare("laptop");
		}
		softAsrt.assertAll("the filter do not work properly");
	}

	@Test
	public void moitorFilterCheck() {
		homepage.getMonitorCategoryFilter().click();
		filterCompare("monitor");
		softAsrt.assertAll();

		while (homepage.getNextButton().isDisplayed()) {
			homepage.getNextButton().click();
			filterCompare("monitor");
		}
		softAsrt.assertAll("the filter do not work properly");
	}

//	---------------------------------------helper methods---------------------------
	/**
	 * compares the dfilter applied properly or not.
	 * 
	 * @param The expected category for the product(phone/laptop/monitor)
	 */
	private void filterCompare(String expectedFilter) {
		List<String> itemNames = homepage.getAllVisibleItemLabels();
		for (String eli : itemNames) {
			String filterName = ExcelReader.itemDetailsfnderByProdName("Sheet1", 4, eli);
			softAsrt.assertTrue(filterName.equalsIgnoreCase(expectedFilter), "category do not match for " + eli);
		}
	}
}
