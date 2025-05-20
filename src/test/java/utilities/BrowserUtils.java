package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BrowserUtils {

    public static WebElement waitForClickability(By locator) {
        return DriverManager.getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForPresence(By locator) {
        return DriverManager.getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static List<WebElement> waitForPresenceAllElement(By locator) {
        return DriverManager.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static WebElement findElement(By locator) {
        return DriverManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static List<WebElement> findElements(By locator) {
        return DriverManager.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static Boolean waitForElementToDisappear(By by) {
         return DriverManager.getWait().until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void sleepFunc(int seconds)
    {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
    }

    
    }


}
