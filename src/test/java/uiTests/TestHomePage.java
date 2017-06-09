package uiTests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.demoqa.HomePageDQA;
import utility.DriverFactory;

/**
 * Created by StefanB on 5/24/2017.
 */
public class TestHomePage extends DriverFactory {

    @Test
    public void testHomepageSections() throws Exception {
        WebDriver driver = getDriver();
        HomePageDQA home = new HomePageDQA(driver);
        home.openPage();
        Thread.sleep(3000);
        Assert.assertTrue(home.getBreadcrumbText().equals("Home"), "Incorrect Breadcrumb!");
        //Assert.assertTrue(home.getNavBarHREF().equals("http://demoqa.com/"), "Incorrect Logo HREF!");
    }
}
