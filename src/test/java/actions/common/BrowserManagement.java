package actions.common;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserManagement {

    enum OS {
	win(".exe"), macos(""), linux("");

	private String _suffix;


	OS(String suffix) {
	    _suffix = suffix;
	}


	public String getPath() {
	    return System.getProperty("user.dir") + DataActions.getPropValue("driver.dir") + name() + "/";
	}


	public String getSuffix() {
	    return _suffix;
	}
    }

    enum Browser {
	chrome("chrome"), firefox("gecko"), phantomjs("phantomjs");

	private String _code;


	Browser(String code) {
	    _code = code;
	}


	public void defineDriverPropertyFor(OS os) {
	    // System.err.println("webdriver." + _code + ".driver >>>>> " + os.getPath() + _code + "driver" + os.getSuffix());
	    System.setProperty("webdriver." + _code + ".driver", os.getPath() + _code + "driver" + os.getSuffix());
	}
    }


    public WebDriver openBrowser(String browser) {
	WebDriver driver = null;
	try {
	    String url = DataActions.getPropValue("driver.url");

	    if (url != null && url.trim().length() > 0) {

		if (browser.equalsIgnoreCase("chrome")) {
		    new DesiredCapabilities();
		    DesiredCapabilities caps = DesiredCapabilities.chrome();
		    caps.setBrowserName("chrome");
		    caps.setAcceptInsecureCerts(true);
		    driver = new RemoteWebDriver(new URL(url), caps);
		} else if (browser.equalsIgnoreCase("ie")) {
		    new DesiredCapabilities();
		    DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		    caps.setBrowserName("internet explorer");
		    caps.setJavascriptEnabled(true);
		    caps.setAcceptInsecureCerts(true);
		    driver = new RemoteWebDriver(new URL(url), caps);
		} else if (browser.equalsIgnoreCase("firefox")) {
		    new DesiredCapabilities();
		    DesiredCapabilities caps = DesiredCapabilities.firefox();
		    caps.setBrowserName("firefox");
		    caps.setAcceptInsecureCerts(true);
		    driver = new RemoteWebDriver(new URL(url), caps);
		} else {
		    new DesiredCapabilities();
		    DesiredCapabilities caps = DesiredCapabilities.chrome();
		    caps.setBrowserName("phantomjs");
		    caps.setAcceptInsecureCerts(true);
		    ((DesiredCapabilities) caps).setCapability("takesScreenshot", true);
		    ((DesiredCapabilities) caps).setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { "--load-images=false" });
		    driver = new RemoteWebDriver(new URL(url), caps);
		}
	    } else {
		String osName = System.getProperty("os.name").toLowerCase();
		OS os = osName.contains("mac os") ? OS.macos : (osName.contains("linux") ? OS.linux : OS.win);

		if (browser.equalsIgnoreCase("chrome")) {
		    Browser.chrome.defineDriverPropertyFor(os);
		    DesiredCapabilities caps = DesiredCapabilities.chrome();
		    caps.setBrowserName("chrome");
		    caps.setAcceptInsecureCerts(true);
		    driver = new ChromeDriver(caps);
		} else if (browser.equalsIgnoreCase("firefox")) {
		    Browser.firefox.defineDriverPropertyFor(os);
		    DesiredCapabilities caps = new DesiredCapabilities();
		    caps.setAcceptInsecureCerts(true);
		    caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		    driver = new FirefoxDriver(caps);
		} else {
		    Browser.phantomjs.defineDriverPropertyFor(os);
		    DesiredCapabilities caps = new DesiredCapabilities();
		    ((DesiredCapabilities) caps).setJavascriptEnabled(true);
		    ((DesiredCapabilities) caps).setCapability("takesScreenshot", true);
		    ((DesiredCapabilities) caps).setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, System.getProperty("webdriver.phantomjs.driver"));
		    ((DesiredCapabilities) caps).setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { "--load-images=false" });

		    driver = new PhantomJSDriver(caps);
		}
	    }

	    driver.manage().timeouts().implicitlyWait(Constants.MEDIUM_TIME, TimeUnit.SECONDS);
	    driver.manage().timeouts().pageLoadTimeout(Constants.LONG_TIME, TimeUnit.SECONDS);
	    driver.manage().window().maximize();
	} catch (Exception e) {
	    System.out.println(e);
	}

	Constants.DRIVER = driver;
	return driver;
    }


    public void navigateURL(WebDriver driver, String url) {
	System.out.println("navigateURL : " + url);
	if (driver != null) {
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(Constants.LONG_TIME, TimeUnit.SECONDS);
	}
    }


    public void closeBrowser(WebDriver driver) {
	if (driver != null)
	    Constants.DRIVER.close();
    }


    public void quitBrowser(WebDriver driver) {
	if (driver != null)
		driver.quit();
    }
}
