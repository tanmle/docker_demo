package actions.common;

import org.openqa.selenium.WebDriver;

public class Constants {
    /********** WebDriver *********/
    public static WebDriver DRIVER = null;

    /********** File Location **********/
    public static final String TEMPLATE_FILE_PATH_REMOTE = "/home/Test 2 - Template Import.xlsx";
    public static final String TEMPLATE_FILE_PATH_LOCALE = "src//test//resources//data//TemplateImport.xlsx";

    /********** Value from ADH Configuration *********/
    public static String LOGIN_URL = DataActions.getPropValue("profile.url.login");
    public static String USERNAME = DataActions.getPropValue("profile.user.simple.email");
    public static String PASSWORD = DataActions.getPropValue("profile.user.simple.password");
    public static String SELENIUM_ADMIN_USERNAME = DataActions.getPropValue("profile.user.selenium.test.admin.email");
    public static String SELENIUM_ADMIN_PASSWORD = DataActions.getPropValue("profile.user.selenium.test.admin.password");
    public static String SELENIUM_USER1_USERNAME = DataActions.getPropValue("profile.user.selenium.user1.email");
    public static String SELENIUM_USER1_PASSWORD = DataActions.getPropValue("profile.user.selenium.user1.password");
    public static String SELENIUM_USER2_USERNAME = DataActions.getPropValue("profile.user.selenium.user2.email");
    public static String SELENIUM_USER2_PASSWORD = DataActions.getPropValue("profile.user.selenium.user2.password");

    /********** Timing Settings *******/
    public static final int MINI_TIME = 2;
    public static final int SHORT_TIME = 5;
    public static final int MEDIUM_TIME = 30;
    public static final int LONG_TIME = 120;
}
