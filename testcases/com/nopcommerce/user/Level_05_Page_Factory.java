package com.nopcommerce.user;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import pageObjects.HomePageObject;
import pageObjects.RegisterPageObject;

public class Level_05_Page_Factory extends BaseTest {

	private WebDriver driver;
	private String emailAddress;

	private HomePageObject homePage;
	private RegisterPageObject registerPage;

	@Parameters({"browser", "url"})
	@BeforeClass
	public void beforeClass(String browserName, String urlValue) {
		driver = getBrowserDriver(browserName, urlValue);
		emailAddress = "afc" + generateFakeNumber() + "@mail.vn";
		homePage = new HomePageObject(driver);
	}

	@Test
	public void TC_01_Register_Empty_Data() {

		homePage.clickRegisterLink();
		registerPage = new RegisterPageObject(driver);

		registerPage.clickRegisterButton();

		Assert.assertEquals(registerPage.getFirstNameErrorMessage(), "First name is required.");
		Assert.assertEquals(registerPage.getLastNameErrorMessage(), "Last name is required.");
		Assert.assertEquals(registerPage.getEmailErrorMessage(), "Email is required.");
		Assert.assertEquals(registerPage.getPasswordErrorMessage(), "Password is required.");
		Assert.assertEquals(registerPage.getConfirmPasswordErrorMessage(), "Password is required.");
	}

	@Test
	public void TC_02_Register_Invalid_Email() {

		homePage.clickRegisterLink();
		registerPage = new RegisterPageObject(driver);

		registerPage.sendkeyToFirstNameTextbox("Automation");
		registerPage.sendkeyToLastNameTextbox("FC");
		registerPage.sendkeyToEmailTextbox("123@456#%*");
		registerPage.sendkeyToPasswordTextbox("123456");
		registerPage.sendkeyToConfirmPasswordTextbox("123456");

		registerPage.clickRegisterButton();

		Assert.assertEquals(registerPage.getEmailErrorMessage(), "Wrong email");

	}

	@Test
	public void TC_03_Register_Success() {

		homePage.clickRegisterLink();
		registerPage = new RegisterPageObject(driver);

		registerPage.sendkeyToFirstNameTextbox("Automation");
		registerPage.sendkeyToLastNameTextbox("FC");
		registerPage.sendkeyToEmailTextbox(emailAddress);
		registerPage.sendkeyToPasswordTextbox("123456");
		registerPage.sendkeyToConfirmPasswordTextbox("123456");

		registerPage.clickRegisterButton();

		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

		registerPage.clickLogoutLink();
		homePage = new HomePageObject(driver);

	}

	@Test
	public void TC_04_Register_Existing_Email() {

		homePage.clickRegisterLink();
		registerPage = new RegisterPageObject(driver);

		registerPage.sendkeyToFirstNameTextbox("Automation");
		registerPage.sendkeyToLastNameTextbox("FC");
		registerPage.sendkeyToEmailTextbox(emailAddress);
		registerPage.sendkeyToPasswordTextbox("123456");
		registerPage.sendkeyToConfirmPasswordTextbox("123456");

		registerPage.clickRegisterButton();

		Assert.assertEquals(registerPage.getExistedEmailErrorMessage(), "The specified email already exists");
	}

	@Test
	public void TC_05_Register_Password_Less_Than_6_Chars() {

		homePage.clickRegisterLink();
		registerPage = new RegisterPageObject(driver);

		registerPage.sendkeyToFirstNameTextbox("Automation");
		registerPage.sendkeyToLastNameTextbox("FC");
		registerPage.sendkeyToEmailTextbox(emailAddress);
		registerPage.sendkeyToPasswordTextbox("123");
		registerPage.sendkeyToConfirmPasswordTextbox("123");

		registerPage.clickRegisterButton();

		Assert.assertEquals(registerPage.getPasswordErrorMessage(),
				"Password must meet the following rules:\nmust have at least 6 characters");
	}

	@Test
	public void TC_06_Register_Invalid_Confirm_Password() {

		homePage.clickRegisterLink();
		registerPage = new RegisterPageObject(driver);

		registerPage.sendkeyToFirstNameTextbox("Automation");
		registerPage.sendkeyToLastNameTextbox("FC");
		registerPage.sendkeyToEmailTextbox(emailAddress);
		registerPage.sendkeyToPasswordTextbox("123456");
		registerPage.sendkeyToConfirmPasswordTextbox("654321");

		registerPage.clickRegisterButton();

		Assert.assertEquals(registerPage.getConfirmPasswordErrorMessage(),
				"The password and confirmation password do not match.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int generateFakeNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

}
