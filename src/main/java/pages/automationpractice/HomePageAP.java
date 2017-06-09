package pages.automationpractice;

import org.openqa.selenium.WebDriver;
import pages.BasePage;

import static utility.CommonData.HOME;

/**
 * Created by StefanB on 5/15/2017.
 */
public class HomePageAP extends BasePage{

    public HomePageAP(WebDriver driver) {
        super(driver);
    }

    public String getPageURL() {
        return HOME;
    }
}
