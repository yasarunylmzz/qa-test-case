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

import java.time.Duration;
import java.util.List;

public class SearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public SearchPage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.actions = new Actions(driver);
    }

    private final By departureTimeFilterButton = By.xpath("//div[@class='ctx-filter-departure-return-time card-header']");
    private final By sliderStep = By.className("rc-slider-step");
    private final By sliderLeftHandle = By.cssSelector(".rc-slider-handle.rc-slider-handle-1");
    private final By sliderRightHandle = By.cssSelector(".rc-slider-handle.rc-slider-handle-2");
    private final By filterLoading = By.xpath("//div[@class='filter-loading']");
    private final By filterSliderContent = By.xpath("//div[@class='filter-slider-content']");
    private final By flightListDiv = By.xpath("//div[@class='flight-list flight-list-departure    domesticList']");
    private final By flights = By.xpath("//div[@data-flight-index]");



    public void openDepartureTimeFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(departureTimeFilterButton)).click();
    }

    public void setDepartureTimeRange(int departureTime, int returnTime) {
        WebElement step = driver.findElement(sliderStep);
        int width = step.getSize().getWidth();

        int offsetStart = (width / 24) * departureTime;
        int offsetEnd = (width / 24) * returnTime;

        WebElement leftHandle = driver.findElement(sliderLeftHandle);
        actions.clickAndHold(leftHandle).moveByOffset(offsetStart, 0).release().perform();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(filterLoading));

        WebElement rightHandle = driver.findElement(sliderRightHandle);
        actions.dragAndDropBy(rightHandle, -(width - offsetEnd), 0).perform();
    }

    public void isCorrectTime (int departureTime, int returnTime) {
        WebElement testIsOk = BrowserUtils.findElement(driver,filterSliderContent,10);

        String expectedText = String.format("%02d:%02d ile %02d:%02d arası", departureTime, 0, returnTime, 0);
        Assert.assertEquals(expectedText, testIsOk.getText());
    }

    public void flightListIsAvailable() {
        WebElement flightList = BrowserUtils.findElement(driver,flightListDiv,10);
        Assert.assertTrue( flightList.isDisplayed());

        List<WebElement> flight = BrowserUtils.findElements(driver,flights,10);
        Assert.assertFalse("Uçuş listesi boş!", flight.isEmpty());
    }

    public void routeIsCorrect (String fromCity,String toCity){
        WebElement element = BrowserUtils.findElement(driver,By.xpath("//div[@class='form-header active']//strong[@class='graphic-strong']"),20);
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





