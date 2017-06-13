package uiTests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.automationpractice.HomePageAP;
import pages.demoqa.HomePageDQA;
import utility.DriverFactory;

/**
 * Created by StefanB on 5/24/2017.
 */
public class TestHomePageAP extends DriverFactory {

    /**
     * Opens HomePage and verifies the Breadcrumb
     *
     * @throws Exception
     */
    @Test
    public void testHomepageBreadcrumb() throws Exception {
        WebDriver driver = getDriver();
        HomePageDQA home = new HomePageDQA(driver);
        home.openPage();
        Assert.assertTrue(home.getBreadcrumbText().equals("Home"), "Incorrect Breadcrumb!");
    }

    /**
     * Opens HomePage and verifies the Navigation Bar Logo - HREF
     *
     * @throws Exception
     */
    @Test
    public void testHomepageLogoHref() throws Exception {
        WebDriver driver = getDriver();
        HomePageDQA home = new HomePageDQA(driver);
        home.openPage();
        Assert.assertTrue(home.getNavBarHREF().equals("http://demoqa.com/"), "Incorrect Logo HREF!");
    }

    /**
     * Opens HomePAge and verifies the title of each content column
     * @throws Exception
     */
    @Test
    public void testContentTitles() throws Exception {
        WebDriver driver = getDriver();
        HomePageDQA home = new HomePageDQA(driver);
        home.openPage();
        System.out.println(home.getContentTitles());
        Assert.assertTrue(home.getContentTitles().contains("Unique & Clean") &&
                home.getContentTitles().contains("Customer Support") && home.getContentTitles()
                .contains("Very Flexible"));
    }

    @Test
    public void testHomepageDQA() throws Exception {
        WebDriver driver = getDriver();
        HomePageAP home = new HomePageAP(driver);
        home.openPage();
        System.out.println(home.getLogoHREF());
    }
}
