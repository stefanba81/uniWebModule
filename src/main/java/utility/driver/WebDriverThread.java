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
//    private WebDriver driver;
//    private final String browser;
//    private final String protocol;
//    private final Platform platform;
//    private final String webURL;
//
//    static String chromeDriverPathWin = "D:\\stefanRepo\\webModule\\drivers\\chromedriver.exe";
//    static String chromeDriverPath = "D:\\stefanRepo\\webModule\\drivers\\chromedriver";
//    static String firefoxDriverPath = "D:\\stefanRepo\\webModule\\drivers\\geckodriver.exe";
//
//    @Inject
//    public WebDriverThread(@Named("browser") String browser, @Named("protocol") String protocol, @Named("platform") Platform platform, @Named("url") String webURL) {
//        this.browser = browser;
//        this.protocol = protocol;
//        this.platform = platform;
//        this.webURL = webURL;
//    }
//
//    public WebDriver get() {
//        return setDriver(browser,webURL);
//    }
//
//    private WebDriver setDriver(String browserType, String webURL) {
//        switch (browser) {
//            case CHROME:
//                driver = initChromeDriver(webURL);
//                break;
//            case FIREFOX:
//                driver = initFirefoxDriver(webURL);
//                break;
//            case SAFARI:
//                driver = initSafariDriver(webURL);
//                break;
//            default:
//                System.out.println("browser : " + browserType
//                        + " is invalid, Launching Firefox as browser of choice..");
//                driver = initFirefoxDriver(webURL);
//        }
//        return driver;
//    }
//
////    private void setDriver() {
////        switch (browser) {
////            case CHROME:
////                if (platform == Platform.WINDOWS)
////                    System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
////                else
////                    System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
////                return new ChromeDriver();
////            case SAFARI:
////                return new SafariDriver();
////            default:
////                System.out.println("Browser Type not recognized. Default to Firefox.");
////            case FIREFOX:
////                if (platform == Platform.WINDOWS)
////                    System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
////                else
////                    System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
////                return new FirefoxDriver();
////        }
////    }
//
//    private WebDriver initChromeDriver(String webURL) {
//        System.out.println("Launching google chrome with new profile..");
//        if (platform == Platform.WINDOWS) {
//            System.setProperty("webdriver.chrome.driver", chromeDriverPathWin);
//        } else
//            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.navigate().to(webURL);
//        return driver;
//    }
//
//    private static WebDriver initFirefoxDriver(String webURL) {
//        System.out.println("Launching Firefox browser..");
//        System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
//        WebDriver driver = new FirefoxDriver();
//        driver.manage().window().maximize();
//        driver.navigate().to(webURL);
//        return driver;
//    }
//
//    private static WebDriver initSafariDriver(String webURL) {
//        System.out.println("Launching Safari browser..");
//        WebDriver driver = new SafariDriver();
//        driver.manage().window().maximize();
//        driver.navigate().to(webURL);
//        return driver;
//    }
//
//    @Parameters({ "browserType", "webURL" })
//    @BeforeClass
//    public void initializeTestBaseSetup(String browserType, String webURL) {
//        try {
//            setDriver(browserType, webURL);
//
//        } catch (Exception e) {
//            System.out.println("Error....." + e.getStackTrace());
//        }
//    }
//
//    @AfterClass
//    public void tearDown() {
//        driver.quit();
//    }
}
