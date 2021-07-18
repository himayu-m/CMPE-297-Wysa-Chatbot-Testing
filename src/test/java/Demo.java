import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Demo {

    public static void main(String[] args) throws MalformedURLException {

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        dc.setCapability("platformName", "android");
        AndroidDriver<AndroidElement> ad = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub/"), dc);

        MobileElement el1 = (MobileElement) ad.findElementByAccessibilityId("Wysa");
        el1.click();
        ad.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        MobileElement el2 = (MobileElement) ad.findElementByXPath("//android.widget.LinearLayout[@content-desc=\"Talk\"]/android.widget.ImageView");
        el2.click();
        MobileElement el3 = (MobileElement) ad.findElementByAccessibilityId("Reply or say help…");
        el3.sendKeys("I am feeling stressed");
        MobileElement el4 = (MobileElement) ad.findElementByAccessibilityId("Send");
        el4.click();
        String text = ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.RelativeLayout/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[3]/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView").getText();
        System.out.println(text);
        /*List<AndroidElement> text1 = ad.findElements(By.className("android.widget.TextView"));
        for(AndroidElement ele : text1) {
            String name = ele.getText();
            System.out.println(name);
        }*/
        /*MobileElement el5 = (MobileElement) ad.findElementByAccessibilityId("Yes");
        el5.click();*/
        ad.manage().timeouts().implicitlyWait(25,TimeUnit.SECONDS);
        MobileElement el6 = (MobileElement) ad.findElementByAccessibilityId("Back button");
        el6.click();


    }
}
