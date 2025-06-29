package pomClasses;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPagePom {
	private WebDriver driver;
	/**
	 * waiting 5 sec
	 */
	private WebDriverWait wait;

	public CartPagePom(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(6));
	}

	/**
	 * @implNote do not use this to locate an element whose visisbility changes.only
	 *           use it for an which should always be in a single state of
	 *           visibbility
	 * @param locator the locator to locate
	 * @return the webelelement ifdf it is visisble
	 */
	private WebElement finder(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return driver.findElement(locator);
	}

	public WebElement getPlaceOrderButton() {
		return finder(By.xpath("//button[contains(@class,'btn-success')]"));
	}

	public String getTotalPrice() {
		return finder(By.id("totalp")).getText();
	}

	/**
	 * 
	 * @return the hashmap containing item name as key and the price of the item in
	 *         the cart page.
	 */
	public HashMap<String, String> getTableItemNamesAndPrices() {

		HashMap<String, String> map = new HashMap<>();
		List<WebElement> parentCell = driver.findElements(By.xpath("//tbody/tr[@class='success']"));
//		wait until it is viisble;
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr/td[2]")));
//		fetch the item name and price of each element
		for (WebElement eli : parentCell) {
			map.put(eli.findElement(By.xpath("./td[2]")).getText(), eli.findElement(By.xpath("./td[3]")).getText());
		}
		return map;
	}

	/**
	 * 
	 * @param prodName- name of the product which you want tot delete
	 * @return the repective delete Webelement
	 */
	public WebElement deleteIcon(String prodName) {
		By deleteButton = By.xpath("//td[text()='" + prodName + "']/following-sibling::td/a");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(deleteButton));
		return driver.findElement(deleteButton);
		
	}

	public boolean productListIsempty() {
		try {
			driver.findElements(By.xpath("//tr[@class='success']"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * 
	 * @return the header of the popup window. use it to verify if the correct popup
	 *         window opened
	 */
	public String placeOrderPopupWindowHeader() {
		return finder(By.id("orderModalLabel")).getText();
	}

	public boolean placeOrderWindowVisiblliyty() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			/// just wait a bit.to fix a sync issue
		}
		return driver.findElement(By.xpath("//h5[text()='Place order']/../../..")).isDisplayed();
	}

	public WebElement placeOrder_PopWindowCloseIcon() {// check if the close icon closes the popup
		return finder(By.xpath("//h5[@id='orderModalLabel']/../button"));
	}

	public String placeOrder_PopWindowTotal() {
		return finder(By.id("totalm")).getText();
	}

	/**
	 * 
	 * @return the purchase button
	 */
	public WebElement placeOrder_PopWindowPurchaseButton() {
		return finder(By.xpath("//button[text()='Purchase']"));
	}

	public WebElement placeOrder_PopWindowCloseButton() {
		return finder(By.xpath("//button[text()='Purchase']/preceding-sibling::button"));
	}

	public boolean confirmPurchaseWindowAppeared() {
		return driver.findElement(By.xpath("//div[contains(@class,'sweet-alert')]")).isDisplayed();
	}

	public String purchaseReeciptDetails() {
		return finder(By.xpath("//p[contains(@class,'lead') and contains(@class,'text-muted')]")).getText();
	}

	public WebElement purchaseReciptOkButton() {
		return finder(By.xpath("//button[text()='OK']"));
	}

	/**
	 * 
	 * @param name-     the name you want ot fill
	 * @param cardNuber the credit card number
	 * @implNote- we do not givethe other field properly cause these field are not
	 *            implemented or are not used anywehere so we skip aevvery other
	 *            field and only gave valid credential for two fields.
	 */
	public void givePurchaseCredentials(String name, String cardNuber) {
		String startpath = "//label[@id='totalm']/..//label[text()='";
		String endpath = "']/following-sibling::input";
		finder(By.xpath(startpath + "Name:" + endpath)).sendKeys(name);
		finder(By.xpath(startpath + "Country:" + endpath)).sendKeys("random");
		finder(By.xpath(startpath + "City:" + endpath)).sendKeys("random");
		finder(By.xpath(startpath + "Credit card:" + endpath)).sendKeys(cardNuber);
		finder(By.xpath(startpath + "Month:" + endpath)).sendKeys("radnom");
		finder(By.xpath(startpath + "Year:" + endpath)).sendKeys("radnom");
	}
}
