package pages.demoqa;

import org.openqa.selenium.WebDriver;

/**
 * Created by StefanB on 5/12/2017.
 */
public class HomePageDQA {

    static WebDriver driver;

    public HomePageDQA(WebDriver passedDriver) {
        driver = passedDriver;
    }

    public String getPageURL(){
        return "/";
    }


}
