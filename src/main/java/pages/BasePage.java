package pages;

import junitx.util.PropertyManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.Set;


/**
 * Created by StefanB on 5/4/2017.
 */
public abstract class BasePage extends PageMethods{

    protected WebDriver driver;
    private By signInButton = By.linkText("Sign in");
    private String originalWindowTitle;

    /**
     * This runs post injection
     */
    @Override
    public void init(){
        PageFactory.initElements(driver, this);
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        init();
    }

    public abstract String getPageURL();

    public void openPage() {
        String url = String.format("%s%s%s",getProtocol(), getWebSite(), getPageURL());
        driver.get(url);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getProtocol() {
        return (PropertyManager.getProperty("SECURE") != null &&PropertyManager.getProperty("SECURE").equalsIgnoreCase("true")) ? "https://" : "http://";
    }

    public String getWebSite() {
        String webSite;
        switch (PropertyManager.getProperty("WEBSITE")){
            case "A":
                webSite = PropertyManager.getProperty("URL1");
                break;
            case "B":
                webSite = PropertyManager.getProperty("URL2");
                break;
            default:
                webSite = PropertyManager.getProperty("URL1");
        }
        return webSite;
    }

    public void clickSignInBtn() {
        System.out.println("clicking on sign in button");
        WebElement signInBtnElement=driver.findElement(signInButton);
        if(signInBtnElement.isDisplayed()||signInBtnElement.isEnabled())
            signInBtnElement.click();
        else System.out.println("Element not found");
    }


    public String getPageTitle(){
        String title = driver.getTitle();
        return title;
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
