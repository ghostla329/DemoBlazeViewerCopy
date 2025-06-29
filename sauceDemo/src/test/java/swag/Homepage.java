package swag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import errorthings.Listenerimplementor;
import pomClasses.HomepagePom;
import pomClasses.UniversalareaPom;

@Listeners(Listenerimplementor.class)
public class Homepage extends BaseTest {
	HomepagePom hometor;
	UniversalareaPom uniter;

	@BeforeMethod(dependsOnMethods = { "Browser_launch" })
	public void site_launch() {
		driver.get(pred.getProperty("homepageUrl"));
		hometor = new HomepagePom(driver, wait);
		uniter = new UniversalareaPom(driver);// object for universal elements
	}

	/**
	 * checks the total products present
	 */
	@Test(priority = 1)
	public void total_products_check() { // checks if the total product present matches or not

		asrt.assertEquals(hometor.totalitemCheck(), Integer.valueOf(pred.getProperty("totalitems")),
				"total number of products are not same as expected");
		asrt.assertAll();
	}

	@Test(priority = 2)
	public void visibility_check() {// checks if the elements title, price and add to cart button are visible or not
		List<String> visibilityStatus = hometor.elementsVisibility_check();
		for (String val : visibilityStatus) {
			asrt.assertTrue(val.equals("truetruetrue"), "some elements are not visible.check the location of false");
		}
		asrt.assertAll();
	}

	@Test(priority = 3)
	public void price_titleMatchCheck() {// checks the ite and their prices are shown correctly

		HashMap<String, String> map = hometor.name_price_tally();
		for (String key : map.keySet()) {
			asrt.assertEquals(map.get(key), pred.getProperty(key),
					"for " + key.replace("Url", "") + " title price do not match");
		}
		asrt.assertAll();
	}

	@Test(priority = 5)
	public void AddToCart_buttonFunction() { // che cks the add to cart button works or not.

		String numberOfItemsToAdd = pred.getProperty("product_to_add");// number o product toadd to cart
		boolean expectedStatus = true; // expected status of the visibility is true
		String buttontextexpexted = "REMOVE"; // expected test inside buton
		boolean actualStatus; // the boolean visibility status actual

		List<String> outlist = hometor.AddToCart_click(Integer.valueOf(numberOfItemsToAdd), "");// why gave the string
																								// "" is written in
																								// javdoc
		for (int i = 0; i < 2; i++) {
			for (String text : outlist) {
				asrt.assertEquals(text, buttontextexpexted, "the text of the button is not as expected");
			}

			try {
				actualStatus = uniter.give_VisibilityStatus(uniter.circleIconInCart());// the boolean status of the
																						// method
			} catch (NoSuchElementException e) {
				actualStatus = false;// if the exception occurst hen it means the element is not present. so it is
										// not visible.
			}
			asrt.assertEquals(actualStatus, expectedStatus, "the circle icon is not present");
			if (expectedStatus == false) {// if it actual status is false then there is no need to go for rest of the
											// line present
				return;
			}

			asrt.assertEquals(uniter.circleIconInCart().getText(), numberOfItemsToAdd,
					"the numebr of item added to cart are nopt matching with circle icon value");// checks if the number

			// added to cart

			expectedStatus = false; // setting value for next loop
			buttontextexpexted = "ADD TO CART"; // setting value for next loop
			hometor.AddToCart_click(Integer.valueOf(numberOfItemsToAdd), "retry");// this sets the reverse scenario
		}
		asrt.assertAll();
	}

	@Test
	public void title_to_productpage_Check() { // tests if on clicking the product title it moves to productpage or not.
		HashMap<String, String> map = hometor.clickOnTitles();// the map containing title and its url
		for (String text : map.keySet()) {
			asrt.assertEquals(map.get(text), pred.getProperty(text), "url did not match");
		}
		asrt.assertAll();
	}

	@Test
	public void sorting_checker() {// checks the sort button functionality
//option1
		hometor.sortButton_OptionSelect("Name (A to Z)");
		List<String> expected_list = hometor.SortingCheck("name");
		List<String> actual_list = Arrays.asList(pred.getProperty("productnamesString").split(","));
		asrt.assertEquals(expected_list, actual_list, "list do not match.alphabetical sorting did not happen properly");
//option 2
		hometor.sortButton_OptionSelect("Name (Z to A)");
		expected_list = hometor.SortingCheck("name");
		actual_list.sort(Comparator.reverseOrder());
		asrt.assertEquals(expected_list, actual_list,
				"list do not match.alphabetically decreasing sorting did not happen properly");
//option 3
		hometor.sortButton_OptionSelect("Price (low to high)");
		expected_list = hometor.SortingCheck("price");

		actual_list = Arrays.asList(pred.getProperty("productpriceString").split(","));
		asrt.assertEquals(expected_list, actual_list,
				"list do not match.price low to high sorting did not happen properly");
//option 4
		hometor.sortButton_OptionSelect("Price (high to low)");
		expected_list = hometor.SortingCheck("price");// somethings are wrong hee
		Collections.reverse(actual_list);

		asrt.assertEquals(expected_list, actual_list,
				"list do not match.price high to low sorting did not happen properly");
		asrt.assertAll();
	}

	@Test
	public void imag_to_productpage() { // checks if on clicking the image, moves to the product page
		List<String> outlist = hometor.clickOnImageFields();
		List<String> actuallsit = new ArrayList<String>();
		actuallsit.add(pred.getProperty("BackpackUrl"));
		actuallsit.add(pred.getProperty("Bike_LightUrl"));
		actuallsit.add(pred.getProperty("Bolt_T-ShirtUrl"));
		actuallsit.add(pred.getProperty("Fleece_JacketUrl"));
		actuallsit.add(pred.getProperty("Test.allTheThings()_T-Shirt_(Red)Url"));
		actuallsit.add(pred.getProperty("OnesieUrl"));

		asrt.assertTrue(outlist.containsAll(actuallsit), "some url are not present.some broken flow is present");
	}

	@Test
	public void image_loaded_checker() {

	}
}
