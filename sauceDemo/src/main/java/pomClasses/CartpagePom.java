package pomClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartpagePom {
	// general locators
	private By subHheader = By.className("subheader");
	private By cartItems = By.className("cart_item");
	private By cart_footer = By.className("cart_footer");
	private By pricing = By.className("inventory_item_price");
	private By itemLabel = By.className("inventory_item_name");
	private By RemoveButonOfItem = By.xpath(".//button");

//	Notesome special case scenario locators are written inside their respective methods

	private WebDriver driver;

	public CartpagePom(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement subheader() {

		return driver.findElement(subHheader);

	}

	public WebElement continue_shoppingbutton() {
		WebElement element = driver.findElement(cart_footer);
		return element.findElement(By.xpath(".//a[@class='btn_secondary']"));

	}

	public WebElement checkoutbutton() {
		WebElement element = driver.findElement(cart_footer);
		return element.findElement(By.xpath(".//a[@class='btn_secondary']/following-sibling::a"));

	}

	/**
	 * 
	 * @return if exception happened then null. else returns the webelements of
	 *         container of items in cart.
	 */
	public List<WebElement> contentContainer() {
		List<WebElement> elmnt;
		elmnt = driver.findElements(cartItems);
		return elmnt;

	}

	/**
	 * 
	 * @param itemlabel- null if you want to remove all.partial/complete label of
	 *                   the item to remove for specific item removal
	 * @return
	 */
	public List<WebElement> removeButtoninItem(List<String> itemlabel) {
		List<WebElement> containers = contentContainer();
		List<WebElement> subelements = new ArrayList<WebElement>();
		if (itemlabel == null || itemlabel.isEmpty()) {
			for (WebElement eli : containers) {
				subelements.add(eli.findElement(RemoveButonOfItem));
			}
		} 
		else {
			for(String strng:itemlabel) {
				WebElement element = driver.findElement(By.xpath("//div[contains(text(),'"+strng+"')]/../..//following-sibling::button"));
				subelements.add(element);
				
			}
			
		}
		return subelements;
	}

	/**
	 * 
	 * @return the map of the webelement of label of products and the price of the
	 *         products
	 */
	public HashMap<WebElement, String> title_PriceOfItems() {
		List<WebElement> containers = contentContainer();
		HashMap<WebElement, String> map = new HashMap<>();
		for (WebElement eli : containers) {
			String price = eli.findElement(pricing).getText();
			WebElement element = eli.findElement(itemLabel);
			map.put(element, price);
		}
		return map;// for further processing
	}

}
