package pomClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomepagePom {
	private WebDriver driver;
	private WebDriverWait wait;

	public HomepagePom(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public WebElement getNextButton() {
		return driver.findElement(By.id("next2"));
	}

	public WebElement getPreviousButton() {
		return driver.findElement(By.id("prev2"));
	}

//webelement for category 
	public WebElement getCategories() {
		return finder(By.id("cat"));
	}

	public WebElement getphoneCategoryFilter() {
		return finder(By.xpath("//a[text()='Phones']"));
	}

	public WebElement getlaptopCategoryFilter() {
		return finder(By.xpath("//a[text()='Laptops']"));
	}

	public WebElement getMonitorCategoryFilter() {
		return finder(By.xpath("//a[text()='Monitors']"));
	}

	/** returns the lsit of card containers when they are visible */
	public List<WebElement> getCardsContainers() {
		List<WebElement> elements = driver
				.findElements(By.xpath("//div[contains(@class,'card') and contains(@class,'h-100')]"));
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
		return elements;
	}

	public HashMap<String, String> getCardItemNameAndPrices() {
		List<WebElement> parents = getCardsContainers();
		HashMap<String, String> map = new HashMap<>();
		for (WebElement eli : parents) {

			map.put(eli.findElement(By.className("card-title")).getText(),
					eli.findElement(By.xpath(".//h5")).getText());
		}
		return map;
	}

	/**
	 * @return list of all the visible product title web elements
	 */
	public List<String> getAllVisibleItemLabels() {
		List<String> elements = new ArrayList<>();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h4/a")));
		driver.findElements(By.xpath("//h4/a")).forEach((WebElement ok) -> {
			elements.add(ok.getText());
		});

		return elements;
	}

	public WebElement getCardImages(String prodname) {
		System.out.println("//h4/a[contains(text(),'" + prodname + "')]/../../preceding-sibling::a");
		return driver.findElement(By.xpath("//h4/a[contains(text(),'" + prodname + "')]/../../preceding-sibling::a"));
	}

	/**
	 * 
	 * @param prodname- the text of the elelement
	 * @return the web element of that element
	 */
	public WebElement findElementByName(String prodname) {

		return driver.findElement(By.xpath("//h4/a[contains(text(),'" + prodname + "')]"));
	}

	public String getVisaibleItemPriceFromname(String prodName) {
		return driver.findElement(By.xpath("//a[contains(text(),'" + prodName + "')]/../../h5")).getText();
	}

	// ---------------------------------------helper methods----------------
	/**
	 * @param -by locator use this where the webelement is visibility is expected
	 *            true always.
	 */
	private WebElement finder(By locater) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
		return driver.findElement(locater);

	}
	// --------------------------------------------------------------------------
}
