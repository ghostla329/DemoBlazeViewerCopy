package pomClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Productpage_generic_pom {
	private WebDriver driver;
	private WebDriverWait wait;

	public Productpage_generic_pom(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	private By AddtoCartButton = By.xpath("//div[@class='inventory_details_desc_container']/button");
	private By backbutton = By.className("inventory_details_back_button");
	private By price = By.className("inventory_details_price");
	

	private WebElement find_field(By locator) {
		return driver.findElement(locator);
	}

	/**
	 * 
	 * @param elemntname -It can be BACKBUTTON/CARTBUTTON/PRICE.anything else throws
	 *                   exception
	 * @return boolean(if element is visible or not)
	 */
	public boolean Visibility_checker(String elemntname) {
		Boolean val = null;
		if (elemntname.toUpperCase().equals("BACKBUTTON")) {
			val = find_field(backbutton).isDisplayed();
		} else if (elemntname.toUpperCase().equals("CARTBUTTON")) {
			val = find_field(AddtoCartButton).isDisplayed();
		} else if (elemntname.toUpperCase().equals("PRICE")) {
			val = find_field(price).isDisplayed();
		}
		return val;
	}

	public String clickAddToCart() {
		WebElement element = find_field(AddtoCartButton);
		element.click();
		wait.until(ExpectedConditions.elementToBeClickable(AddtoCartButton));
		return element.getText();
	}

	public void clickBackButton() {
		find_field(backbutton).click();
	}
	public String get_price() {
		return find_field(price).getText();
		
		
	}
}
