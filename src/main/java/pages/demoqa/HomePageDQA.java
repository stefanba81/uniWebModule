package pages.demoqa;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.util.List;
import java.util.stream.Collectors;

import static utility.CommonData.HOME;

/**
 * Created by StefanB on 5/12/2017.
 */
public class HomePageDQA extends BasePage {
    WebDriverWait _wait;

    /**
     * Locators:
     */
    @FindBy(css = ".navbar-header a")
    WebElement NAVBAR_HEADER_LOGO;
    @FindBy(css = "#breadcrumbs > li > span")
    WebElement BREADCRUMB_TEXT;
    @FindBy(css = ".entry-content .col-md-4 > h5")
    List<WebElement> CONTENT_COLUMNS_TITLES;


    public HomePageDQA(WebDriver driver) {
        super(driver);
    }

    public String getPageURL() {
        return HOME;
    }

    public String getNavBarHREF() throws InterruptedException {
        try {
            Thread.sleep(2000);
            return NAVBAR_HEADER_LOGO.getAttribute("href");
        } catch (StaleElementReferenceException e) {
            return NAVBAR_HEADER_LOGO.getAttribute("href");
        }
    }

    public String getBreadcrumbText() {
        return BREADCRUMB_TEXT.getText();
    }

    public List<String> getContentTitles() {
        return CONTENT_COLUMNS_TITLES.stream().map(WebElement::getText).collect(Collectors.toList());
    }

}
