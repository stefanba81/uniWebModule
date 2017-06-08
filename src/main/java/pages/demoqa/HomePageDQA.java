package pages.demoqa;

import org.openqa.selenium.WebDriver;
import pages.BasePage;

/**
 * Created by StefanB on 5/12/2017.
 */
public class HomePageDQA extends BasePage{

    public HomePageDQA(WebDriver driver) {
        super(driver);
    }

    public String getPageURL(){
        return "/";
    }


}
