import com.google.common.base.Splitter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class DomainTestAutomate {
    public static URL url;
    public static DesiredCapabilities capabilities;
    public static AndroidDriver<AndroidElement> driver;
    public String workingDir;

    Properties properties;
    List<String> domainInput;
    List<String> unrecValidation;
    List<String> domainAnxiety;
    List<String> domainStress;
    List<String> domainDepression;
    List<String> domainConcept;
    List<String> domainFurther;
    List<String> domainAdvice;

    public void readPropertyFile() {

        try {

            InputStream inputStream = LanguageTestAutomate.class.getClassLoader().getResourceAsStream("inputData.properties");
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            properties = new Properties();
            properties.load(isr);
            String domainStr = properties.getProperty("domain");
            domainInput = Splitter.on(";").trimResults().splitToList(domainStr);
            InputStream validationStream = LanguageTestAutomate.class.getClassLoader().getResourceAsStream("validation.properties");

            InputStreamReader vsr = new InputStreamReader(validationStream, "UTF-8");
            properties = new Properties();
            properties.load(vsr);
            String unrecognizedStr = properties.getProperty("unrecognized");
            unrecValidation = Splitter.on(";").trimResults().splitToList(unrecognizedStr);
            String anxietyStr = properties.getProperty("domain_anxiety");
            domainAnxiety = Splitter.on(";").trimResults().splitToList(anxietyStr);
            String stressStr = properties.getProperty("domain_stress");
            domainStress = Splitter.on(";").trimResults().splitToList(stressStr);
            String depressStr = properties.getProperty("domain_depression");
            domainDepression = Splitter.on(";").trimResults().splitToList(depressStr);
            String conceptStr = properties.getProperty("domain_concept");
            domainConcept = Splitter.on(";").trimResults().splitToList(conceptStr);
            String furtherStr = properties.getProperty("domain_further");
            domainFurther = Splitter.on(";").trimResults().splitToList(furtherStr);
            String adviceStr = properties.getProperty("domain_advice");
            domainAdvice = Splitter.on(";").trimResults().splitToList(adviceStr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean keywordChecker(List<String> check, List<String> validation){
        for(int i=0; i< validation.size();i++){
            String phrase = validation.get(i);
            for(int j=0; j< check.size();j++){
                String test = check.get(j);
                if (test.contains(phrase)){
                    return TRUE;
                }
            }
        }
        return FALSE;
    }

    @BeforeSuite
    public void setupAppium() throws MalformedURLException {

        readPropertyFile();

        final String URL_STRING = "http://0.0.0.0:4723/wd/hub/";
        url = new URL(URL_STRING);
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        driver = new AndroidDriver<AndroidElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        MobileElement el1 = (MobileElement) driver.findElementByAccessibilityId("Wysa");
        el1.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        MobileElement el21 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.TextView[2]");
        el21.click();
    }

    @Test(enabled = true)
    public void myFirstTest() throws InterruptedException, IOException {

        int totalPassed = 0;
        WebDriverWait wait = new WebDriverWait(driver, 120);

        for (int i = 0 ; i < domainInput.size() ; i++) {

            int caseID = i+1;
            int passed = 0;

            List<String> outputData = new ArrayList<>();
            List<Integer> adviceList = new ArrayList<>(Arrays.asList(1,2,11,12,13,14,23,24,25,26,35,36));
            List<Integer> conceptList = new ArrayList<>(Arrays.asList(3,4,5,15,16,17,27,28,29));

            MobileElement elem = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.LinearLayout[@content-desc=\"Talk\"]/android.widget.ImageView")));
            MobileElement el2 = (MobileElement) driver.findElementByXPath("//android.widget.LinearLayout[@content-desc=\"Talk\"]/android.widget.ImageView");
            el2.click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            MobileElement el3 = (MobileElement) driver.findElementByAccessibilityId("Reply or say help…");
            el3.sendKeys(domainInput.get(i));
            System.out.println("Testing Case DK "+ caseID);
            System.out.println("Input: "+domainInput.get(i));
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


            System.out.println("Output: "+outputData);

            if(unrecValidation.contains(outputData.get(0).trim())){
                System.out.println("Acknowledge Problem: Failed");
            }
            else{
                System.out.println("Acknowledge Problem: Passed");
                passed += 1;
            }

            List<String> subject;
            if (caseID >=13 && caseID <=24){
                subject = domainStress;
            }
            else if (caseID >=25 && caseID <=36){
                subject = domainDepression;
            }
            else{
                subject = domainAnxiety;
            }

            if (keywordChecker(outputData, subject)){
                System.out.println("Subject Recognized: Passed");
                passed+=1;
            }
            else{
                System.out.println("Subject Recognized: Failed");
            }

            if (adviceList.contains(caseID)){
                 if (keywordChecker(outputData, domainAdvice)){
                     System.out.println("Provides Advice: Passed");
                     passed +=1;
                 }
                 else{
                     System.out.println("Provides Advice: Failed");
                 }
             }
             else if(conceptList.contains(caseID)){
                 if (keywordChecker(outputData, domainConcept)){
                     System.out.println("Explains Concept: Passed");
                     passed +=1;
                 }
                 else{
                     System.out.println("Explains Concept: Failed");
                 }
             }
             else{
                 if (keywordChecker(outputData, domainFurther)){
                     System.out.println("Asks Further Questions: Passed");
                     passed +=1;
                 }
                 else{
                     System.out.println("Asks Further Questions: Failed");
                 }
             }

            if (passed==3){
                System.out.println("Test Case DK "+ caseID+": PASSED"+"\n");
                totalPassed +=1;
            }
            else{
                System.out.println("Test Case DK "+ caseID+": FAILED"+"\n");
            }

            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            MobileElement el6 = (MobileElement) driver.findElementByAccessibilityId("Back button");
            el6.click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        System.out.println("Total test cases Passed: "+totalPassed);
    }
}
