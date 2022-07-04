package base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import utility.BrowserFactory;
import utility.ExcelDataProvider;
import utility.Helper;
import utility.configDateProvider;

@SuppressWarnings("deprecation")
public class TestBase {
	public static WebDriver driver;
	public ExcelDataProvider readExcelData;
	public configDateProvider config;
	public ExtentReports report;
	public ExtentTest logger;
	public int elementWaitInSeconds = 60;
	public int timeOut = 60;
	public int retryattempts = 2;
	public Select select;
	public ExtentTest test;	
	WebElement element;

	@BeforeClass
	public void initiateSetup() {
		readExcelData = new ExcelDataProvider();
		config = new configDateProvider();

		configDateProvider cdp = new configDateProvider ();
		ExtentHtmlReporter extent = new ExtentHtmlReporter( new
		File("./Reports/ExtentReport_" + Helper.getCurrentDateTime() + ".html"));
		extent.loadXMLConfig(new File(System.getProperty("user.dir") +
		"./XML Files/Extent-config.xml")); report = new ExtentReports();
	report.setSystemInfo("Evironment", "QA"); report.setSystemInfo("Build#",
	 "21003"); report.attachReporter(extent);
	 
	 
		driver = BrowserFactory.startApplication(driver, "chrome","https://www.saucedemo.com/");
	}

	@BeforeMethod(alwaysRun = true)
	public void registerTestName(Method method) {
		String testName = method.getName();
		logger = report.createTest(testName);
	}

	@AfterClass
	public void tearDownMethod() {
		BrowserFactory.quitBrowser(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void teadDownMethod(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Inside the filed step");
			System.out.println("Taking the screenshots of failure step");
			Helper.captureScreenshots(driver);
			System.out.println("Screenshot Captured for Failed Step");
			logger.fail("Test Failed",
					MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshots(driver)).build());
			System.out.println("Failed Screenshot added in the extent report");
			logger.log(Status.FAIL, "The Test Method Named as: " + result.getName() + " is Failed");
			logger.log(Status.FAIL, "Test Failed: " + result.getThrowable());

		}

		else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, "The Test Method Named as: " + result.getName() + " is Passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP, "The test Method: " + result.getName() + " is skipped");
		}

		report.flush();
	}
	
	

///////Generic Methods////////////////

	public void setImplicit(int timeOut) {

		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	public WebDriverWait webDriverWait() {

		return new WebDriverWait(driver, Duration.ofSeconds(elementWaitInSeconds));
	}

	public String getText(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(elementWaitInSeconds));
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.getText();

	}

	public void clickEvent(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		webDriverWait().until(ExpectedConditions.visibilityOf(element));
		element.click();
		System.out.println("Element: " + locator + " Clicked");

	}


	public void enterText(String locator, String textValue) {

		element = driver.findElement(By.xpath(locator));

		logger.info("Entered Text - " + textValue);
		element.sendKeys(textValue);

	}

		public boolean isElementDisplayed(String locator) {
	
		boolean flag = false;

		WebElement element = driver.findElement(By.xpath(locator));

		try {
			setImplicit(1000);
			flag = element.isDisplayed();
		} catch (StaleElementReferenceException stale) {

		}

		catch (Exception e) {

			flag = false;
		}
		logger.info("Is element " + element + " displayed - " + flag);
		return flag;
	}
}
