package utility;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import utility.driver.WebDriverThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Listeners(ScreenshotListener.class)
public class DriverFactory {

    private static List<WebDriverThread> webDriverThreadPool = Collections.synchronizedList(new ArrayList<WebDriverThread>());
    private static ThreadLocal<WebDriverThread> driverThread;

    @BeforeSuite(alwaysRun=true)
    public static void instantiateDriverObject() {
        driverThread = ThreadLocal.withInitial(() -> {
            WebDriverThread webDriverThread = new WebDriverThread();
            webDriverThreadPool.add(webDriverThread);
            return webDriverThread;
        });
    }

    public static WebDriver getDriver() throws Exception {
        WebDriver driver = driverThread.get().getDriver();
        driver.manage().window().maximize();
        return driver;
    }
   /*
         @AfterMethod (alwaysRun=true)

      public static void clearCookies() throws Exception {
          getDriver().manage().deleteAllCookies();k
          System.out.println("----------------------------------------------------");
      }
    }

*/
      @AfterMethod(alwaysRun=true)
      public static void closeDriverObjects() {
          for (WebDriverThread webDriverThread : webDriverThreadPool) {
              //webDriverThread.quitDriver();
          }
          System.out.println("----------------------Browser Closed----------------------");
      }


}

