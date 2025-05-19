package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.BrowserUtils;
import utilities.DriverManager;
import pages.locators.SearchPageLocators;

import java.util.List;

public class SearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public SearchPage() {
        this.driver = DriverManager.getDriver();
        this.wait = DriverManager.getWait();
        this.actions = new Actions(driver);
    }



    public void openDepartureTimeFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(SearchPageLocators.DEPERTURE_TIME_FILTER_BUTTON)).click();
    }

    public void setDepartureTimeRange(int departureTime, int returnTime) {
        WebElement step = driver.findElement(SearchPageLocators.SLIDER_STEP);
        int width = step.getSize().getWidth();

        int offsetStart = (width / 24) * departureTime;
        int offsetEnd = (width / 24) * returnTime;

        WebElement leftHandle = BrowserUtils.findElement(SearchPageLocators.SLIDER_LEFT_HANDLE);
        actions.clickAndHold(leftHandle).moveByOffset(offsetStart, 0).release().perform();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(SearchPageLocators.FILTER_LOADING));

        WebElement rightHandle = BrowserUtils.findElement(SearchPageLocators.SLIDER_RIGHT_HANDLE);
        actions.dragAndDropBy(rightHandle, -(width - offsetEnd), 0).perform();
    }

    public void isCorrectTime (int departureTime, int returnTime) {
        WebElement testIsOk = BrowserUtils.findElement(SearchPageLocators.FILTER_SLIDER_CONTENT);

        String expectedText = String.format("%02d:%02d ile %02d:%02d arası", departureTime, 0, returnTime, 0);
        Assert.assertEquals(expectedText, testIsOk.getText());
    }

    public void flightListIsAvailable() {
        WebElement flightList = BrowserUtils.findElement(SearchPageLocators.FLIGHT_LIST_DIV);
        Assert.assertTrue( flightList.isDisplayed());

        List<WebElement> flight = BrowserUtils.findElements(SearchPageLocators.FLIGHTS);
        Assert.assertFalse("Uçuş listesi boş!", flight.isEmpty());
    }

    public void routeIsCorrect (String fromCity,String toCity){
        WebElement element = BrowserUtils.findElement(By.xpath("//div[@class='form-header active']//strong[@class='graphic-strong']"));
        String text = "";
        if(element.isDisplayed()) {
            text = element.getText().trim();
            String fixedText = text.replaceAll("([a-zçğıöşü])([A-ZÇĞIÖŞÜ])", "$1 $2");

            String expectedText = (fromCity + " " + toCity).trim();
            Assert.assertEquals(expectedText, fixedText);
        } else {
            Assert.fail("Element is not displayed");
        }

    }

    }





