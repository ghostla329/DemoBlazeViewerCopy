package swag;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pomClasses.HomepagePom;
import pomClasses.SummarypagePom;

public class SummaryPage extends BaseTest {
	SummarypagePom sumter;

	@BeforeMethod
	public void site_launch() {

		driver.get(pred.getProperty("summarypageUrl"));
		sumter = new SummarypagePom(driver);
	}

	@Test
	public void subheader_Check() {
		assertEquals(sumter.giveSubheadertexrt(), "Checkout: Overview");
		asrt.assertAll();
	}

	@Test
	public void shipperNmaeCheck() {
		assertEquals(sumter.shippername(), "FREE PONY EXPRESS DELIVERY!");
		asrt.assertAll();
	}

	@Test
	public void clickcancelButtonTest() {
		sumter.cancelButton();
		assertEquals(driver.getCurrentUrl(), pred.getProperty("homepageUrl"));
		asrt.assertAll();
	}

	@Test
	public void clickFinishbuttonTest() {
		sumter.finishButton();
		assertEquals(driver.getCurrentUrl(), pred.getProperty("finalpageUrl"));
		asrt.assertAll();
	}

	@Test
	public void Billing_check() {
		float q1 = sumter.subtotal();
		float q2 = sumter.tax();
		assertEquals(q1 + q2, sumter.total());
		asrt.assertAll();

	}

//after adding to cart check clicking on label goes to product page and the total value of all item in cart tally with subtotal

}
