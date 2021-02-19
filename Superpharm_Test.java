package superpharm;

import org.testng.annotations.Test;
import com.google.common.io.Files;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import utility.GetDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class Superpharm_Test {

	// Global variables 
	// Add extent reports
	private ExtentReports extent;
	private ExtentTest myTest;
	private static String reportPaht = System.getProperty("user.dir") + "\\test-output\\ZapReports.html";

	private WebDriver driver;
	private String baseUrl;
	private Actions actions;



	@BeforeClass
	public void beforeClass() {
		extent = new ExtentReports(reportPaht);
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\resources\\zap-extent-config.xml"));
		baseUrl = "https://www.zap.co.il/";
		driver = GetDriver.getDriver("chrome", baseUrl);
	}

	
	@BeforeMethod
	public void beforeMethod(Method method) throws IOException {
		
	}
	
	@Test(priority = 1, enabled = true, description = "Check default sort of products search results")
	public void defaultSortOfSearch_ByPoplarity(Method method) throws InterruptedException, IOException {
		myTest = extent.startTest("Zap - verify default sorting of results to popolarity");
		myTest.log(LogStatus.INFO, "Starting test", "Start test: " + method.getName());
		
		WebElement computersMenu = driver.findElement(By.xpath("//div[@id='uc_Topper_divHeaderContainer']//a[text()='מחשבים']"));
		
		//String attributeSRCvalue = computersMenu.getAttribute("src");
		
		actions = new Actions(driver);
		actions.moveToElement(computersMenu).build().perform();
		
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//div[@id=\"menuItemsWrapper\"]//a[text()='מחשבים ניידים']")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector("span.selected")).getText().equals("פופולריות"));
		

	}
	
	@Test(priority = 1, enabled = true, description = "Check deals page logo")
	public void verifyDealsPageLogo(Method method) throws InterruptedException, IOException {
		
		myTest = extent.startTest("Zap - Check deals page logo");
		myTest.log(LogStatus.INFO, "Starting test", "Start test: " + method.getName());
		
		WebElement dealsMenu = driver.findElement(By.linkText("מבצעים"));
		dealsMenu.click();
		
		String srcAttributeValue = driver.findElement(By.xpath("//*[@id=\"sectionHeader\"]/div/div[2]/a/img")).getAttribute("src");
		Assert.assertEquals(srcAttributeValue, "https://img.zap.co.il/dealsImages/dealspage/zapDealsPlusLogo.png?v=1");
		

	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			myTest.log(LogStatus.FAIL, "Test failed: " + result.getName());
			myTest.log(LogStatus.FAIL, "Test failed reason: " +result.getThrowable());
			myTest.log(LogStatus.FAIL, myTest.addScreenCapture(takeScreenShot(driver)));
		}
		else {
			myTest.log(LogStatus.PASS, result.getName(), "Verify successful ");
			myTest.log(LogStatus.PASS, myTest.addScreenCapture(takeScreenShot(driver)));

		}

		myTest.log(LogStatus.INFO, "Finish test", "Finish test ");
		extent.endTest(myTest);
	
		//return to base URL 
		driver.get(baseUrl);
	}

	@AfterClass
	public void afterClass() {
		extent.flush();
		
		driver.quit();
		extent.close();

	}

	
	//Take screen shot for the ExtentReports
	public static String takeScreenShot(WebDriver driver) throws IOException {
		
		//take snapshot
		TakesScreenshot takescreenshot = (TakesScreenshot)driver;
		
		
		//Get the image file
		File screenshotFile = takescreenshot.getScreenshotAs(OutputType.FILE);
		
		//Create time stamp string for image name
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		
		//output image
		File destFile = new File(System.getProperty("user.dir") + "\\test-output\\images\\" + timeStamp +".png");
		
		//Create the image
		try {
			Files.copy(screenshotFile, destFile);
		}
		catch(IOException e) {
			System.out.println("Failed to create image..." + System.getProperty("user.dir") + "\\test-output\\images\\" + timeStamp +".png");
		}
		
		//Send back the path of the image
		return destFile.getAbsolutePath();
	}
}
	