package pomClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UniversalareaPom {
	private By cartIcon = By.id("shopping_cart_container");
	private By numberOfItemInCart = By.xpath("//a/span");
	private By drawer = By.className("bm-burger-button");
	private By allitem = By.id("inventory_sidebar_link");
	private By logout = By.id("logout_sidebar_link");
	

	private WebDriver driver;
	

	public UniversalareaPom(WebDriver driver) {// constructor fot the class
		this.driver = driver;
		
	}



//microactions
	public boolean give_VisibilityStatus(WebElement elementl) {
		return elementl.isDisplayed();
	}

	public WebElement cart_icon() {
		return driver.findElement(cartIcon);
	}

	public WebElement drawerButton() {
		
		return driver.findElement(drawer);
	}

	public WebElement sidebarOption_logout() {
		return driver.findElement(logout);
	}
	
	public WebElement sidebarOption_allItemt() {
		return driver.findElement(allitem);
	}
	
	public WebElement circleIconInCart() {
		return driver.findElement(numberOfItemInCart);
	}
}
