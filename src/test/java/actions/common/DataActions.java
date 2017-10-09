package actions.common;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class DataActions {

    public static String readXmlData(String fileLocation, String dataNode) {
	String testinput;
	String projectPath = System.getProperty("user.dir");
	File fXmlFile = new File(projectPath + File.separator + fileLocation);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;

	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	}

	Document doc = null;
	try {
	    doc = dBuilder.parse(fXmlFile);
	} catch (SAXException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	XPath xPath = XPathFactory.newInstance().newXPath();
	String expression = String.format(dataNode);
	Node node = null;
	try {
	    node = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
	} catch (XPathExpressionException e) {
	    e.printStackTrace();
	}

	testinput = node != null ? (node.getTextContent()) : "cannot read the test data xml file";
	return testinput;
    }


    public static JSONObject readJsonData(String filePath, String className) {
	JSONObject testObject = null;

	try {
	    FileReader reader = new FileReader(filePath);
	    JSONParser jsonParser = new JSONParser();
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
	    testObject = (JSONObject) jsonObject.get(className);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return testObject;
    }

    /*
     * Properties mode
     */
    private static Properties _conf, _profile, _default;


    private static Properties _propsForName(String propFileName) {
	InputStream inputStream = null;
	try {
	    inputStream = DataActions.class.getClassLoader().getResourceAsStream(propFileName);

	    if (inputStream != null) {
		Properties prop = new Properties();
		prop.load(inputStream);
		return prop;
	    } else {
		// throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		return null;
	    }
	} catch (Exception e) {
	    System.out.println("Exception: " + e);
	} finally {
	    if (inputStream != null)
		try {
		    inputStream.close();
		} catch (Exception e) {
		}
	}
	return null;
    }


    private static void _initProps() {
	if (_conf == null) {
	    _conf = _propsForName("conf.properties");
	    _default = _propsForName("profiles/default.properties");
	    String profile = System.getProperty("profile");
	    if (profile != null)
		_profile = _propsForName("profiles/" + profile + ".properties");
	}
    }


    public static String getPropValue(String key) {
	return getPropValue(key, null);
    }


    public static String getPropValue(String key, String defaultValue) {
	_initProps();

	if (System.getProperty(key) != null)
	    return System.getProperty(key);

	if (_conf.containsKey(key))
	    return _conf.getProperty(key);

	if (_profile != null && _profile.containsKey(key))
	    return _profile.getProperty(key);

	if (_default != null && _default.containsKey(key))
	    return _default.getProperty(key);

	return defaultValue;
    }

}
