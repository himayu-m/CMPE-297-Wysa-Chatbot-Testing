import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import com.google.common.base.Splitter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.net.URL;
import java.util.ArrayList;
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
    List<String> domainInput;
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
            String domainStr = properties.getProperty("domain");
            domainInput = Splitter.on(";").trimResults().splitToList(domainStr);
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
        System.out.println(domainInput);
        System.out.println(validationData);

        final String URL_STRING = "http://0.0.0.0:4723/wd/hub/";
        url = new URL(URL_STRING);
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        driver = new AndroidDriver<MobileElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        MobileElement el1 = (MobileElement) driver.findElementByAccessibilityId("Wysa");
//        el1.click();
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test(enabled = true)
    public void myFirstTest() throws InterruptedException {

        for (int i=0;i<inputData.size();i++){
            List<String> outputData = new ArrayList<>();;
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            MobileElement el2 = (MobileElement) driver.findElementByXPath("//android.widget.LinearLayout[@content-desc=\"Talk\"]/android.widget.ImageView");
            el2.click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            MobileElement el3 = (MobileElement) driver.findElementByAccessibilityId("Reply or say help…");
            el3.sendKeys(inputData.get(i));
            MobileElement el4 = (MobileElement) driver.findElementByAccessibilityId("Send");
            el4.click();
            java.util.concurrent.TimeUnit.SECONDS.sleep(15);
            List<MobileElement> text1 = driver.findElements(By.className("android.widget.TextView"));
            for(MobileElement ele : text1) {
                outputData.add(ele.getText());
            }
            outputData.remove(0);
            outputData.remove(0);
            outputData.remove(0);
            outputData.remove(outputData.size()-1);
            System.out.println(outputData);
            MobileElement el6 = (MobileElement) driver.findElementByAccessibilityId("Back button");
            el6.click();
        }
    }
}
