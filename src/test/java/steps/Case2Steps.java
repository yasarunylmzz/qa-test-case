package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.DriverManager;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Case2Steps {
    WebDriver driver = BaseTest.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


    @And("I filter flights by Turkish Airlines only")
    public void filterFlightsByTurkishAirlinesOnly() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement filterLoading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='filter-loading']")));
        WebElement filterFlightsElement = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='filter-card card']//div[@class='ctx-filter-airline card-header']"))
        );
        filterFlightsElement.click();

        WebElement selectTKairlines = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//label[@for='TKairlines']"))));
        selectTKairlines.click();
    }

    @Then("all displayed flights should be Turkish Airlines flights")
    public void allDisplayedFlightsShouldBeTurkishAirlinesFlights() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='filter-loading']")));
        List<WebElement> airlines = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='summary-marketing-airlines ']")));
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

    @And("the flight prices should be sorted in ascending order")
    public void theFlightPricesShouldBeSortedInAscendingOrder() {
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
        BaseTest.quitDriver();
    }

}
