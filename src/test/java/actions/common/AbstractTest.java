package actions.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;

public class AbstractTest extends BrowserManagement {
    public Log log = LogFactory.getLog(getClass());


    /**
     * verify True
     * 
     * @param condition
     * @param halt
     * @return passed/failed
     */
    protected boolean verifyTrue(boolean condition, boolean halt) {
	return verify(() -> Assert.assertTrue(condition), halt);
    }


    /**
     * verify true
     * 
     * @param condition
     * @return true/false
     */
    protected boolean verifyTrue(boolean condition) {
	return verifyTrue(condition, false);
    }


    /**
     * verify false
     * 
     * @param condition
     * @param halt
     * @return passed/failed
     */
    protected boolean verifyFalse(boolean condition, boolean halt) {
	return verify(() -> Assert.assertFalse(condition), halt);
    }


    /**
     * verify false
     * 
     * @param condition
     * @return true/false
     */
    protected boolean verifyFalse(boolean condition) {
	return verifyFalse(condition, false);
    }


    /**
     * verify equal
     * 
     * @param actual
     * @param expected
     * @param halt
     * @return passed/failed
     */
    protected boolean verifyEquals(Object actual, Object expected, boolean halt) {
	return verify(() -> Assert.assertEquals(actual, expected), halt);
    }


    /**
     * verify equal
     * 
     * @param actual
     * @param expected
     * @return true/false
     */
    protected boolean verifyEquals(Object actual, Object expected) {
	return verifyEquals(actual, expected, false);
    }


    /**
     * 
     * @param ass
     * @return true/false
     */
    protected boolean verify(Assertion ass) {
	return verify(ass, false);
    }


    /**
     * 
     * @param ass
     * @param halt
     * @return true/false
     */
    protected boolean verify(Assertion ass, boolean halt) {
	boolean pass = true;
	if (halt == false) {
	    try {
		ass.assertIt();
		log.info("===PASSED===");
		if (DataActions.getPropValue("tests.captureOnAssertion", "no").equals("yes"))
		    CommonActions.captureScreenshot();
	    } catch (Throwable e) {
		log.info("===FAILED===");
		pass = false;
		VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
		CommonActions.captureScreenshot();
	    }
	} else {
	    ass.assertIt();
	}
	return pass;
    }
}

@FunctionalInterface
interface Assertion {
    void assertIt();
}
