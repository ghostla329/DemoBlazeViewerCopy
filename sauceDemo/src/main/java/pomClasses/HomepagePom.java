package pomClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomepagePom {
	WebDriver driver;
	private List<WebElement> elements=new ArrayList<WebElement>();;
	WebDriverWait wait;
	private List<String> comparorlist = new ArrayList<String>();
	private Select sel;

	/**
	 * 
	 * do not initialize globally.only declare then later initialize.cause it got
	 * constructer.which can cause failure
	 * 
	 * parameter wait- is the webdriver wait object in Base_test
	 */

	public HomepagePom(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	private By mainContainer = By.className("inventory_item");// locator for container. always find it first
	private By item_labels = By.className("inventory_item_name");
	private By item_img = By.className("inventory_item_img");
	private By item_price = By.className("inventory_item_price");
	private By add_cartButton = By.xpath(".//div/button");
	private By SortButton = By.className("product_sort_container");

	/**
	 * 
	 * @param locator
	 * @return the single webelement
	 */
	private List<WebElement> Find_Multifield(By locator) {

		return driver.findElements(locator);
	}

//return multi element
	private WebElement find_fieldin(WebElement webeli, By locator) {
		if (webeli == null) {
			return driver.findElement(locator);
		} else {
			return webeli.findElement(locator);
		}

	}
//clicks on add carty button of element
	/**
	 * 
	 * @param num - number of item to click
	 * @param reverseCheck -to check the reverse condition.if true then it checks
	 */
	private void addingToCart(int num, Boolean reverseCheck) {//adds the items presentin elements variable items to cart
		for (int i = 0; i < num; i++) {// iterated
			
			WebElement element = find_fieldin(elements.get(i), add_cartButton);// found add to cart button
			wait.until(ExpectedConditions.elementToBeClickable(element));// waiting for element to be clickable
			element.click();// click on add cart of the element

			if (reverseCheck == true) {
				wait.until(ExpectedConditions.elementToBeClickable(element));// waiting for element to be clickable
				comparorlist.add(element.getText());
			} // adds the text shown after clciking the add cart button}
			
		}
	}
//---------------------------------------------------------------------------------------------------------------------
//	actions
	/**
	 * @param condition for negative check give "retry" as parameter.or else you can
	 *                  give any string.
	 * @param number    tell number of items to add randomly from all items.
	 * @return the list of Stringtext shown inside th button button
	 */
	public List<String> AddToCart_click(int number, String condition) {
		comparorlist.clear();// made sure list is empty
		if (condition.equalsIgnoreCase("retry")) {

		} else {

			elements = Find_Multifield(mainContainer);
			Collections.shuffle(elements);// shuffled

		}

		addingToCart(number, true);
		return comparorlist;

	}

	

	/**
	 * 
	 * @return map of product name as key and price as the value.
	 */
	public HashMap<String, String> name_price_tally() {
		List<WebElement> parentConatiner = Find_Multifield(mainContainer);
		HashMap<String, String> map = new HashMap<String, String>();// map to sore productname and its price
		for (WebElement parent : parentConatiner) {// iterated to populate the map
			String prodprice = find_fieldin(parent, item_price).getText();
			String prodname = find_fieldin(parent, item_labels).getText().replace("Sauce Labs ", "").replace(" ", "_");
			map.put(prodname + "Price", prodprice);// returns the map

		}
		return map;
	}

	/**
	 * returns the total number of product present in page
	 */
	public Integer totalitemCheck() {
		return Find_Multifield(mainContainer).size();//
	}

	/**
	 * the method returns the valueof 3 field i.e "label","price" and "add to cart
	 * button" as concated string of boolean status.
	 * 
	 */
	public List<String> elementsVisibility_check() {
		elements = Find_Multifield(mainContainer);
		comparorlist.clear();
		for (WebElement eli : elements) {

			String s1 = String.valueOf(find_fieldin(eli, add_cartButton).isDisplayed());
			String s2 = String.valueOf(find_fieldin(eli, item_price).isDisplayed());
			String s3 = String.valueOf(find_fieldin(eli, item_labels).isDisplayed());
			comparorlist.add(s1 + s2 + s3); // storing the result of all three as string in comparator arraylist}
		}
		return comparorlist;

	}

	public void sortButton_OptionSelect(String value) {

		WebElement element = find_fieldin(null, SortButton);
		if (sel == null) {
			sel = new Select(element);
		}
		sel.selectByVisibleText(value);
		wait.until(ExpectedConditions.elementToBeClickable(SortButton));
	}

	/**
	 * 
	 * @param to get the price pass "price" as parameter. For getting label pass
	 *           "name" as parameter.
	 * @return the list of price or label based on parameter
	 */
	public List<String> SortingCheck(String val) {
		elements = Find_Multifield(mainContainer);// found the root path
		comparorlist.clear();
		for (WebElement eli : elements) {
			if (val.equals("price")) {
				comparorlist.add(find_fieldin(eli, item_price).getText());// adds the prices of the product
			} else if (val.equals("name")) {
				comparorlist.add(find_fieldin(eli, item_labels).getText());// adddd the label of the product
			}

		}
		return comparorlist;

	}

	/**
	 * 
	 * @return a map of only productname as key and its expected url of product page
	 *         as value.
	 */
	public HashMap<String, String> clickOnTitles() {
		elements = Find_Multifield(mainContainer);
		HashMap<String, String> map = new HashMap<>();
		for (WebElement eli : elements) {
			WebElement subelement = find_fieldin(eli, item_labels);
			String text = subelement.getText().replace("Sauce Labs ", "").replace(" ", "_");
			subelement.click();
			map.put(text + "Url", driver.getCurrentUrl());
			driver.navigate().back();
		}

		return map;
	}

	public List<String> clickOnImageFields() {
		elements = Find_Multifield(mainContainer);// try to optimizwe it by executng via constructor if possiible

		for (WebElement eli : elements) {
			find_fieldin(eli, item_img).click();

			comparorlist.add(driver.getCurrentUrl());
			driver.navigate().back();
		}

		return comparorlist;
	}

	/**
	 * 
	 * @param elementname- the full label/ name of the product/element
	 * @paramp whatToClick-what type to click.click the text/ clickimage of the
	 *         associated element
	 */
	public void SingleElement_click(String fullelementname, String whatToClick) {// will be useful in end toend
																					// testing
		WebElement element;
		if (whatToClick.equals("image")) {
			element = find_fieldin(null, By.partialLinkText(fullelementname));
			String text = element.getDomAttribute("id").replace("_title_link", "_img_link']/../../..");
			System.out.println("//a[@id='" + text);
			element = find_fieldin(null, By.xpath("//a[@id='" + text));
		} else {
			element = find_fieldin(null, By.partialLinkText(fullelementname));
		}
		element.click();
	}

	public void itemadder(List<String> itemsToAdd) {

		for (String label : itemsToAdd) {
			WebElement item = driver.findElement(By.xpath("//div[contains(text(),'" + label + "')]/../../.."));
			elements.add(item);
		}
		addingToCart(elements.size(),false);
		return;
	}
	
	public List<String> ButtnIconGrab(List<String>items) {
		List<String> buttonText=new ArrayList<String>();
		for(String label:items) {
			
			buttonText.add(driver.findElement(By.xpath("//div[contains(text(),'"+label+"')]/../../..//button")).getText());
		}
		return buttonText;
	} 
}
