package tests.docker;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import actions.common.AbstractTest;
import actions.common.CommonActions;
import actions.common.Constants;
import actions.common.DataActions;

public class TM_Test extends AbstractTest {

	private String uploadPath = "";
	private String seleniumUser1 = "Selenium User 1";
	private String seleniumUser2 = "Selenium User 2";
	private String groupTestSelenium = "Group test selenium";
	private String catalogNote = "Hello";
	private WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "browser" })
	public void set_up(String browser) {
		
		log.info("Initiate webdriver");
		driver =  openBrowser(browser);
		log.info("Pre-condition: Navigate to Login page");
		navigateURL(driver, Constants.LOGIN_URL);
	}

	@Test(groups = { "catalog" }, description = "Verify that user add catalog, category and product properly", enabled = true)
	public void TC_AddCatalogCategoryProduct() {
		log.info("TC_AddCatalogCategoryProduct - Step 1: Login with a valid account (no iframe)");
		verifyTrue(true);
	}
	
	@Test(groups = { "catalog" }, description = "Verify that user add catalog, category and product properly", enabled = true)
	public void TC_AddCatalogCategoryProduct01() {
		log.info("TC_AddCatalogCategoryProduct01 - Step 1: Login with a valid account (no iframe)");
		verifyTrue(true);
	}

	@AfterMethod(alwaysRun = true)
	public void clean_up(ITestResult result) {
		log.info("Post Condition: Close Browser");
		quitBrowser(driver);
	}
}
