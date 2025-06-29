package swag;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import errorthings.Listenerimplementor;
import pomClasses.CartpagePom;
import pomClasses.HomepagePom;
import pomClasses.UniversalareaPom;

@Listeners(Listenerimplementor.class)

public class Cartpage extends BaseTest {
	private CartpagePom carter;

	@BeforeMethod
	public void site_launch() {
		driver.get(pred.getProperty("cartpageUrl"));
		carter = new CartpagePom(driver);
	}

	@Test
	public void continueShoppingButtonTest() { // verifies if continue shopping button takes you back to home page
		WebElement element = carter.continue_shoppingbutton();
		visibility_test(element);
		asrt.assertEquals(element.getText(), "Continue Shopping".toUpperCase());
		element.click();
		assertEquals(driver.getCurrentUrl(), pred.getProperty("homepageUrl"));
		asrt.assertAll();
	}

	@Test // verifies the subheader text is as expected
	public void subheaderCheck() {
		WebElement element = carter.subheader();
		visibility_test(element);
		assertEquals(element.getText(), "Your Cart");
		asrt.assertAll();
	}

	@Test
	public void CheckoutButtonTest() { // verifies checkout button functionality
		WebElement element = carter.checkoutbutton();
		visibility_test(element);
		asrt.assertEquals(element.getText(), "CHECKOUT");
		element.click();
		assertEquals(driver.getCurrentUrl(), pred.getProperty("checkoutpageUrl"),
				"it did not go to desirred page.url don't match");
		asrt.assertAll();
	}

	@Test // this one needs a little optimization.it is slow
	public void beforeadding_cart_contentTest() {// Tests if before adding anything t cart container is empty
		List<WebElement> elements = carter.contentContainer();
		if (elements.isEmpty()) {
			elements = null;
		}
		assertNull(elements);
		asrt.assertAll();
	}

	@Test
	public void afteradding_cart_contentTest() {
		int numbertoadd = 4;
		continueShoppingButtonTest(); // clcicked on continue shopping button
		new HomepagePom(driver, wait).AddToCart_click(numbertoadd, "");// selected 4 items
		new UniversalareaPom(driver).cart_icon().click(); // clicked on cart icon
		List<WebElement> elements = carter.contentContainer(); // found the the element present inside cart
		asrt.assertEquals(elements.size(), numbertoadd, "something is wrong in the flow");

		for (WebElement eli : elements) {
			visibility_test(eli);
		} // performed visibility test of elements

		List<WebElement> storedList = carter.removeButtoninItem(null);// list of remove biuttons
		for (WebElement eli : storedList) {
			eli.click();// rem0ved items from cart
		}
		asrt.assertEquals(carter.contentContainer().size(), 0);// check if containeris blank or not

		HashMap<WebElement, String> map = carter.title_PriceOfItems();
		for (WebElement eli : map.keySet()) {
			String key = eli.getText().replace("Sauce Labs ", "").replace(" ", "");// formatting of the string to make a
																					// key
			asrt.assertEquals(map.get(eli), pred.getProperty(key + "Price"));

			eli.click();
			asrt.assertEquals(driver.getCurrentUrl(), pred.getProperty(key) + "Url",
					"on clicking label it does not goback to its product page");
			asrt.assertAll();
		}
	}

	/**
	 * 
	 * @param element the webelement whose visibility to be checked.
	 * @implNote assertion checks that element is present. if not present then
	 *           throws null. if present then,if it is visible or not.
	 */
	private void visibility_test(WebElement element) {// checks if element is visible
		assertTrue(element.isDisplayed(), "element is not visible");// elementis visible
	}

}
