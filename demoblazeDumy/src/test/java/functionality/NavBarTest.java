package functionality;

import org.testng.annotations.Test;

import org.testng.annotations.DataProvider;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import baseTest.BaseTest;
import pomClasses.NavBarPom;
import utility.PropertiesReader;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class NavBarTest extends BaseTest {

//testif the homrlabel works or not
//check about section

	private String homeeUrl = PropertiesReader.getProperty("homepageurl");
	private String afterLoginHomerUrl = PropertiesReader.getProperty("afterLoginHomepageurl");
	private NavBarPom nav;

	@BeforeMethod(alwaysRun = true)
	public void siteLaunch() {
		driver.get(homeeUrl);
		nav = new NavBarPom(driver, explicitWait);
		assertTrue(nav.navBarContainerVisibility(), "nav bar is not visible");
	}

	@Test(groups = { "smoke", "integration" })
	public void brandLogointegrationTest() {// check if clicking on the brand logo redirect ot homepage
		WebElement logo = nav.getLogo();
		assertTrue(logo.isDisplayed(), "logo is not visible");
		logo.click();
		explicitWait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(homeeUrl)));

		assertEquals(driver.getCurrentUrl(), afterLoginHomerUrl,
				"logo did not redirect to proper page.url do not match");
	}

	@Test(groups = { "smoke", "integration" })
	public void homeLabelIntegrationBeforeLoginTest() {// check if in before login clicking on the ghome button takes to
														// the homepage
		nav.getHomeLabel().click();
		explicitWait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(homeeUrl)));
		softAsrt.assertEquals(driver.getCurrentUrl(), afterLoginHomerUrl,
				"after ogin label did not redirect to proper page.url do not match");
		softAsrt.assertAll();
	}

	@Test(dataProvider = "aboutUsCredentials")
	public void contactLabelFunctionalityTest(String email, String name, String Message, boolean expectedVisibility) {// check

		nav.getContactLabel().click();
		// then give crefdentials
		nav.getContactEmailField().sendKeys(email);
		nav.getContactNameField().sendKeys(name);
		nav.getContactMessageField().sendKeys(Message);
		nav.getContactSendMessageButton().click();
		// wait for alert to appear
		explicitWait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		softAsrt.assertEquals(alert.getText(), PropertiesReader.getProperty("contactAlertText"));
		alert.accept();
		explicitWait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
		softAsrt.assertEquals(nav.contactPopupWindowvisibility(), expectedVisibility);
		softAsrt.assertAll();
	}

	@Test(groups = { "smoke" })
	public void contactCloseButtonTest() {// check if the clsoe butto works
		nav.getContactLabel().click();
		WebElement closeButton = nav.getContactCloseButton();
		explicitWait.until(ExpectedConditions.elementToBeClickable(closeButton));
		closeButton.click();
		// wait until close button is invisible
		explicitWait.until(ExpectedConditions.invisibilityOf(closeButton));
		softAsrt.assertFalse(nav.contactPopupWindowvisibility());
		softAsrt.assertAll();
	}

	@Test(groups = { "smoke" })
	public void contactCloseIconTest() {// check if the close icon works properly or not
		nav.getContactLabel().click();
		WebElement closeIcon = nav.getContactCloseIcon();
		closeIcon.click();
		explicitWait.until(ExpectedConditions.invisibilityOf(closeIcon));
		softAsrt.assertFalse(nav.contactPopupWindowvisibility());
		softAsrt.assertAll();
	}

	@Test(groups = { "smoke" })
	public void aboutUsCloseButtonAndIconTest() {// check if the close butoon of about us popup works
		nav.getAboutUsLabel().click();
		WebElement closeButton = nav.getAbouCloseButton();
		closeButton.click();
		explicitWait.until(ExpectedConditions.invisibilityOf(closeButton));
		assertFalse(nav.aboutPopupWindowvisibility());

	}

	@Test(groups = { "smoke" })
	public void aboutCloseIconTest() {// check if the close icon inside about us popup works
		nav.getAboutUsLabel().click();
		WebElement closeIcoon = nav.getAboutCloseIcon();
		closeIcoon.click();
		explicitWait.until(ExpectedConditions.invisibilityOf(closeIcoon));
		assertFalse(nav.aboutPopupWindowvisibility());
	}

	@Test(groups = { "smoke" })
	public void signupFunctiontionalityTest() {// check if the signup label properly signs up user or not
		String name = "tylor6654";// username for signup
		String pass = "utre6789";// passwordfor signup

		WebElement signUpLabel = nav.getSignUpLabel();
		explicitWait.until(ExpectedConditions.visibilityOf(signUpLabel));
		signUpLabel.click();

		nav.signup_getUsernamefield().sendKeys(name);
		nav.signup_getPasswordfield().sendKeys(pass);
		PropertiesReader.updateValue("usename", name);
		PropertiesReader.updateValue("password", pass);
		// click to signup
		WebElement signup = nav.signup_getSignUpButton();
		signup.click();
		explicitWait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		// if user does bot exist, then run assertions.else throw a runtime exception
		if (!alert.getText().equals("This user already exist.")) {
			softAsrt.assertEquals(alert.getText(), "Sign up successful.");
			alert.accept();
			softAsrt.assertAll();
		} else {
			throw new RuntimeException("the usernameand/password already exist.signup with other credentials");
		}
	}

	@Test(groups = { "smoke" })
	public void blankOrPartialSignupTest() {// check if with no input i can sign up
		WebElement signUpLabel = nav.getSignUpLabel();
		explicitWait.until(ExpectedConditions.visibilityOf(signUpLabel));
		signUpLabel.click();
		nav.signup_getSignUpButton().click();
		explicitWait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		softAsrt.assertEquals(alert.getText(), "Please fill out Username and Password.");
		alert.accept();
		softAsrt.assertAll();
	}

	@Test(groups = { "smoke" })
	public void signupCloseButtonTest() {
		WebElement signUpLabel = nav.getSignUpLabel();
		explicitWait.until(ExpectedConditions.visibilityOf(signUpLabel));
		signUpLabel.click();

		WebElement closeButton = nav.signup_getCloseButton();
		closeButton.click();
		explicitWait.until(ExpectedConditions.invisibilityOf(closeButton));
		assertFalse(nav.signupMiniWindow().isDisplayed());
	}

	@Test(groups = { "smoke" })
	public void signupCloseIconTest() {// check if the close icon works or not
		WebElement signUpLabel = nav.getSignUpLabel();
		explicitWait.until(ExpectedConditions.visibilityOf(signUpLabel));
		signUpLabel.click();
		WebElement closeButton = nav.signup_getCloseIcon();
		closeButton.click();
		explicitWait.until(ExpectedConditions.invisibilityOf(closeButton));
		assertFalse(nav.signupMiniWindow().isDisplayed());
	}

	@Test(groups = { "smoke" })
	public void loginFunctionalityCheck() {// check if you can login usinfg valid credentials
		WebElement loginLabel = nav.getLoginLabel();
		loginLabel.click();
		explicitWait.until(ExpectedConditions.visibilityOf(loginLabel));
		String username = PropertiesReader.getCredProperTy("usename");
		nav.giveLoginUsername().sendKeys(username);
		nav.giveLoginPassword().sendKeys(PropertiesReader.getCredProperTy("password"));
		WebElement loginButton = nav.getLoginButton();
		loginButton.click();
		// wait for element to be uinvisible to check window got closed
		explicitWait.until(ExpectedConditions.invisibilityOf(loginButton));
		assertFalse(nav.getLoginMiniWindowVisibility());
		String profile_name = nav.getProfileLabel().getText();
		softAsrt.assertTrue(profile_name.contains(username));
		// check if the logout button is viible not
		softAsrt.assertTrue(nav.logoutLabel().isDisplayed(), "logout not visibile");
		softAsrt.assertAll();
	}

	@Test(groups = { "smoke" })
	public void blankLogin() {// check if blank login hapens ort not
		WebElement loginLabel = nav.getLoginLabel();
		loginLabel.click();
		explicitWait.until(ExpectedConditions.visibilityOf(loginLabel));
		WebElement loginButton = nav.getLoginButton();
		loginButton.click();
		// wait for element to be uinvisible to check window got closed
		explicitWait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
		nav.getLoginPageCloseButton().click();
		softAsrt.assertFalse(nav.logoutLabel().isDisplayed());
		softAsrt.assertAll();
	}

	@Test(groups = { "smoke" })
	public void loginCloseButtonTest() {// check if the login [page close button works
		nav.getLoginLabel().click();
		WebElement closeButton = nav.getLoginPageCloseButton();
		closeButton.click();
		explicitWait.until(ExpectedConditions.invisibilityOf(closeButton));
		softAsrt.assertFalse(nav.getLoginMiniWindowVisibility());
	}

	@Test(groups = { "smoke" })
	public void loginCloseIconTest() {
		nav.getLoginLabel().click();
		WebElement closeButton = nav.getLoginPageCloseIcon();
		closeButton.click();
		explicitWait.until(ExpectedConditions.invisibilityOf(closeButton));
		softAsrt.assertFalse(nav.getLoginMiniWindowVisibility());

	}

	@Test(groups = { "smoke" })
	public void logoutTest() {
		loginFunctionalityCheck();
		nav.logoutLabel().click();;
		assertFalse(nav.logoutLabel().isDisplayed());
	}

	@DataProvider
	public Object[][] aboutUsCredentials() {// data provider for about us field
		String message = PropertiesReader.getProperty("contactMessage");
		String name = PropertiesReader.getProperty("contactname");
		Object[][] data = new Object[3][4];
		data[0][0] = PropertiesReader.getProperty("contactEmail");
		data[0][1] = name;
		data[0][2] = message;
		data[0][3] = false;
		data[1][0] = PropertiesReader.getProperty("wrongFormatEmail");
		data[1][1] = name;
		data[1][2] = message;
		data[1][3] = true;
		data[2][0] = "";// blank cases
		data[2][1] = "";// blank cases
		data[2][2] = "";// blank cases
		data[2][3] = true;
		return data;
	}

	@AfterMethod(alwaysRun = true)
	public void clenaup() {
		driver.manage().deleteAllCookies();
	}
}
