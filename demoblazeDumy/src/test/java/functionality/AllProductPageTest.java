package functionality;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import baseTest.BaseTest;
import pomClasses.NavBarPom;
import pomClasses.ProdPageGenericPom;
import utility.ExcelReader;

public class AllProductPageTest extends BaseTest {
	ProdPageGenericPom prod;

	@BeforeMethod(alwaysRun=true)
	public void prequiste() {
		prod = new ProdPageGenericPom(driver);
	}

	@Test(groups = { "smoke" }, dataProvider = "datas")
	public void product_AddToCartButton_Test(String url) {// check the add to cart functionality
		// go topage
		driver.get(url);
		// click on add to cart
		prod.getAddcartButton().click();
		// wait for alert to appear
		explicitWait.until(ExpectedConditions.alertIsPresent());
		// get the alert message and handle it
		Alert alert = driver.switchTo().alert();
		String message = alert.getText();
		alert.accept();
		// check if text match
		softAsrt.assertEquals(message, "Product added");
	}

	@Test(groups = { "smoke" }, dataProvider = "datas")
	public void product_Pricetally(String url) {// check if the price of the item is as expected
//		 go to page
		driver.get(url);
//		 get the product name
		String productName = prod.getProductName();
//		 search it in excel
		String expectedPrice = ExcelReader.itemDetailsfnderByProdName("Sheet1", 2, productName);
//		the price showni on site
		String actualPrice = prod.getPrice().replace(" *includes tax", "");
//		match
		softAsrt.assertEquals(actualPrice, expectedPrice, "price did not tally with expected price");
		softAsrt.assertAll();
	}

	@DataProvider
	public Object[][] datas() {
		Object[][] testData = new Object[15][1];
		for (int i = 0; i < 15; i++) {
			testData[i][0] =ExcelReader.getValue(i + 1, 3, "Sheet1");
		} // setting the urls
		return testData;
	}
}
