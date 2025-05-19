package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.locators.FilteredFlightLocators;
import utilities.BrowserUtils;
import utilities.DriverManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilteredFlightPage {
    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


    public void filterFlight(){
        BrowserUtils.findElement(FilteredFlightLocators.FILTER_LOADING);
        BrowserUtils.waitForElementToDisappear(FilteredFlightLocators.FILTER_LOADING_EFFECT);

        BrowserUtils.waitForClickability(FilteredFlightLocators.FILTER_AIRLINE_HEADER)
                .click();


        List<WebElement> showMoreButtons = BrowserUtils.findElements(FilteredFlightLocators.FILTER_SHOW_MORE);
        if (!showMoreButtons.isEmpty()) {
            WebElement showMoreButton = showMoreButtons.get(0);
            if (showMoreButton.isDisplayed()) {
                showMoreButton.click();
            }
        }


        BrowserUtils.findElement(FilteredFlightLocators.AIRLINE_TK_LABEL)
                .click();
    }

    public void allFlightsIsCorrect(){
        BrowserUtils.waitForElementToDisappear(FilteredFlightLocators.FILTER_LOADING_EFFECT);
        List<WebElement> airlines = BrowserUtils.waitForPresenceAllElement(FilteredFlightLocators.SUMMARY_MARKETING_AIRLINES);
        boolean allTurkishAirlines = true;

        System.out.println(airlines.get(0).getText());

        for (WebElement airline : airlines) {
            String airlineName = airline.getText().trim();
            if (!airlineName.equals("Türk Hava Yolları")) {
                allTurkishAirlines = false;
                break;
            }
        }
        Assert.assertTrue(allTurkishAirlines);
    }

    public void allFlightIsAscendingOrder(){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(FilteredFlightLocators.FILTER_LOADING));
        WebElement ascendingButton = BrowserUtils.findElement(FilteredFlightLocators.SORT_BUTTONS_0);
        ascendingButton.click();

        List<WebElement> priceElements = BrowserUtils.findElements(FilteredFlightLocators.FLIGHT_INFO_PRICE);
        List<Double> prices = new ArrayList<>();
        for (WebElement priceEl : priceElements) {
            String priceText = priceEl.getDomAttribute("data-price"); // örnek: "2076.99"
            if (priceText != null && !priceText.isEmpty()) {
                prices.add(Double.parseDouble(priceText));
            }
        }

        List<Double> sortedPrices = new ArrayList<>(prices);

        Collections.sort(sortedPrices);

        Assert.assertEquals(prices, sortedPrices);


    }


}
