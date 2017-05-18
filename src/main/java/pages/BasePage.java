package pages;

import junitx.util.PropertyManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;


/**
 * Created by StefanB on 5/4/2017.
 */
public abstract class BasePage extends PageMethods{
    protected WebDriver driver;
    private By signInButton = By.linkText("Sign in");
    private String originalWindowTitle;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public abstract String getPageURL();

    public void openPage() {
        String url = String.format("%s%s", PropertyManager.getProperty("URL"), getPageURL());
        for(int i = 0; i < 2; i++) {
            driver.get(url);
            if(!driver.getPageSource().contains("Uh oh! The Kitchen's a mess!")){
                return;
            }
        }
    }

    public void clickSignInBtn() {
        System.out.println("clicking on sign in button");
        WebElement signInBtnElement=driver.findElement(signInButton);
        if(signInBtnElement.isDisplayed()||signInBtnElement.isEnabled())
            signInBtnElement.click();
        else System.out.println("Element not found");
    }

    public void clickImagesLink() {
        //It should have a logic to click on images link
        //And it should navigate to google images page
    }

    public String getPageTitle(){
        String title = driver.getTitle();
        return title;
    }

    public boolean verifyBasePageTitle() {
        String expectedPageTitle="Google";
        return getPageTitle().contains(expectedPageTitle);
    }

    /**
     * This switches to the window title you asked for
     *
     * @param windowTitle title of the window where you want to go
     */
    public String handleMultipleWindows(String windowTitle) {
        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {
            driver.switchTo().window(window);
            if (driver.getTitle().contains(windowTitle)) {
                return window;
            }
        }
        return "";
    }

    /**
     * Verifies if the given window title is displayed
     *
     * @param windowTitle
     * @return true or false if the window titles is on screen
     */
    public boolean verifyWindowTitle(String windowTitle) {
        boolean result = false;
        String parentWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {
            driver.switchTo().window(window);
            if (driver.getTitle().contains(windowTitle)) {
                print.appendInfo("Opened page: " + driver.getCurrentUrl());
                result = true;
                driver.close();
                driver.switchTo().window(parentWindow);
                print.appendInfo("Switch page back to: " + driver.getCurrentUrl());
            }
        }
        return result;
    }

    public boolean verifyWindowIsOnScreen(String windowTitle) {
        boolean result = false;
        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {
            driver.switchTo().window(window);
            if (driver.getTitle().contains(windowTitle)) {
                print.appendInfo("Opened page: " + driver.getCurrentUrl());
                result = true;
            }
        }
        return result;
    }

    public void closeModal(String windowTitle){
        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {
            WebDriver modal = driver.switchTo().window(window);
            if (driver.getTitle().contains(windowTitle)) {
                modal.close();
            }
        }
    }

    /**
     * This switches to the window url you asked for
     *
     * @param windowUrl Url of the window where you want to go
     */
    public void handleMultipleWindowsByUrl(String windowUrl) {
        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {
            try {
                driver.switchTo().window(window);
            } catch (NoSuchWindowException e) {
                print.appendError(e.getLocalizedMessage() + " - Checking next window.");
                continue;
            }
            if (driver.getCurrentUrl().contains(windowUrl)) {
                return;
            }
        }
        windows.stream().forEach(print::appendError);
    }

    /**
     * Saves the title of the current window so that you can go back later on once you done with the switched window
     */
    public void saveOriginalWindowTitle() {
        originalWindowTitle = driver.getTitle();
    }

    /**
     * Gives back the original title window
     *
     * @return
     */
    public String getOriginalWindowTitle() {
        return originalWindowTitle;
    }
}
