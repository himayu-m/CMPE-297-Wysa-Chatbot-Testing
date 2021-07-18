import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import com.google.common.base.Splitter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class LanguageTestAutomate {

    public static URL url;
    public static DesiredCapabilities capabilities;
    public static AndroidDriver<MobileElement> driver;
    public String workingDir;

    Properties properties;
    List<String> inputData;
    List<String> validationData;

    public void readPropertyFile() {

        try {
            workingDir = System.getProperty("user.dir");
            System.out.println(workingDir);

            InputStream inputFile = LanguageTestAutomate.class.getClassLoader().getResourceAsStream("inputData.properties");//new FileInputStream(workingDir + "\\src\\test\\resources\\inputData.properties");
            InputStream validationFile = LanguageTestAutomate.class.getClassLoader().getResourceAsStream("validation.properties");
            properties = new Properties();
            properties.load(inputFile);
            String languageStr = properties.getProperty("language");
            inputData = Splitter.on(";").trimResults().splitToList(languageStr);
            properties = new Properties();
            properties.load(validationFile);
            String validationStr = properties.getProperty("dummy");
            validationData = Splitter.on(";").trimResults().splitToList(validationStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void setupAppium() throws MalformedURLException {

        readPropertyFile();
        System.out.println(inputData);
        System.out.println(validationData);
        /*final String URL_STRING = "http://0.0.0.0:4723/wd/hub/";
        url = new URL(URL_STRING);

        capabilities = new DesiredCapabilities();
        //capabilities.setCapability(MobileCapabilityType.APP, "D:\\MyCourse\\297\\Wysa anxiety depression sleep therapy chatbot_v2.8.7_apkpure.com.apk");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        driver = new AndroidDriver<MobileElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);*/
        //driver.resetApp();
    }

    //@Test(enabled = true)
    public void myFirstTest() {

        /*MobileElement el1 = (MobileElement) driver.findElementByAccessibilityId("Wysa");
        el1.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        MobileElement el2 = (MobileElement) driver.findElementByXPath("//android.widget.LinearLayout[@content-desc=\"Talk\"]/android.widget.ImageView");
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementByAccessibilityId("Reply or say help…");
        el3.sendKeys("Please help me calm my mind");
        MobileElement el4 = (MobileElement) driver.findElementByAccessibilityId("Send");
        el4.click();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        MobileElement el6 = (MobileElement) driver.findElementByAccessibilityId("Reply or say help…");
        el6.sendKeys("I am disturbed");
        el6.click();
        MobileElement el7 = (MobileElement) driver.findElementByAccessibilityId("Send");
        el7.click();*/
    }


}
