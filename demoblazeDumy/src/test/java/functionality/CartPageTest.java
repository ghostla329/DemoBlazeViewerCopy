package functionality;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import baseTest.BaseTest;
import pomClasses.CartPagePom;
import pomClasses.HomepagePom;
import pomClasses.NavBarPom;
import pomClasses.ProdPageGenericPom;
import utility.ExcelReader;
import utility.PropertiesReader;

public class CartPageTest extends BaseTest {
	List<String> toBeadddeditems = List.of(ExcelReader.getValue(4, 1, "Sheet1"), ExcelReader.getValue(7, 1, "Sheet1"));
	HomepagePom homePage;
	NavBarPom nav;
	CartPagePom cartPage;
	ProdPageGenericPom prodPage;
	String homeUrl = PropertiesReader.getProperty("homepageurl");

	@BeforeMethod(alwaysRun = true)
	public void cartPage_Prquisiste_Action() {
		driver.get(homeUrl);
		homePage = new HomepagePom(driver, explicitWait);
		prodPage = new ProdPageGenericPom(driver);
		cartPage = new CartPagePom(driver);
		nav = new NavBarPom(driver, explicitWait);
	}

	@Test(groups = { "integration" })
	public void addToCartIntegrationCheck() {// check if the item added to the cart show up as it is in the cart
												// page.verify titleand price match
		HashMap<String, String> addedItmsAndPrice = addProductsToCart(toBeadddeditems);// the itmes that are added to
																						// cart
		// go to the cart page
		nav.getCartLabel().click();
		HashMap<String, String> cartItems = cartPage.getTableItemNamesAndPrices();// the item that are present in cart
		softAsrt.assertEquals(cartItems, addedItmsAndPrice);
		softAsrt.assertEquals(cartItems.keySet().size(), addedItmsAndPrice.keySet().size());
		softAsrt.assertAll();
	}

	@Test(groups = { "smoke" })
	public void noItemAddCartTestIntegrationTest() {// check by default the cart is empty
		driver.get(PropertiesReader.getProperty("cartpageurl"));
		softAsrt.assertTrue(cartPage.productListIsempty());
		softAsrt.assertAll();
	}

	@Test(groups = { "smoke" })
	public void cartItemDeletButtonTest() {// check if the delete button works perfectly
//		add items and move into cart
		addProductsToCart(toBeadddeditems);
		// go to cart
		nav.getCartLabel().click();
		for (String s : toBeadddeditems) {
			// click on delet button
			WebElement deleteIcon = cartPage.deleteIcon(s);
			deleteIcon.click();

		}
		softAsrt.assertTrue(cartPage.productListIsempty(), "the delete icon not work.severity-blocker, priority-high");
		softAsrt.assertAll();
	}

	@Test(groups = { "Smoke" })
	public void cartTotalPriceToPayTest() {// check if the total price shown is correct
		// add products to Cart
		addProductsToCart(toBeadddeditems);
		int actualTotal = 0;
//		move to cart
		nav.getCartLabel().click();
		HashMap<String, String> namesAndPrices = cartPage.getTableItemNamesAndPrices();

		for (String price : namesAndPrices.values()) {
			actualTotal += Integer.valueOf(price);
		}
		//we wrapped the integer cause this threw compilation error while running from terminal.so to match the type. we did this.
		//we just wrapped this integer.
		softAsrt.assertEquals(Integer.valueOf(actualTotal), Integer.valueOf(cartPage.getTotalPrice()));
	}

	@Test(groups = { "smoke" })
	public void placeOrderButtonTest() {// check if place order button opens the popupand reflect the total price or not
		driver.get(PropertiesReader.getProperty("cartpageurl"));
		cartPage.getPlaceOrderButton().click();
		softAsrt.assertEquals(cartPage.placeOrderPopupWindowHeader(), "Place order",
				"popup window header do not match");
		softAsrt.assertAll();
	}

	@Test
	public void placeOrderWindowCloseIconTest() {// check if the close button inside thepopup matches
		// first validated the coorect popup reached
		placeOrderButtonTest();
		// clicked close icon
		cartPage.placeOrder_PopWindowCloseIcon().click();

		softAsrt.assertFalse(cartPage.placeOrderWindowVisiblliyty());
		softAsrt.assertAll();
	}

	@Test(groups = { "integration" })
	public void placeOrder_ToatalPriceToBePaidIntegrationTest() {// check if the total price to be paid matches with the
																	// expected value
		goTocartPageAfterAddingProducts_Action();
		// getTotal price
		String expected = cartPage.getTotalPrice();
		// click on placeorder button
		cartPage.getPlaceOrderButton().click();

		String actual = cartPage.placeOrder_PopWindowTotal().replace("Total: ", "");
		softAsrt.assertEquals(actual, expected);
		softAsrt.assertAll();
	}

	@Test(groups = { "smoke", "integration" })
	public void placeOrder_toalPriceToBePaidNegativeScenarioTest() {// check if the integrationworksin negative scenario
		// wenr into cart page
		driver.get(PropertiesReader.getProperty("cartpageurl"));
		// clicked on place order
		cartPage.getPlaceOrderButton().click();
		String actual = cartPage.placeOrder_PopWindowTotal().replace("Total:", "");
		softAsrt.assertTrue(actual.isBlank());
		softAsrt.assertAll();
	}

	@Test
	public void placeOrder_PopWindowCloseButtonTest() {// check if the close button closes the window
		driver.get(PropertiesReader.getProperty("cartpageurl"));
		cartPage.getPlaceOrderButton().click();
		cartPage.placeOrder_PopWindowCloseButton().click();
		softAsrt.assertFalse(cartPage.placeOrderWindowVisiblliyty());
		softAsrt.assertAll();
	}

	@Test(groups = { "smoke" })
	public void placeBlankOrdeComletion() {// check if the order is place or not
		// blank case
		driver.get(PropertiesReader.getProperty("cartpageurl"));
		// clicked on place order
		cartPage.getPlaceOrderButton().click();
		cartPage.placeOrder_PopWindowPurchaseButton().click();
		softAsrt.assertFalse(cartPage.confirmPurchaseWindowAppeared());
	}

	@Test(dataProvider = "someCredentials")
	public void placeValidOrder(String name, String cardDetail, boolean shouldPass) {
		goTocartPageAfterAddingProducts_Action();
		cartPage.getPlaceOrderButton().click();
		String totalPriceToPay = cartPage.getTotalPrice();
		// give cedenntial and click purchase
		cartPage.givePurchaseCredentials(name, cardDetail);
		cartPage.placeOrder_PopWindowPurchaseButton().click();

		softAsrt.assertEquals(cartPage.confirmPurchaseWindowAppeared(), shouldPass,
				"the expected visibility status of the popup window so not match ");
		softAsrt.assertAll();

		String recipt = cartPage.purchaseReeciptDetails();

		softAsrt.assertTrue(recipt.contains("Name: " + name), "recipt is incorrect");
		softAsrt.assertTrue(recipt.contains("Card Number: " + cardDetail), "recipt is incorrect");
		softAsrt.assertTrue(recipt.contains("Amount: " + totalPriceToPay), "recipt is incorrect");
		softAsrt.assertFalse(cartPage.placeOrder_PopWindowPurchaseButton().isEnabled(),
				"button works.this is a critical error");// to check if the purchase button shown is disabled
		softAsrt.assertFalse(cartPage.placeOrder_PopWindowCloseIcon().isEnabled(),
				"button works.this is a critical error");
		softAsrt.assertAll();

	}

	@Test(groups = { "smoke" })
	public void reciptOkButtonTest() {// check if the ok button inrecipt works properly
		goTocartPageAfterAddingProducts_Action();
		cartPage.getPlaceOrderButton().click();
		cartPage.givePurchaseCredentials("dummy", "43535");
		cartPage.placeOrder_PopWindowPurchaseButton().click();

		explicitWait.until(ExpectedConditions.urlToBe(homeUrl));
		softAsrt.assertEquals(driver.getCurrentUrl(), homeUrl, "the ok button does not function properly");
	}

	@Test(groups = "smoke")
	public void placeOrderWithoutManadatoryField() {// check if not giving anything as input make the alert popout
		// go and add products to cart
		goTocartPageAfterAddingProducts_Action();
//	place order button click
		cartPage.getPlaceOrderButton().click();
		// give cedenntial and click purchase
		cartPage.givePurchaseCredentials("", "");
		cartPage.placeOrder_PopWindowPurchaseButton().click();
		// wait ot alert appear
		explicitWait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		softAsrt.assertTrue(alert.getText().contains("Please fill out Name and Creditcard"),
				"the alert is not what we expected");
		alert.accept();
		softAsrt.assertAll();
	}

	/**
	 * 
	 * @return and 2d array with3 fileds name card number and bolean status of if
	 *         test should pass or not
	 */
	@DataProvider
	public Object[][] someCredentials() {
		Object[][] data = new Object[4][3];// 3 column name,card number and error
		String validName = "Tylor";
		data[0][0] = validName;
		data[0][1] = "546546";
		data[0][2] = true;
		data[1][0] = validName;
		data[1][1] = "sdsffs";
		data[1][2] = false;
		data[2][0] = "3432342";
		data[2][1] = "2423432";
		data[2][2] = false;
		data[3][0] = "53543535";
		data[3][1] = "sdfsf";
		data[3][2] = false;
		return data;
	}

//--------------------------------------------------helpers-----------------------------------------
	/**
	 * 
	 * @return the map of item name as key and price as value. also itmoves into the
	 *         cart page.so the current active page will be cart page.
	 */
	private HashMap<String, String> addProductsToCart(List<String> itemToAdd) {
		HashMap<String, String> itemMap = new HashMap<String, String>();
		for (String name : itemToAdd) {
			WebElement element = homePage.findElementByName(name);
			itemMap.put(name, homePage.getVisaibleItemPriceFromname(name));
			// go the product page
			element.click();
			// add to cart
			WebElement AddCartButton = prodPage.getAddcartButton();
			explicitWait.until(ExpectedConditions.visibilityOf(AddCartButton));
			AddCartButton.click();
			// handle the alert
			explicitWait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
			// comeback tohomepage
			driver.navigate().to(homeUrl);
		}
		return itemMap;
	}

	/**
	 * this is helper action that adds the product to cart and navigates to cart
	 * page
	 */
	private void goTocartPageAfterAddingProducts_Action() {
		addProductsToCart(toBeadddeditems);
		nav.getCartLabel().click();
		// wait until a product is visblew in cart

	}
}
