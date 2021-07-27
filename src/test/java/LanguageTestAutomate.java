import com.google.common.base.Splitter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
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
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class LanguageTestAutomate {

    public static URL url;
    public static DesiredCapabilities capabilities;
    public static AndroidDriver<MobileElement> driver;

    Properties properties;
    List<String> languageInput;
    List<String> unrecognizedLi;
    List<String> subjectKeywordLi;
    Integer failCount = 0;

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
            unrecognizedLi = Splitter.on(";").trimResults().splitToList(unrecognizedStr);
            String subjectStr = properties.getProperty("subject_keywords");
            subjectKeywordLi = Splitter.on(";").trimResults().splitToList(subjectStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean grammarChecker(List<String> check) throws IOException {
        JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());

        for(int i = 0 ; i < check.size() ; i++) {
            List<RuleMatch> matches = langTool.check(check.get(i));
            if (matches.size()>0) {
                if(matches.toString().contains("Possible spelling mistake found") || matches.toString().contains("EXTREME_ADJECTIVES") ||
                        matches.toString().contains("SOME_OF_THE") || matches.toString().contains("DAY_TO_DAY_HYPHEN")) {
                    return TRUE;
                }
                return FALSE;
            }
        }
        return TRUE;
    }

    @BeforeSuite
    public void setupAppium() throws MalformedURLException, IOException {

        readPropertyFile();

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
//        MobileElement el21 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.TextView[2]");
//        el21.click();
    }

    @Test(enabled = true)
    public void myFirstTest() throws InterruptedException, IOException {

        WebDriverWait wait = new WebDriverWait(driver, 120);
        for (int i = 0 ; i < languageInput.size() ; i++) {
            int caseID = i+1;
            List<String> outputData = new ArrayList<>();
            MobileElement elem = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.LinearLayout[@content-desc=\"Talk\"]/android.widget.ImageView")));
            MobileElement el2 = (MobileElement) driver.findElementByXPath("//android.widget.LinearLayout[@content-desc=\"Talk\"]/android.widget.ImageView");
            el2.click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            MobileElement el3 = (MobileElement) driver.findElementByAccessibilityId("Reply or say help…");
            el3.sendKeys(languageInput.get(i));
            MobileElement el4 = (MobileElement) driver.findElementByAccessibilityId("Send");
            el4.click();
            TimeUnit.SECONDS.sleep(17);
            List<MobileElement> text1 = driver.findElements(By.className("android.widget.TextView"));
            for(MobileElement ele : text1) {
                outputData.add(ele.getText());
            }
            outputData.remove(0);
            outputData.remove(0);
            outputData.remove(0);

            boolean acknowledgeFlag = true;
            boolean grammarFlag = false;

            System.out.println("Test case " + caseID+".1");
            System.out.println("Input : " + languageInput.get(i));
            System.out.println("Output : " + outputData);
            //Identified One of unrecognized keywords
            if(unrecognizedLi.contains(outputData.get(0).trim())) {
                acknowledgeFlag = false;
            }
            System.out.println("Acknowledges Problem : "+acknowledgeFlag);
            if(grammarChecker(outputData)) {
                grammarFlag = true;
            }
            System.out.println("Grammar/Semantics Check : "+grammarFlag);

            boolean subjectFlag = subjectKeywordLi.stream().anyMatch(str -> outputData.get(0).toLowerCase().contains(str.toLowerCase()));
            System.out.println("Subject Recognized : " +subjectFlag);

            if(acknowledgeFlag && grammarFlag && subjectFlag) {
                System.out.println(caseID+".1 Status : Passed");
            } else {
                failCount++;
                System.out.println(caseID+".1 Status : Failed");
            }
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            MobileElement el6 = (MobileElement) driver.findElementByAccessibilityId("Back button");
            el6.click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        System.out.println("Total no of test cases that failed : " +failCount);
    }
}
