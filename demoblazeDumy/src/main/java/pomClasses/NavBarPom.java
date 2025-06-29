package pomClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavBarPom {
	private WebDriver driver;
	WebDriverWait wait;

// constructor
	public NavBarPom(WebDriver driver,WebDriverWait wait) {
		this.driver = driver;
		this.wait=wait;
	}

//chweck if th econtainer is present
	public boolean navBarContainerVisibility() {
		return driver.findElement(By.tagName("nav")).isDisplayed();
	}

//	the webelement of the logo
	public WebElement getLogo() {
		return driver.findElement(By.className("navbar-brand"));
	}

	public WebElement getHomeLabel() {
		return finder(By.xpath("//a[contains(text(),'Home')]"));
	}

	public WebElement getContactLabel() {
		return finder(By.xpath("//a[contains(text(),'Contact')]"));
	}
//	---------------------------subelement-----------------------------------

//	-------------------------------------------------------------------------
	public WebElement getAboutUsLabel() {
		return finder(By.xpath("//a[contains(text(),'About us')]"));
	}
//------------------------------subelement-------------

//-----------------------------------------------------

	public WebElement getCartLabel() {
		return finder(By.xpath("//a[contains(text(),'Cart')]"));
	}

	public WebElement getSignUpLabel() {
		return finder(By.xpath("//a[contains(text(),'Sign up')]"));
	}

//	---------------------------subelement-----------------------------------
	/**
	 * 
	 * @return the mini window webelement of signup
	 */
	public WebElement signupMiniWindow() {
		return driver.findElement(By.xpath("//div[@id='signInModal']/div/div"));
	}

	public WebElement signup_getCloseIcon() {
		return signupMiniWindow().findElement(By.xpath(".//button[@class='close']"));
	}

	public WebElement signup_getCloseButton() {
		return signupMiniWindow().findElement(By.xpath(".//button[text()='Close']"));
	}

	public WebElement signup_getSignUpButton() {
		return signupMiniWindow().findElement(By.xpath(".//button[text()='Sign up']"));
	}

	public WebElement signup_getUsernamefield() {
		return signupMiniWindow().findElement(By.id("sign-username"));
	}

	public WebElement signup_getPasswordfield() {
		return signupMiniWindow().findElement(By.id("sign-password"));
	}
//	-------------------------------------------------------------------------

	public WebElement getLoginLabel() {
		return finder(By.xpath("//a[contains(text(),'Log in')]"));
	}
//	---------------------------subelement-----------------------------------

//	-------------------------------------------------------------------------
	/**
	 * @implNote- use this only when you know that element alaways visible.
	 * @param locator
	 * @return the webe leemnt ifit is vissible.else throws runtimre exception
	 */
	private WebElement finder(By locator) {
		WebElement element = driver.findElement(locator);
		wait.until(ExpectedConditions.visibilityOf(element));
		if (element.isDisplayed()) {
			return element;
		} else
			throw new RuntimeException("the elemnt is not visible.but it is present.that is why this custom exception");
	}
}
