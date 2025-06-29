package swag;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import errorthings.Listenerimplementor;
import pomClasses.Productpage_generic_pom;
import pomClasses.UniversalareaPom;

@Listeners(Listenerimplementor.class)
public class PrdouctPages_functionality extends BaseTest {
	Productpage_generic_pom proter;
	String logpage = pred.getProperty("loginpageUrl");

	@BeforeMethod(dependsOnMethods = { "Browser_launch" })
	public void prequisitelaunch() {

		driver.get(logpage);// just for more visua purpose
		proter = new Productpage_generic_pom(driver, wait);

	}

	// backpackrelated tests
	@Test
	public void backpackpage_functionalityTest() {
		driver.get(pred.getProperty("BackpackUrl"));
		visibility_test();
		addtocart_check();
		name_price_tally("BackpackPrice");
		backButton_check();
		asrt.assertAll();
	}

	// bikelight related tests
	@Test
	public void bikelightpage_functionalityTest() {
		driver.get(pred.getProperty("Bike_LightUrl"));
		visibility_test();
		addtocart_check();
		name_price_tally("Bike_LightPrice");
		backButton_check();
		asrt.assertAll();
	}

	// bolt shirt related tests
	@Test
	public void bolt_shirt_functionalityTest() {
		driver.get(pred.getProperty("Bolt_T-ShirtUrl"));
		visibility_test();
		addtocart_check();
		name_price_tally("Bolt_T-ShirtPrice");
		backButton_check();
		asrt.assertAll();

	}

	// test allthings shirt related test
	@Test
	public void testallthingShirtpage_functionaity() {
		driver.get(pred.getProperty("Test.allTheThings()_T-Shirt_(Red)Url"));
		visibility_test();
		addtocart_check();
		name_price_tally("Test.allTheThings()_T-Shirt_(Red)Price");
		backButton_check();
		asrt.assertAll();
	}

	// fleece jacket related test
	@Test
	public void fleecejacketpage_functionaityTest() {
		driver.get(pred.getProperty("Fleece_JacketUrl"));
		visibility_test();
		addtocart_check();
		name_price_tally("Fleece_JacketPrice");
		backButton_check();
		asrt.assertAll();
	}
	
//	---------------------------------------------------------------------------------------------

	// common assertions
	/**
	 * verifies the visibility of element add cart button, back button, and price
	 * tag
	 */

	private void visibility_test() {
		asrt.assertTrue(proter.Visibility_checker("BACKBUTTON"), "button not visible.");
		asrt.assertTrue(proter.Visibility_checker("CARTBUTTON"), "butto not visible.");
		asrt.assertTrue(proter.Visibility_checker("PRICE"), "price tag not visible.");
	}

	/**
	 * verify the add to cart functionality
	 */

	private void addtocart_check() {
		UniversalareaPom uniter = new UniversalareaPom(driver);
		asrt.assertEquals(proter.clickAddToCart(), "REMOVE");
		asrt.assertEquals(uniter.circleIconInCart().getText(), "1");
		asrt.assertEquals(proter.clickAddToCart(), "ADD TO CART");

	}

	/**
	 * verifies the back button functionality
	 */

	private void backButton_check() {
		proter.clickBackButton();
		String actual = driver.getCurrentUrl();
		asrt.assertEquals(actual, pred.getProperty("homepageUrl"),
				"unexpected behaviour.check that it follows navigate back test passed or not.");
		asrt.assertEquals(logpage, driver.getCurrentUrl(),
				"it actually did not follow navigate back path.so this is a strong flow error.");
	}

	/**
	 * 
	 * @param keyname-the prodsuct name you want to tally
	 */
	private void name_price_tally(String keyname) {
		asrt.assertEquals(proter.get_price(), pred.getProperty(keyname));

	}

}
