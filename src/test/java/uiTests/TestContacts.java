package uiTests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.BaseTest;
import pages.demoqa.HomePageDQA;

/**
 * Created by StefanB on 5/4/2017.
 */
public class TestContacts extends BaseTest{

    @Test
    public void testContacts() throws Exception {
        WebDriver driver = getDriver();
        HomePageDQA home = new HomePageDQA(driver);
        home.openPage();
    }
}
