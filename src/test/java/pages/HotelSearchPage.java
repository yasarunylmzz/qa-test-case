package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.DriverManager;

import java.time.Duration;

public class HotelSearchPage {

    public static void adjustGuestCount(WebDriverWait wait,int desiredCount, By countLocator, By plusButtonLocator, By minusButtonLocator) {
        WebElement countElement = wait.until(ExpectedConditions.visibilityOfElementLocated(countLocator));
        int currentCount = Integer.parseInt(countElement.getText());

        WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(plusButtonLocator));
        WebElement minusButton = wait.until(ExpectedConditions.elementToBeClickable(minusButtonLocator));
        while (currentCount < desiredCount) {
            plusButton.click();
            currentCount++;
        }

        while (currentCount > desiredCount) {
            minusButton.click();
            currentCount--;
        }
    }
}
