package pomClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPom {
	WebDriver driver;

	public LoginPom(WebDriver driver) {
		this.driver = driver;

	}

	private By usernameField = By.id("user-name");
	private By passwordField = By.id("password");
	private By LoginButton = By.id("login-button");
	private By errorButton = By.className("error-button");
	private By errormsg = By.xpath("//h3");

	private WebElement find_field(By locator) {
		return driver.findElement(locator);
	}

//actions
	public void Giveusername(String name) {
		find_field(usernameField).sendKeys(name);
	}

	/**
	 * gives the password to passwordfield
	 * 
	 * @param pass the p[assword value
	 */
	public void Givepassword(String pass) {
		find_field(passwordField).sendKeys(pass);
	}

	public WebElement getPasswordField() {
		return find_field(passwordField);
	}

	public void clicklogin() {
		find_field(LoginButton).click();
	}

	public boolean loginVisibility() {
		return find_field(LoginButton).isDisplayed();
	}

	public boolean usernamefieldVisibility() {
		return find_field(usernameField).isDisplayed();
	}

	public boolean passwordfieldVisibility() {

		return find_field(passwordField).isDisplayed();
	}

	public WebElement errorButton() {
		return find_field(errorButton);
	}

	public String errormessage() {
		WebElement element = find_field(errormsg);
		return element.getText();
	}

}
