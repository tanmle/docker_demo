package actions.config;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;

import actions.common.CommonActions;
import actions.common.VerificationFailures;

public class MethodListener implements IInvokedMethodListener, ITestListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
	log.debug("Before invocation of " + method.getTestMethod().getMethodName());
    }


    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
	log.debug("After invocation of " + method.getTestMethod().getMethodName());
	Reporter.setCurrentTestResult(result);
	if (method.isTestMethod()) {
	    VerificationFailures allFailures = VerificationFailures.getFailures();

	    // Add an existing failure for the result to the failure list.
	    if (result.getThrowable() != null) {
		allFailures.addFailureForTest(result, result.getThrowable());
	    }

	    List<Throwable> failures = allFailures.getFailuresForTest(result);
	    int size = failures.size();

	    if (size > 0) {
		result.setStatus(ITestResult.FAILURE);
		if (size == 1) {
		    result.setThrowable(failures.get(0));
		} else {
		    StringBuffer message = new StringBuffer("Mulitple failures (").append(size).append("):\n");
		    for (int failure = 0; failure < size - 1; failure++) {
			message.append("Failure ").append(failure + 1).append(" of ").append(size).append("\n");
			message.append(Utils.stackTrace(failures.get(failure), false)[1]).append("\n");
		    }
		    Throwable last = failures.get(size - 1);
		    message.append("Failure ").append(size).append(" of ").append(size).append("\n");
		    message.append(last.toString());
		    Throwable merged = new Throwable(message.toString());
		    merged.setStackTrace(last.getStackTrace());
		    result.setThrowable(merged);
		}
	    }
	}
    }

    private static final Log log = LogFactory.getLog(MethodListener.class);


    @Override
    public void onFinish(ITestContext arg0) {
	// TODO Auto-generated method stub

    }


    @Override
    public void onStart(ITestContext arg0) {
	// TODO Auto-generated method stub

    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
	// TODO Auto-generated method stub

    }


    @Override
    public void onTestFailure(ITestResult result) {
	if (result.getThrowable().getMessage().toString().contains("NoSuchElementException") || result.getThrowable().getMessage().toString().contains("Unable to locate element")) {

	    String path = CommonActions.captureScreenshot(getClass().toString().replace("class ", ""), "./Screenshot");
	    String script = CommonActions.convertImageToURI(path);
	    Reporter.log(script);
	}
    }


    @Override
    public void onTestSkipped(ITestResult arg0) {
	// TODO Auto-generated method stub

    }


    @Override
    public void onTestStart(ITestResult arg0) {
	// TODO Auto-generated method stub

    }


    @Override
    public void onTestSuccess(ITestResult arg0) {
	// TODO Auto-generated method stub

    }
}
