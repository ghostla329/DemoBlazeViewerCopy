package endToEndScenarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pomClasses.CartpagePom;
import pomClasses.CheckoutpagePom;
import pomClasses.HomepagePom;
import pomClasses.LoginPom;
import pomClasses.SummarypagePom;
import pomClasses.UniversalareaPom;
import swag.BaseTest;

public class endToEndScenarios extends BaseTest {
	LoginPom log;
	HomepagePom home;
	UniversalareaPom uniarea;
	CartpagePom cart;
	CheckoutpagePom checkoout;
	SummarypagePom summary;
	String loginPageUrl = pred.getProperty("loginpageUrl");

	@BeforeMethod
	private void site_launch() {
		log = new LoginPom(driver);
		home = new HomepagePom(driver, wait);
		uniarea = new UniversalareaPom(driver);

		driver.get(loginPageUrl);// went into the browser

		cart = new CartpagePom(driver);
		checkoout = new CheckoutpagePom(driver);
		summary = new SummarypagePom(driver);

	}
	

	@Test(priority=1)
	@Parameters({"username","password"})
	public void fullLoginFlow(String user,String pass) {// test the full login to checkout flow

//		first user logged in with problemb user normal user credential
		loggedIn(user, pass);
//		then he went and  then choose bought a bike light.	
		List<String> list = Arrays.asList("Bike Light", "Onesie");
		home.itemadder(list);
		asrt.assertEquals(uniarea.circleIconInCart().getText(), String.valueOf(list.size()),
				"the number of item present do not match");
//		then he went o cart
		uniarea.cart_icon().click();
//		confirmed the order
		float outSubtotal = giveAllItemTotalValue();
		cart.checkoutbutton().click();
		gaveAdress();
		verifiedPurchase(outSubtotal);

		lastStep();
		loggedoutFromDrawer();
	}

	@Test
	@Parameters({"username","password"})
	public void loggedInAndLoggedOut(String user, String pass) {// user justt logged in and logged out
		loggedIn(user, pass);
		// then logged out
		loggedoutFromDrawer();
	}

	@Test(dataProvider = "testdata")
	@Parameters({"username","password"})
	public void LeftAfterAddingTocart(String user, String pass) {// checks when after adding user leaves and then ocomes
																	// for checkout
		loggedIn(user, pass);
		List<String> list = Arrays.asList("Bike Light", "Onesie", "Bolt T-Shirt");
		home.itemadder(list);// added them to cart
		asrt.assertEquals(uniarea.circleIconInCart().getText(), String.valueOf(list.size()),
				"the number of item present do not match");
		loggedoutFromDrawer();
		// again he logged in again
		loggedIn(user, pass);
		try {
			uniarea.circleIconInCart();
		} catch (NoSuchElementException e) {
			// this means unexpected behaviour is shown
			asrt.fail();
		}
		asrt.assertEquals(uniarea.circleIconInCart().getText(), String.valueOf(list.size()),
				"the number of item present do not match");
		// clicked on cart icon
		uniarea.cart_icon().click();

		assertEquals(driver.getCurrentUrl(), pred.getProperty("cartpageUrl"), "maybe due to appearance of alert");
		HashMap<WebElement, String> map = cart.title_PriceOfItems();
		assertFalse(map.isEmpty(), "there are no element added to cart");

		for (WebElement eli : map.keySet()) {
			boolean present = false;// if the item is present incart pafge
			for (int i = 0; i < list.size(); i++) {
				try {
					assertFalse(eli.getText().contains(list.get(i)));
				} catch (AssertionError e) {
					present = true;
				}
			}
			asrt.assertTrue(present, "item is not added to cart");
		}
		// checks out
		cart.checkoutbutton().click();
		gaveAdress();
		// complete this
		float value = giveAllItemTotalValue();
		verifiedPurchase(value);
		lastStep();

	}

	@Test
	@Parameters({"username","password"})
	public void AddeditemTocartThenRemoved(String user, String pass) {
		loggedIn(user, pass);
		List<String> list = new ArrayList<String>(Arrays.asList("Bike Light", "Onesie", "Bolt T-Shirt"));
		home.itemadder(list);// added them to cart
		asrt.assertEquals(uniarea.circleIconInCart().getText(), String.valueOf(list.size()),
				"the number of item present do not match");
		uniarea.cart_icon().click();
		// remove two uitem
		list.remove(1);
		for (WebElement eli : cart.removeButtoninItem(list)) {
			eli.click();
		}
		asrt.assertEquals(uniarea.circleIconInCart().getText(), String.valueOf(list.size()),
				"the number of item present do not match.it is a bug");

		float value = giveAllItemTotalValue();
		assertFalse(cart.contentContainer().isEmpty());
		cart.checkoutbutton().click();
		gaveAdress();
		verifiedPurchase(value);
		lastStep();

	}

	@Test
	@Parameters({"username","password"})
	public void afterGoingToCarChangeMind(String user, String pass) { // after going to cart cahnges hismind removes all
																		// item then goes back to
		// home
		// page using continue shopping or driver.navigae back

		loggedIn(user, pass);// logged into site
		// product to add to cart
		List<String> listItems = new ArrayList<String>(
				Arrays.asList("Bike Light", "Onesie", "Fleece Jacket", "Bolt T-Shirt"));
		List<String> removedItems = new ArrayList<String>();
//		added them to cart

		home.itemadder(listItems);
		asrt.assertEquals(uniarea.circleIconInCart().getText(), String.valueOf(listItems.size()),
				"the number of item present do not match");
		// enter into cart
		uniarea.cart_icon().click();

		removedItems.add(listItems.remove(1));// removed onesie.note now the list got updated
		removedItems.add(listItems.remove(2));// removed bolt shirt

		for (WebElement eli : cart.removeButtoninItem(listItems)) {
			eli.click();
		} // removed the desired item
//		check the changes are reflected in circleicon or not

		asrt.assertEquals(uniarea.circleIconInCart().getText(), String.valueOf(listItems.size()),
				" the circle icon status not updated");
//		pressed back button

		driver.navigate().back();
		// check the chanes in item are reflected or not
		asrt.assertEquals(uniarea.circleIconInCart().getText(), String.valueOf(listItems.size()),
				"circle icon text do not tally with number of item in cart by navigating back though back button");

		removedItems = home.ButtnIconGrab(removedItems);
		for (String strng : removedItems) {
			asrt.assertEquals(strng, "ADD TO CART", "text status inside the button is n0t updated");
		}

//		logged out

		loggedoutFromDrawer();
		asrt.assertEquals(driver.getCurrentUrl(), loginPageUrl, "did not return to login page");// reached the logiin
																								// page
	}

//----------------------------------------------------------------------------------
	private void lastStep() {
		summary.finishButton().click();
		assertEquals(driver.getCurrentUrl(), pred.getProperty("finalpageUrl"),
				"final page not reached.flow got broken");// finish page reached
	}

	private void verifiedPurchase(float outSubtotal) {
		asrt.assertTrue(String.valueOf(summary.subtotal()).contains(String.valueOf(outSubtotal)));
		DecimalFormat df = new DecimalFormat("0.00");

		Float outTotal = Float.valueOf(outSubtotal) + Float.valueOf(summary.tax());

		asrt.assertEquals(summary.total(), df.format(outTotal), // there is a slight error in this code
				"there is an error in pricing calculation");
	}

	private void gaveAdress() {
		checkoout.givefirstnme("Martin");
		checkoout.givelastname("Luther");
		checkoout.givezipcode("789065");
		checkoout.clickContinueButton();
	}

	private void loggedIn(String name, String pass) {
		log.Giveusername(name);
		log.Givepassword(pass);
		log.clicklogin();

	}

	private void loggedoutFromDrawer() {
		uniarea.drawerButton().click();// clicked on drawwer
		uniarea.sidebarOption_logout().click();// clicked lon llogout button
		asrt.assertEquals(driver.getCurrentUrl(), loginPageUrl, "logout failure");
	}

	private float giveAllItemTotalValue() {
		HashMap<WebElement, String> map = cart.title_PriceOfItems();
		float outSubtotal = 0;
		for (WebElement eli : map.keySet()) {
			Float addable = Float.valueOf(map.get(eli).replace("$", ""));
			outSubtotal = outSubtotal + addable;
		}
		return outSubtotal;
	}
}
