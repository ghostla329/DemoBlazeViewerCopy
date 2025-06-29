package swag;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import errorthings.Listenerimplementor;
import pomClasses.LoginPom;

@Listeners(Listenerimplementor.class)

public class Loginpage extends BaseTest {
	LoginPom logter;// page object for loginpage
	private String CurrentUrl = pred.getProperty("loginpageUrl");// featches the loginpage url

	/**
	 * a regular username
	 */
	private String username1 = pred.getProperty("username1");// norml useranem
	private String username3 = pred.getProperty("username3");// locked username
	private String password = pred.getProperty("password");// password
	private String user_errMessage = pred.getProperty("partialUserErrorMsg");// prtial error message for username field
	private String pass_errMessage = pred.getProperty("partialPassErrorMsg");// partial error message for password field
	private String login_erressage = pred.getProperty("parrtialLoginErrorMsg");// partial error message if login
																				// credentials are wrong
	private String lockedmessage = pred.getProperty("partialLocked_outMsg");// for locked user partial error message
	File td;

	@BeforeMethod(alwaysRun = true)
	public void site_lauch() {// goes to the login page
		driver.get(CurrentUrl);
		logter = new LoginPom(driver);
	}

	@Test(priority = 1)
	public void verify_loginpageReached() { // check if login page is reached or not
		assertEquals(driver.getCurrentUrl(), CurrentUrl);
		
	}

	@Test(priority = 2)
	public void verify_elementPresence() { // check if all main element are visible or not

		asrt.assertTrue(logter.usernamefieldVisibility(), "usernamefield is not cvisible");
		asrt.assertTrue(logter.usernamefieldVisibility(), "passwordfield is not cvisible");
		asrt.assertTrue(logter.loginVisibility(), "login button is not cvisible");
		asrt.assertAll();
	}

	@Test
	public void oneInputTest_1() { // checks can user login just giving username only
		logter.Giveusername(username1);
		logter.clicklogin();
		assertEquals(driver.getCurrentUrl(), CurrentUrl, "the url does not match");
		asrt.assertTrue(logter.errormessage().contains(pass_errMessage), "the password error message did not match");
		error_buttonTest();
		asrt.assertAll();
	}

	@Test
	public void oneInputTest_2() { // checks can user login just giving password only
		logter.Givepassword(password);
		logter.clicklogin();
		assertEquals(driver.getCurrentUrl(), CurrentUrl, "url do not match");
		assertTrue(logter.errormessage().contains(user_errMessage), "the usename error message did not match");
		error_buttonTest();
		asrt.assertAll();
	}

	@Test
	public void wrongInputTest() { // checks can user login using wrong /invalid credential
		logter.Giveusername("rahul_das");
		logter.Givepassword("scary_thing");
		logter.clicklogin();
		assertEquals(driver.getCurrentUrl(), CurrentUrl, "urls do not match");
		assertTrue(logter.errormessage().contains(login_erressage), "the usename error message did not match");
		error_buttonTest();
		asrt.assertAll();
	}

	@Test(dataProvider = "testdata", priority = 3)
	public void loginFlowCheck(String user, String pass) { // checks can user login login giving some valid credentials

		logter.Giveusername(user);
		logter.Givepassword(pass);
		logter.clicklogin();
		if (username3.equals(user)) {
			asrt.assertEquals(driver.getCurrentUrl(), CurrentUrl, "urls do not match");
			assertTrue(logter.errormessage().contains(lockedmessage), "the locked user error message did not appear");
			error_buttonTest();
		} else {
			asrt.assertEquals(driver.getCurrentUrl(), pred.getProperty("homepageUrl"), "url do not martch");
		}
		asrt.assertAll();
	}

	@Test(priority = 4)
	public void negaivelogincheck_1() { // checks if user can login using only space in all field
		logter.Giveusername("       ");
		logter.Givepassword("        ");
		logter.clicklogin();
		asrt.assertTrue(driver.getCurrentUrl().equals(CurrentUrl));
		assertTrue(logter.errormessage().contains(login_erressage));
		error_buttonTest();
		asrt.assertAll();
	}

	@Test(priority = 5)
	public void negaivelogincheck_2() { // check if user can just click login button after entering and login
		logter.clicklogin();
		asrt.assertTrue(driver.getCurrentUrl().equals(CurrentUrl), "urls do not match");
		assertTrue(logter.errormessage().contains(user_errMessage), "the error messages do not match");
		error_buttonTest();
		asrt.assertAll();
	}

	@Test(priority = 6)
	public void passwordNotCopyable() {// checks if user can copy password field data or not
		
		Actions act = new Actions(driver);
		String blankStateValue = logter.getPasswordField().getDomAttribute("value");
		logter.Givepassword("dear");// gives dummy input
		String outfieldValue = logter.getPasswordField().getDomAttribute("value");

		asrt.assertEquals(outfieldValue, "dear", "Unexpectd value got ent into the password field");
		
		act.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
		act.keyDown(Keys.CONTROL).sendKeys("x").keyUp(Keys.CONTROL).build().perform();//cuz ctrl+x copy and deleete.so best foor this scenario. 
		assertFalse(logter.getPasswordField().getDomAttribute("value").equals(blankStateValue),
				"unexpected.the password field got empty.This means ctrl+x worked.");
		asrt.assertEquals(outfieldValue, "dear", "Unexpectd value got ent into the password field");
asrt.assertAll();
	}

	@DataProvider
	private Object testdata() { // dataproviders
		Object[][] data = new Object[3][2];
		data[0][0] = username1;
		data[0][1] = password;
		data[1][0] = pred.getProperty("username2");
		data[1][1] = password;
		data[2][0] = username3;
		data[2][1] = password;
		return data;

	}

	/**
	 * test the error button that shows in case of different login error
	 */
	private void error_buttonTest() { //
		Boolean isDisplayed = true;
		logter.errorButton().click();
		asrt.assertTrue(isDisplayed);
		try {
			logter.errorButton();
		} catch (NoSuchElementException e) {
			isDisplayed = false;
		}
		assertFalse(isDisplayed);
	}

}
