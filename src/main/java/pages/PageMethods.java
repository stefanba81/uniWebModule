package pages;

import com.google.inject.Inject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.Initializer;
import utility.Print;


/**
 * Created by StefanB on 5/15/2017.
 */
public class PageMethods implements Initializer{

    @Inject
    WebDriver driver;
    @Inject
    WebDriverWait _wait;
    @Inject
    Print print;

    /**
     * This runs post injection
     */
    @Override
    public void init(){
        PageFactory.initElements(driver, this);
    }

    /**
     * Waits for the element to be displayed. Try for 10 times max.Sleep 1 seonds in between tries
     *
     * @param webElement
     * @throws Exception
     */
    public void waitForElementPresent(WebElement webElement) throws Exception {
        int maxRetries = 5;
        for (int i = 0; i < maxRetries; i++) {
            try {
                if (webElement.isDisplayed()) {
                    return;
                }
            } catch (NoSuchElementException e) {
                Thread.sleep(1000);
            }
        }
    }

    /**
     * Clicks on an element
     *
     * @param element
     * @param properName
     */
    protected void clickElement(WebElement element, String properName) {
        try {
            _wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            print.appendInfo("Clicked element: " + properName);
            // Wait after a click occurs for time to load the page
            Thread.sleep(1500L);
        } catch (NoSuchElementException e) {
            print.appendErrorWithFail("Unable to find '" + properName + "' to be clicked.", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clicks on an element by passing the cssSelector String
     *
     * @param locator
     * @param properName
     */
    protected void clickElement(String locator, String properName) {
        try {
            _wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locator)));
            driver.findElement(By.cssSelector(locator)).click();
            print.appendInfo("Clicked element: " + properName);
        } catch (NoSuchElementException e) {
            print.appendErrorWithFail("Unable to find '" + properName + "' to be clicked.", e);
        }
    }

    public static ExpectedCondition<Boolean> invisibilityOfElementLocated(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return !(element.isDisplayed());
                } catch (NoSuchElementException e) {
                    // Returns true because the element is not present in DOM. The
                    // try block checks if the element is present but is invisible.
                    return true;
                } catch (StaleElementReferenceException e) {
                    // Returns true because stale element reference implies that element
                    // is no longer visible.
                    return true;
                }
            }

            @Override
            public String toString() {
                return "element no longer visible: " + element;
            }
        };
    }

    /**
     * WAITS
     */

    /**
     * Wait until -> visibility of elements located
     *
     * @param element
     * @return
     */
    protected ExpectedCondition<Boolean> visibilityOfElementLocated(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return element.isDisplayed();
                } catch (NoSuchElementException e) {
                    // Returns false because the element is not present in DOM. The
                    // try block checks if the element is present but is invisible.
                    return false;
                } catch (StaleElementReferenceException e) {
                    // Returns false because stale element reference implies that element
                    // is no longer visible.
                    return false;
                }
            }

            @Override
            public String toString() {
                return "element to be visible: " + element;
            }
        };
    }

    public void pageDown(){
        Actions action = new Actions(driver);
        action.sendKeys(Keys.PAGE_DOWN);
        action.perform();
    }

    public String jsScrollToElem() {
        return "arguments[0].scrollIntoView(true);";
    }

    public void jsScrollToElemBycssLocator(String cssSelector) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript(String.format("document.querySelector('%s').scrollTop = %d", cssSelector, 1200));
    }
}
