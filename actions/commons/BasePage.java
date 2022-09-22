package commons;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	public static BasePage getBasePage() {
		return new BasePage();
	}
	
	private static final int TIMEWAIT = 0;
	

	public static void openUrl(WebDriver driver, String url) {
		driver.get(url);
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getPageSourceCode(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public Alert waitForAlertPresence(WebDriver driver) {
		return new WebDriverWait(driver, TIMEWAIT).until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert(WebDriver driver) {
		waitForAlertPresence(driver).accept();
		;
	}

	public void cancelAlert(WebDriver driver) {
		waitForAlertPresence(driver).dismiss();
	}

	public String getAlertText(WebDriver driver) {
		return waitForAlertPresence(driver).getText();
	}

	public void sendkeyToAlert(WebDriver driver, String value) {
		waitForAlertPresence(driver).sendKeys(value);
	}

	public String getParentWindowID(WebDriver driver) {
		return driver.getWindowHandle();
	}

	public Set<String> getAllWindowsID(WebDriver driver) {
		return driver.getWindowHandles();
	}

	public void switchToWindow(WebDriver driver, String window) {
		driver.switchTo().window(window);
	}

	public void closeWindow(WebDriver driver) {
		driver.close();
	}

	public void switchWindowByID(WebDriver driver) {
		String parentWindow = getParentWindowID(driver);
		Set<String> allWindows = getAllWindowsID(driver);
		for (String window : allWindows) {
			if (!window.equals(parentWindow)) {
				switchToWindow(driver, window);
				break;
			}
		}
	}

	public void switchWindowByTitle(WebDriver driver, String title) {
		Set<String> allWindows = getAllWindowsID(driver);
		for (String window : allWindows) {
			switchToWindow(driver, window);
			String windowTitle = getPageTitle(driver);
			if (windowTitle.equals(title)) {
				break;
			}
		}
	}

	public void closeAllWindowsWithoutParent(WebDriver driver, String parentWindowID) {
		Set<String> allWindows = getAllWindowsID(driver);
		for (String window : allWindows) {
			if (!window.equals(parentWindowID)) {
				switchToWindow(driver, window);
				closeWindow(driver);
			}
		}
		switchToWindow(driver, parentWindowID);
	}

	public By getByXpath(String xpathExparession) {
		return By.xpath(xpathExparession);
	}

	public WebElement getWebElement(WebDriver driver, String xpathExpression) {
		return driver.findElement(getByXpath(xpathExpression));
	}

	public List<WebElement> getWebElements(WebDriver driver, String xpathExpression) {
		return driver.findElements(getByXpath(xpathExpression));
	}

	public void clickToElement(WebDriver driver, String xpathExpression) {
		getWebElement(driver, xpathExpression).click();
	}

	public void sendkeyToElement(WebDriver driver, String xpathExpression, String value) {
		getWebElement(driver, xpathExpression).clear();
		getWebElement(driver, xpathExpression).sendKeys(value);
	}

	public String getElementText(WebDriver driver, String xpathExpression) {
		return getWebElement(driver, xpathExpression).getText();
	}

	public void selectItemInDefaultDropdown(WebDriver driver, String xpathExpression, String itemText) {
		new Select(getWebElement(driver, xpathExpression)).selectByVisibleText(itemText);
	}

	public String getSelectedItemInDefaultDropdown(WebDriver driver, String xpathExpression) {
		return new Select(getWebElement(driver, xpathExpression)).getFirstSelectedOption().getText();
	}

	public boolean isDefaultDropdownMuliple(WebDriver driver, String xpathExpression) {
		return new Select(getWebElement(driver, xpathExpression)).isMultiple();
	}

	public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator,
			String expectedItem) {
		getWebElement(driver, parentLocator).click();
		sleepInSecond(1);

		WebDriverWait explicitWait = new WebDriverWait(driver, TIMEWAIT);
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childItemLocator)));

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);

				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}

	public String getElementAttribute(WebDriver driver, String xpathExpression, String attributeName) {
		return getWebElement(driver, xpathExpression).getAttribute(attributeName);
	}

	public String getElementCssValue(WebDriver driver, String xpathExpression, String propertyName) {
		return getWebElement(driver, xpathExpression).getCssValue(propertyName);
	}

	public String getHexaColorByRgbaColor(String rgbaColor) {
		return Color.fromString(rgbaColor).asHex();
	}

	public int getElementsNumber(WebDriver driver, String xpathExpression) {
		return getWebElements(driver, xpathExpression).size();
	}

	public void checkToRadioOrCheckbox(WebDriver driver, String xpathExpression) {
		if (!isElementSelected(driver, xpathExpression)) {
			getWebElement(driver, xpathExpression).click();
		}
	}

	public void uncheckToCheckbox(WebDriver driver, String xpathExpression) {
		if (isElementSelected(driver, xpathExpression)) {
			getWebElement(driver, xpathExpression).click();
		}
	}

	public boolean isElementDisplayed(WebDriver driver, String xpathExpression) {
		return getWebElement(driver, xpathExpression).isDisplayed();
	}

	public boolean isElementSelected(WebDriver driver, String xpathExpression) {
		return getWebElement(driver, xpathExpression).isSelected();
	}

	public boolean isElementEnabled(WebDriver driver, String xpathExpression) {
		return getWebElement(driver, xpathExpression).isEnabled();
	}

	public void switchToFrame(WebDriver driver, String xpathExpression) {
		driver.switchTo().frame(xpathExpression);
	}

	public void switchToDefaultContentPage(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public void hoverMouseToElement(WebDriver driver, String xpathExpression) {
		new Actions(driver).moveToElement(getWebElement(driver, xpathExpression)).perform();
	}

	public void pressKeyboardToElement(WebDriver driver, String xpathExpression, Keys keys) {
		new Actions(driver).sendKeys(getWebElement(driver, xpathExpression), keys).perform();
	}

	public Object executeForBrowser(WebDriver driver, String javaScript) {
		return ((JavascriptExecutor) driver).executeScript(javaScript);
	}

	public String getInnerText(WebDriver driver) {
		return (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		String textActual = (String) ((JavascriptExecutor) driver)
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(WebDriver driver, String url) {
		((JavascriptExecutor) driver).executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(WebDriver driver, String xpathExpression) {
		WebElement element = getWebElement(driver, xpathExpression);
		String originalStyle = element.getAttribute("style");
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element,
				"style", "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element,
				"style", originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String xpathExpression) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(driver, xpathExpression));
	}

	public void scrollToElement(WebDriver driver, String xpathExpression) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				getWebElement(driver, xpathExpression));
	}

	public void sendkeyToElementByJS(WebDriver driver, String xpathExpression, String value) {
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + value + "')",
				getWebElement(driver, xpathExpression));
	}

	public void removeAttributeInDOM(WebDriver driver, String xpathExpression, String attributeRemove) {
		((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
				getWebElement(driver, xpathExpression));
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, TIMEWAIT);
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public String getElementValidationMessage(WebDriver driver, String xpathExpression) {
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;",
				getWebElement(driver, xpathExpression));
	}

	public boolean isImageLoaded(WebDriver driver, String xpathExpression) {
		boolean status = (boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				getWebElement(driver, xpathExpression));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void waitForElementVisible(WebDriver driver, String xpathExpression) {
		new WebDriverWait(driver, TIMEWAIT)
				.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathExpression)));
	}

	public void waitForElementClickable(WebDriver driver, String xpathExpression) {
		new WebDriverWait(driver, TIMEWAIT).until(ExpectedConditions.elementToBeClickable(getByXpath(xpathExpression)));
	}

	public void waitForElementInvisible(WebDriver driver, String xpathExpression) {
		new WebDriverWait(driver, TIMEWAIT)
				.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathExpression)));
	}

	public void waitForAllElementVisible(WebDriver driver, String xpathExpression) {
		new WebDriverWait(driver, TIMEWAIT)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathExpression)));
	}

	public void waitForAllElementInvisible(WebDriver driver, String xpathExpression) {
		new WebDriverWait(driver, TIMEWAIT)
				.until(ExpectedConditions.invisibilityOfAllElements(getWebElements(driver, xpathExpression)));
	}

	public void sleepInSecond(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
