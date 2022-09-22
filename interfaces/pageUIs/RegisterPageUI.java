package pageUIs;

public class RegisterPageUI {
	
	public static final String FIRSTNAME_TEXTBOX = "//input[@id='FirstName']";
	public static final String LASTNAME_TEXTBOX = "//input[@id='LastName']";
	public static final String EMAIL_TEXTBOX = "//input[@id='Email']";
	public static final String PASSWORD_TEXTBOX = "//input[@id='Password']";
	public static final String CONFIRMPASSWORD_TEXTBOX = "//input[@id='ConfirmPassword']";
	
	public static final String FIRSTNAME_ERRORMESSAGE = "//span[@id='FirstName-error']";
	public static final String LASTNAME_ERRORMESSAGE = "//span[@id='LastName-error']";
	public static final String EMAIL_ERRORMESSAGE = "//span[@id='Email-error']";
	public static final String PASSWORD_ERRORMESSAGE = "//span[@id='Password-error']";
	public static final String CONFIRMPASSWORD_ERRORMESSAGE = "//span[@id='ConfirmPassword-error']";
	public static final String EXISTEDEMAIL_ERRORMESSAGE = "//div[contains(@class,'message-error')]//li";
	
	public static final String REGISTER_SUCCESSMESSAGE = "//div[@class='result']";
	
	public static final String REGISTER_BUTTON = "//button[@id='register-button']";
	
	public static final String LOGOUT_LINK = "//a[@class='ico-logout']";

}
