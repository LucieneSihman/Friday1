package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {

	// WebDriver from test
	public WebDriver driver;

	WebDriverWait wait;
	Actions action;

	public Base(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);
	}

	/*
	 * Parameters: 1. WebElement 2. Text to type Send keys to web element after
	 * clean
	 */
	public void typeTextSubmit(By by, String text) throws InterruptedException {

		wait.until(ExpectedConditions.elementToBeClickable((by)));

		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(text);
		driver.findElement(by).submit();
		Thread.sleep(2000);
	}

	/*
	 * Parameters: 1. WebElement click on web element after verify is click able
	 */
	public void click(By by) {
		// Verify the WebElement is click able and then click
		wait.until(ExpectedConditions.elementToBeClickable((by)));
		driver.findElement(by).click();
	}

	
	/*
	 * Parameters: 1. WebElement getText after verify exist
	 */
	public String getText(By by) {
		// Verify the WebElement is click able and then click
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((by)));
		String text = driver.findElement(by).getText();
		
		return text;
	}

	
	public void moveToElement(By by) {
		action = new Actions(driver);
		action.moveToElement(driver.findElement(by)).build().perform();
	}
	
	
	public void clickJS(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	
}
