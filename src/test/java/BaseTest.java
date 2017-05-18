import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utility.DriverFactory;

/**
 * Created by StefanB on 5/4/2017.
 */
public class BaseTest extends DriverFactory{
    @Inject
    WebDriver driver;

    @BeforeClass
    public void setUp() throws Exception {
        driver = getDriver();
    }

    @Test
    public void verifyHomePage() {
        driver.get("https://google.com");
    }

/*    @AfterClass
    public void tearDown() {
        driver.quit();
    }*/
}
