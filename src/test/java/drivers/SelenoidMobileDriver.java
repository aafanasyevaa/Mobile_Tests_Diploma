package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.SelenoidConfig;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SelenoidMobileDriver implements WebDriverProvider {

    public static SelenoidConfig selenoidConfig = ConfigFactory.create(SelenoidConfig.class);

    private URL apkUrl() {
        try {
            return new URL(
                    "https://github.com/wikimedia/apps-android-wikipedia/releases/download/latest/app-alpha-universal-release.apk");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL getSelenoidUrl() {
        try {
            return new URL(selenoidConfig.selenoidUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {

        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("deviceName", selenoidConfig.deviceName());
        desiredCapabilities.setCapability("version", selenoidConfig.version());
        desiredCapabilities.setCapability("locale", selenoidConfig.locale());
        desiredCapabilities.setCapability("language", selenoidConfig.language());
        desiredCapabilities.setCapability("enableVNC", true);
        desiredCapabilities.setCapability("enableVideo", true);
        desiredCapabilities.setCapability("appPackage", selenoidConfig.appPackage());
        desiredCapabilities.setCapability("appActivity", selenoidConfig.appActivity());
        desiredCapabilities.setCapability("app", apkUrl());

        return new AndroidDriver(getSelenoidUrl(), desiredCapabilities);
    }
}