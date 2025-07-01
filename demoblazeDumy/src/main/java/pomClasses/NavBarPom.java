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
	public NavBarPom(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	/**
	 * 
	 * @param locator to finds the element when visisble
	 * @return the WEbelemenet
	 */
	private WebElement findWhenVisible(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return driver.findElement(locator);
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
		return driver.findElement(By.xpath("//a[contains(text(),'Home')]"));
	}

//------------------------contact label----------------------------------------
	public WebElement getContactLabel() {

		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Contact')]"));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element;

	}

//	---------------------------sub-element-----------------------------------

	/** returns the visisbility status of thre popup */
	public boolean contactPopupWindowvisibility() {
		return driver.findElement(By.id("exampleModalLabel")).isDisplayed();
	}

	public WebElement getContactEmailField() {
		return findWhenVisible(By.id("recipient-email"));
	}

	public WebElement getContactNameField() {
		return findWhenVisible(By.id("recipient-name"));
	}

	public WebElement getContactMessageField() {
		return findWhenVisible(By.id("message-text"));
	}

	public WebElement getContactSendMessageButton() {
		return findWhenVisible(By.xpath("//button[text()='Send message']"));
	}

	public WebElement getContactCloseButton() {
		return driver
				.findElement(By.xpath("//button[text()='Send message']/preceding-sibling::button[text()='Close']"));
	}

	public WebElement getContactCloseIcon() {
		return findWhenVisible(By.xpath("//h5[@id='exampleModalLabel']/following-sibling::button"));
	}

//	-----------------------------About us--------------------------------------------
	public WebElement getAboutUsLabel() {
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'About us')]"));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element;
	}

//------------------------------subelement----------------------
	public boolean aboutPopupWindowvisibility() {
		return driver.findElement(By.xpath("//h5[text()='About us']/../..")).isDisplayed();
	}

	public WebElement getAbouCloseButton() {
		return findWhenVisible(By.xpath("//h5[text()='About us']/../..//button[text()='Close']"));
	}

	public WebElement getAboutCloseIcon() {
		return findWhenVisible(By.xpath("//h5[text()='About us']/following-sibling::button"));
	}
//-----------------------------------------------------

	public WebElement getCartLabel() {
		return findWhenVisible(By.xpath("//a[contains(text(),'Cart')]"));
	}

	public WebElement getSignUpLabel() {
		return findWhenVisible(By.xpath("//a[contains(text(),'Sign up')]"));
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
		return findWhenVisible(By.xpath("//a[contains(text(),'Log in')]"));
	}
//	---------------------------subelement-----------------------------------

//	-------------------------------------------------------------------------
}
