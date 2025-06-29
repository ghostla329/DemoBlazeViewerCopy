package pomClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProdPageGenericPom {
	private WebDriver driver;

	public ProdPageGenericPom(WebDriver driver) {
		this.driver = driver;
	}

	private By addToCart = By.xpath("//div/a[text()='Add to cart']");
	private By productName = By.xpath("//h2");
	private By price = By.xpath("//h3");
	private By description = By.xpath("//div[@id='myTabContent']//p");
	

	public WebElement getAddcartButton() {
		return driver.findElement(addToCart);
	}

	/**
	 * 
	 * @return the name of the ptroduct in the productpage
	 */
	public String getProductName() {
		return driver.findElement(productName).getText();
	}

	/**
	 * 
	 * @return the item price in the product page
	 */
	public String getPrice() {
		return driver.findElement(price).getText();
	}

	public String getDescription() {
		return driver.findElement(description).getText();
	}

}
