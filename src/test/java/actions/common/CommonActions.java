package actions.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;

public class CommonActions {

    private static CommonActions commonActions;


    public static CommonActions getCommonObject() {
	if (commonActions == null) {
	    commonActions = new CommonActions();
	}
	return commonActions;
    }


    /**
     * Generate a random string
     * 
     * @param basestring
     * @return
     */
    public static String generateUniqueString(String basestring) {
	Random random = new Random();
	int randomNumber = random.nextInt(1000);
	return basestring + "_" + getDateTime() + randomNumber;
    }


    /**
     * Get current time by defined format
     * 
     * @return
     */
    private static String getDateTime() {
	DateFormat dateFormat = new SimpleDateFormat("SSHHmmddMMyyyy");
	Date date = new Date();
	return dateFormat.format(date);
    }


    /**
     * capture Screenshot
     * 
     * @param WebDriver
     * @param filename
     * @param filepath
     * @return true/false
     * @throws IOException
     */
    public static String captureScreenshot(String filename, String filepath) {
	String path = "";
	try {
	    // Taking the screen using TakesScreenshot Class
	    File objScreenCaptureFile = ((TakesScreenshot) Constants.DRIVER).getScreenshotAs(OutputType.FILE);

	    // Storing the image in the local system.
	    File dest = new File(System.getProperty("user.dir") + File.separator + filepath + File.separator + filename + ".png");
	    FileUtils.copyFile(objScreenCaptureFile, dest);
	    return path = dest.getAbsolutePath();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return path;
    }


    public static String convertImageToURI(String imagePath) {

	System.setProperty("org.uncommons.reportng.escape-output", "false");
	BufferedImage img;
	File image = new File(imagePath);

	String randomPopUpId = generateUniqueString("id");
	String randomButtonId = randomPopUpId + "button";
	try {
	    img = ImageIO.read(image);
	    ByteArrayOutputStream convert = new ByteArrayOutputStream();
	    ImageIO.write(img, "png", convert);
	    String data = DatatypeConverter.printBase64Binary(convert.toByteArray());
	    String imageString = "data:image/png;base64," + data;
	    String htmlScript = "<script>$(document).ready(function(){$( \"#" + randomPopUpId + "\" ).dialog({ autoOpen: false });$( \"#" + randomPopUpId
		    + "\" ).dialog({width:1000},{height:700});$( \"#" + randomButtonId + "\" ).click(function() {$( \"#" + randomPopUpId + "\" ).dialog( \"open\" );});});</script></br><img id=\""
		    + randomButtonId + "\" src=\"" + imageString
		    + "\" style=\"border: 4px solid #f6f7fa;width: 150px;cursor: zoom-in;display: block;margin-top: 15px;\"/></br><div style=\"width: 50%; margin: 0 auto;\" id=\"" + randomPopUpId
		    + "\" > <a href=\"#" + randomPopUpId
		    + "\"  class=\"ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right\"></a><img style=\"width:800px;height:600;\"  src=\"" + imageString
		    + "\"/></div>";
	    image.delete(); // delete image after converted
	    return htmlScript;
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	return null;
    }


    public static void captureScreenshot() {
	String path = captureScreenshot(UUID.randomUUID().toString(), "screenshots");
	String script = convertImageToURI(path);
	Reporter.log(script);
    }
}
