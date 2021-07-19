import com.google.common.base.Splitter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class LanguageTestAutomate {

    public static URL url;
    public static DesiredCapabilities capabilities;
    public static AndroidDriver<AndroidElement> driver;

    Properties properties;
    List<String> languageInput;
    List<String> unrecValidation;
    public void readPropertyFile() {

        try {
            InputStream inputStream = LanguageTestAutomate.class.getClassLoader().getResourceAsStream("inputData.properties");
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            properties = new Properties();
            properties.load(isr);
            String languageStr = properties.getProperty("language");
            languageInput = Splitter.on(";").trimResults().splitToList(languageStr);
            InputStream validationStream = LanguageTestAutomate.class.getClassLoader().getResourceAsStream("validation.properties");
            InputStreamReader vsr = new InputStreamReader(validationStream, "UTF-8");
            properties = new Properties();
            properties.load(vsr);
            String unrecognizedStr = properties.getProperty("unrecognized");
            unrecValidation = Splitter.on(";").trimResults().splitToList(unrecognizedStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void setupAppium() throws MalformedURLException {

        readPropertyFile();
        System.out.println(languageInput);
        System.out.println(unrecValidation);

        final String URL_STRING = "http://0.0.0.0:4723/wd/hub/";
        url = new URL(URL_STRING);
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        driver = new AndroidDriver<AndroidElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        MobileElement el1 = (MobileElement) driver.findElementByAccessibilityId("Wysa");
//        el1.click();
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        MobileElement el21 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.TextView[2]");
//        el21.click();
    }

    @Test(enabled = true)
    public void myFirstTest() throws InterruptedException {

        for (int i = 0 ; i < languageInput.size() ; i++) {
            int caseID = i+1;
            List<String> outputData = new ArrayList<>();;
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            MobileElement el2 = (MobileElement) driver.findElementByXPath("//android.widget.LinearLayout[@content-desc=\"Talk\"]/android.widget.ImageView");
            el2.click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            MobileElement el3 = (MobileElement) driver.findElementByAccessibilityId("Reply or say help…");
            el3.sendKeys(languageInput.get(i));
            MobileElement el4 = (MobileElement) driver.findElementByAccessibilityId("Send");
            el4.click();
            TimeUnit.SECONDS.sleep(17);
            List<AndroidElement> text1 = driver.findElements(By.className("android.widget.TextView"));
            for(MobileElement ele : text1) {
                outputData.add(ele.getText());
            }

            outputData.remove(0);
            outputData.remove(0);
            outputData.remove(0);
            System.out.println("Testing Case "+ caseID+".1");
            //System.out.println(outputData);

            int passed = 0;

            if(unrecValidation.contains(outputData.get(0))){
                System.out.println("Acknowledge Problem: Failed");
            }
            else{
                System.out.println("Acknowledge Problem: Passed");
                passed += 1;
            }

//            if (grammarCheck){
//                System.out.println("Grammar and Semantics: Passed");
//                passed += 1;
//            }
//            else{
//                System.out.println("Grammar and Semantics: Failed");
//            }
//
//            if (keyword){
//                System.out.println("Subject Recognized: Passed");
//                passed+=1;
//            }
//            else{
//                System.out.println("Subject Recognized: Failed");
//            }
//
//            if (passed==3){
//                System.out.println("Test Case "+ caseID+".1: Passed");
//            }
//            else{
//                System.out.println("Test Case "+ caseID+".1: Failed");
//            }

            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            MobileElement el6 = (MobileElement) driver.findElementByAccessibilityId("Back button");
            el6.click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }
}
