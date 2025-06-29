package swag;

import java.time.Duration;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import errorthings.Listenerimplementor;
import pomClasses.CheckoutpagePom;

@Listeners(Listenerimplementor.class)

public class Checkoutpage extends BaseTest {
	CheckoutpagePom checkter;

	private String fName = pred.getProperty("firstname");
	private String lname = pred.getProperty("lastname");
	private String zcode = pred.getProperty("zipcode");
	private String fmsg = pred.getProperty("partialFirstNamemsg");
	private String lmsg = pred.getProperty("partialLaststNamemsg");
	private String zmsg = pred.getProperty("partialzipcodemsg");
	private String prevPageUrl = pred.getProperty("cartpageUrl");
	private String thispageUrl = pred.getProperty("checkoutpageUrl");

	@BeforeMethod
	public void site_launch() {
		driver.get(thispageUrl);
		checkter = new CheckoutpagePom(driver);
	}

	@DataProvider
	public Object[][] data() {

		Object[][] datas = new Object[7][1];
		datas[0][0] = "weweewee";// oly text
		datas[1][0] = "321342424";// only number
		datas[2][0] = "#$$%##";// only symbol
		datas[3][0] = "wew3224dffsd";// only text and number
		datas[4][0] = "wqq@#%ewqe";/// only text and symbol
		datas[5][0] = "2344$%^24";// only number and symbol
		datas[6][0] = "wee54*45$#";// all combo
		return datas;

	}

	@Test
	public void subheadermatch_Test() {
		asrt.assertEquals(checkter.giveSubheader(), pred.getProperty("Checkpagesubhead"));
		asrt.assertAll();
	}

	@Test(dataProvider = "data") // giving only first name
	public void Firstnamefieldtest(String input) {
		checkter.givefirstnme(input);
		checkter.clickContinueButton();
		asrt.assertTrue(checkter.errormessage().contains(lmsg));
		errorbutton_caseTest();
		asrt.assertAll();
	}

	@Test(dataProvider = "data") // giving only last name
	public void lastnamefieldTest(String input) {
		checkter.givelastname(input);
		checkter.clickContinueButton();
		asrt.assertTrue(checkter.errormessage().contains(fmsg));
		errorbutton_caseTest();
		asrt.assertAll();
	}

	@Test(dataProvider = "data") // giving only zipcode
	public void zipcodefieldTest(String input) {
		checkter.givelastname(input);
		checkter.clickContinueButton();
		asrt.assertTrue(checkter.errormessage().contains(fmsg));
		errorbutton_caseTest();
		asrt.assertAll();
	}

	@Test // only click continue button
	public void blankInputTest() {
		checkter.clickContinueButton();
		asrt.assertTrue(checkter.errormessage().contains(fmsg));
		errorbutton_caseTest();
		asrt.assertAll();
	}

	@Test // only give first name and last name
	public void twoInputTest1() {

		checkter.givefirstnme(fName);
		checkter.givelastname(lname);
		checkter.clickContinueButton();
		asrt.assertTrue(checkter.errormessage().contains(zmsg), "error message is not as expected");
		errorbutton_caseTest();
		asrt.assertAll();
	}

	@Test // give only firstname and zipcode
	public void twoInputTest2() {

		// give only first name and zipcode
		checkter.givefirstnme(fName);
		checkter.givezipcode(zcode);
		checkter.clickContinueButton();
		asrt.assertTrue(checkter.errormessage().contains(lmsg), "error message is not as expected");
		errorbutton_caseTest();
		asrt.assertAll();
	}

	@Test // give only zipcodse and lastname
	public void twoInputTest3() {
		checkter.givezipcode(zcode);
		checkter.givelastname(lname);
		checkter.clickContinueButton();
		asrt.assertTrue(checkter.errormessage().contains(fmsg), "error message is not as expected");
		errorbutton_caseTest();
		asrt.assertAll();
	}

	@Test
	public void cancelButtton_Test() {
		cancel_buttoncheck();
		// only one field input
		checkter.givefirstnme(fName);
		cancel_buttoncheck();

		checkter.givelastname(lname);
		cancel_buttoncheck();

		checkter.givezipcode(zcode);
		cancel_buttoncheck();

		// only two field input
		checkter.givefirstnme(fName);
		checkter.givelastname(lname);
		cancel_buttoncheck();

		checkter.givelastname(lname);
		checkter.givezipcode(zcode);
		cancel_buttoncheck();

		checkter.givezipcode(zcode);
		checkter.givefirstnme(fName);
		cancel_buttoncheck();
		// givew all input
		checkter.givezipcode(zcode);
		checkter.givefirstnme(fName);
		checkter.givelastname(lname);
		checkter.givezipcode(zcode);
		cancel_buttoncheck();
		asrt.assertAll();
	}

	@Test // give all3 field
	public void allinputTest() {
		checkter.givefirstnme(fName);
		checkter.givelastname(lname);
		checkter.givezipcode(zcode);
		checkter.clickContinueButton();
		asrt.assertEquals(driver.getCurrentUrl(), pred.getProperty("summarypageUrl"), "url does not match");
		asrt.assertAll();
	}
//-----------------------------------------------------------------------------------------------------------------
	
	private void errorbutton_caseTest() {
		WebElement errorButton = checkter.errorButton();
		asrt.assertTrue(errorButton.isDisplayed(), "error button is not visible for some reason");
		errorButton.click();
		boolean exceptionpresent = false;
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(700));// temporary wait to lowerduration

		try {
			checkter.errorButton();
		} catch (NoSuchElementException e) {
			exceptionpresent = true;
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ImplicitWait_Time));
		asrt.assertTrue(exceptionpresent, "the element named error buttonis present in dom");

	}

	/**
	 * this method clicks on cancel button and softasserts and comes backto original
	 * page
	 */
	private void cancel_buttoncheck() {

		checkter.clickCancelButton();
		asrt.assertEquals(driver.getCurrentUrl(), prevPageUrl, "url did not match");
		driver.navigate().to(thispageUrl);
	}

}
