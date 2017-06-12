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

    @Test
    public void testHomepageSections() throws Exception {
        WebDriver driver = getDriver();
        HomePageDQA home = new HomePageDQA(driver);
        home.openPage();
        Assert.assertTrue(home.getBreadcrumbText().equals("Home"), "Incorrect Breadcrumb!");
        //Assert.assertTrue(home.getNavBarHREF().equals("http://demoqa.com/"), "Incorrect Logo HREF!");
    }

    @Test
    public void testHomepageDQA() throws Exception {
        WebDriver driver = getDriver();
        HomePageAP home = new HomePageAP(driver);
        home.openPage();
        System.out.println(home.getLogoHREF());
    }
}
