package pages.automationpractice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import static utility.CommonData.HOME;

/**
 * Created by StefanB on 5/15/2017.
 */
public class HomePageAP extends BasePage{
    WebDriverWait _wait;

    /**
     * Locators:
     */
    @FindBy(css = "#header_logo > a")
    WebElement HEADER_LOGO;

    public HomePageAP(WebDriver driver) {
        super(driver);
    }

    public String getPageURL() {
        return HOME;
    }

    /**
     * Methods:
     */

    public String getLogoHREF() {
        return HEADER_LOGO.getAttribute("href");
    }
}
