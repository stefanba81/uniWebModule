package utility.driver;

import junitx.util.PropertyManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;
import static org.openqa.selenium.remote.BrowserType.*;

/**
 * Created by StefanB on 5/4/2017.
 */
public class WebDriverThread {
    private WebDriver webdriver;
    private DriverType selectedDriverType;

    private final DriverType defaultDriverType = DriverType.CHROME;
    private final String browser = PropertyManager.getProperty("BROWSER", defaultDriverType.toString());
    private final String platform = PropertyManager.getProperty("OS", "abc").toUpperCase();
    private final boolean useRemoteWebDriver = Boolean.parseBoolean(PropertyManager.getProperty("USE_GRID"));
    private final boolean proxyEnabled = Boolean.parseBoolean(PropertyManager.getProperty("PROXY"));
    private final String proxyHostname = PropertyManager.getProperty("proxyHost");
    private final Integer proxyPort = Integer.parseInt(PropertyManager.getProperty("proxyPort"));
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);

    public WebDriver getDriver() throws Exception {
        if (null == webdriver) {
            Proxy proxy = null;
            if (proxyEnabled) {
                proxy = new Proxy();
                proxy.setProxyType(MANUAL);
                proxy.setHttpProxy(proxyDetails);
                proxy.setSslProxy(proxyDetails);
            }
            determineEffectiveDriverType();
            DesiredCapabilities desiredCapabilities = selectedDriverType.getDesiredCapabilities(proxy);
            instantiateWebDriver(desiredCapabilities);
        }

        return webdriver;
    }

    public void quitDriver() {
        if (null != webdriver) {
            webdriver.quit();
        }
    }

    private void determineEffectiveDriverType() {
        DriverType driverType = defaultDriverType;
        try {
            driverType = DriverType.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    private void instantiateWebDriver(DesiredCapabilities desiredCapabilities) throws MalformedURLException {
        System.out.println(" ");
        System.out.println("Current Operating System: " + platform);
        System.out.println("Current Browser Selection: " + selectedDriverType);
        System.out.println(" ");

        if (useRemoteWebDriver) {
            System.out.println("Going to use REMOTE WEB DRIVER ");
            URL seleniumGridURL = new URL(System.getProperty("seleniumGridURL"));
            String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
            String desiredPlatform = System.getProperty("desiredPlatform");


            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }

            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                desiredCapabilities.setVersion(desiredBrowserVersion);
            }

            webdriver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        } else {
            System.out.println("Going to use LOCAL WEB DRIVER");
            switch (browser) {
                case CHROME:
                    if (platform.equals(Platform.WINDOWS.toString()))
                        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
                    else
                        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
                    webdriver = selectedDriverType.getWebDriverObject(desiredCapabilities);
                    break;
                case SAFARI:
                    webdriver = selectedDriverType.getWebDriverObject(desiredCapabilities);
                    break;
                default:
                    System.out.println("Browser Type not recognized. Default to Firefox.");
                    break;
                case FIREFOX:
                    if (platform.equals(Platform.WINDOWS.toString()))
                        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
                    else
                        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
                    webdriver = selectedDriverType.getWebDriverObject(desiredCapabilities);
                    break;
            }
        }
    }
}
