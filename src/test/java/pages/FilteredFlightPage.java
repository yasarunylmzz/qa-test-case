package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
        BrowserUtils.findElement(driver,By.xpath("//div[@class='filter-loading']"),10);
        BrowserUtils.waitForElementToDisappear(driver,By.className("filter-loading-effect"), 20);

        BrowserUtils.waitForClickability(driver,By.xpath("//div[@class='filter-card card']//div[@class='ctx-filter-airline card-header']"),20)
                .click();


        List<WebElement> showMoreButtons = driver.findElements(By.xpath("//div[@class='filter-show-more']"));
        if (!showMoreButtons.isEmpty()) {
            WebElement showMoreButton = showMoreButtons.get(0);
            if (showMoreButton.isDisplayed()) {
                showMoreButton.click();
            }
        }


        driver.findElement(By.xpath("//label[@for='TKairlines']"))
                .click();
    }

    public void allFlightsIsCorrect(){
        BrowserUtils.waitForElementToDisappear(driver,By.xpath("//div[@class='filter-loading']"),10);
        List<WebElement> airlines = BrowserUtils.waitForPresenceAllElement(driver,By.xpath("//div[@class='summary-marketing-airlines ']"),20);
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
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='filter-loading']")));
        WebElement ascendingButton = driver.findElement(By.xpath("//div[@data-testid='sortButtons0']"));
        ascendingButton.click();

        List<WebElement> priceElements = driver.findElements(By.xpath("//div[@data-testid='flightInfoPrice']"));
        List<Double> prices = new ArrayList<>();
        for (WebElement priceEl : priceElements) {
            String priceText = priceEl.getAttribute("data-price"); // örnek: "2076.99"
            if (priceText != null && !priceText.isEmpty()) {
                prices.add(Double.parseDouble(priceText));
            }
        }

        List<Double> sortedPrices = new ArrayList<>(prices);

        Collections.sort(sortedPrices);

        Assert.assertEquals(prices, sortedPrices);


    }


}
