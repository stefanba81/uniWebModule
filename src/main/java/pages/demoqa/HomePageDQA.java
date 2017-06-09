package pages.demoqa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import static utility.CommonData.HOME;

/**
 * Created by StefanB on 5/12/2017.
 */
public class HomePageDQA extends BasePage{
    WebDriverWait _wait;

    /**
     * Locators:
     */
    @FindBy(css = ".navbar-header a")
    WebElement NAVBAR_HEADER_LOGO;
    @FindBy(css = "#breadcrumbs > li > span")
    WebElement BREADCRUMB_TEXT;


    public HomePageDQA(WebDriver driver) {
        super(driver);
    }

    public String getPageURL() {
        return HOME;
    }

    public String getNavBarHREF() throws InterruptedException {
        try {
        Thread.sleep(2000);
        return NAVBAR_HEADER_LOGO.getAttribute("href"); }
        catch (Exception e) {
            return NAVBAR_HEADER_LOGO.getAttribute("href");
        }
    }

    public String getBreadcrumbText() {
        return _wait.until(ExpectedConditions.visibilityOf(BREADCRUMB_TEXT)).getText();
    }

}
