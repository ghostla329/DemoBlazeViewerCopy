package pomClasses;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SummarypagePom {
	private By subheaer = By.className("subheader");
	private By cancelButton = By.xpath("//a[text()='CANCEL']");
	private By finishButton = By.xpath("//a[text()='FINISH']");
	private By cartitems = By.xpath("//div[@class='cart_item']");
	private By subtotal = By.className("summary_subtotal_label");
	private By tax = By.className("summary_tax_label");
	private By total = By.className("summary_total_label");
	private By shipper = By
			.xpath("//div[text()='Shipping Information:']/following-sibling::div[@class='summary_value_label']");
// some locators i ignored
	private WebDriver driver;

	public SummarypagePom(WebDriver driver) {
		this.driver = driver;
	}

	public String giveSubheadertexrt() {
		return driver.findElement(subheaer).getText();
	}

	public WebElement cancelButton() {
		return driver.findElement(cancelButton);
	}

	public WebElement finishButton() {
		return driver.findElement(finishButton);
	}

	public float subtotal() {
		return Float.valueOf(driver.findElement(subtotal).getText().replace("Item total: $", ""));
	}

	public float tax() {
		return Float.valueOf(driver.findElement(tax).getText().replace("Tax: $", ""));
	}

	public float total() {
		return Float.valueOf(driver.findElement(total).getText().replace("Total: $", ""));
	}

	public String shippername() {
		return driver.findElement(shipper).getText();
	}

	
}
