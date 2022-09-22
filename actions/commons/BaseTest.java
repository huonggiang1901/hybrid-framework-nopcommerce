package commons;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest {

	private WebDriver driver;

	protected WebDriver getBrowserDriver(String browserName, String urlValue) {

		BrowserList browser = BrowserList.valueOf(browserName.toUpperCase());

		if (browser == BrowserList.FIREFOX) {
			WebDriverManager.firefoxdriver().setup();
			
//			 Browser version
//			 WebDriverManager.firefoxdriver().browserVersion("95.0").setup();
			
//			 Browser driver version
//			 WebDriverManager.firefoxdriver().driverVersion("0.30.0").setup(;
			
			driver = new FirefoxDriver();
		} else if (browser == BrowserList.CHROME) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser == BrowserList.EDGE) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
//		} else if (browser == BrowserList.IE) {
//			WebDriverManager.iedriver().arch32().setup();
//			driver = new InternetExplorerDriver();
		} else {
			throw new RuntimeException("This browser is not supported!");
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		BasePage.openUrl(driver, urlValue);

		return driver;

	}
}
