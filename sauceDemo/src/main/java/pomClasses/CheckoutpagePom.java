package pomClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutpagePom {
	private By firstnameField = By.id("first-name");
	private By lastnamefield = By.id("last-name");
	private By zipcode = By.id("postal-code");
	private By Continue_Btn = By.xpath("//input[@type='submit']");
	private By cancel_btn = By.xpath("//div[@class='checkout_buttons']/a");
	private By error_BtnIcon = By.xpath("//h3/button");
	private By error_msg = By.xpath("//h3");
	private By subheader=  By.className("subheader");

	WebDriver driver;

	public CheckoutpagePom(WebDriver driver) {
		this.driver = driver;
	}

	public void givefirstnme(String input) {
		driver.findElement(firstnameField).sendKeys(input);
		;
	}

	public void givelastname(String input) {
		driver.findElement(lastnamefield).sendKeys(input);
	}

	public void givezipcode(String input) {
		driver.findElement(zipcode).sendKeys(input);
	}

	public void clickContinueButton() {
		driver.findElement(Continue_Btn).click();

	}

	public void clickCancelButton() {
		driver.findElement(cancel_btn).click();

	}

	public String errormessage() {
		return driver.findElement(error_msg).getText();
	}

	public WebElement errorButton() {
		return driver.findElement(error_BtnIcon);
	}
	public String giveSubheader() {
		return driver.findElement(subheader).getText();
	}
}
