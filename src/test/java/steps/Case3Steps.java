package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Before;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.SearchPage;
import utilities.DriverManager;

import java.time.Duration;
import java.util.List;

public class Case3Steps {
    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
    Actions actions = new Actions(driver);
    SearchPage searchPage = new SearchPage();

    @Before
    public void setUp() {}


    @And("I navigate to the Hotel search section")
    public void iNavigateToTheHotelSearchSection() {
        WebElement hotelButton = driver.findElement(By.xpath("//a[@data-testid='header-nav-links-1']"));
        hotelButton.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("https://www.enuygun.com/otel/"));
    }

    @When("I type {string} in the Location field")
    public void iTypeInTheLocationField(String location) {
        WebElement locationField = driver.findElement(By.xpath("//input[@data-testid='endesign-hotel-autosuggestion-input']"));
        locationField.sendKeys(location);
    }

    @And("I select check-in date {string} and check-out date {string}")
    public void iSelectCheckInDateAndCheckOutDate(String checkin, String checkout) {

    }

    @And("I set the guests to {string} and {string}")
    public void iSetTheGuestsTo(String adult, String child) {
        WebElement guestButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-testid='hotel']")));
        guestButton.click();

        int adultInt =  Integer.parseInt(adult);
        int childInt = Integer.parseInt(child);

        WebElement adultNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid='hotel-adult-counter-count']")));
        int currentAdult = Integer.parseInt(adultNumber.getText());
        WebElement plusButtonAdult = driver.findElement(By.xpath("//button[@data-testid='hotel-adult-counter-plus-button']"));
        WebElement minusButtonAdult = driver.findElement(By.xpath("//button[@data-testid='hotel-adult-counter-minus-button']"));
        while (adultInt > currentAdult){
            plusButtonAdult.click();
            currentAdult++;
        }

        while (adultInt < currentAdult) {
            minusButtonAdult.click();
            currentAdult--;
        }
    }

    @And("I click the {string} button")
    public void iClickTheButton(String buttonText) {
        // TODO: Click the "Find Hotel" button
    }

    @Then("the hotel results page should be displayed")
    public void theHotelResultsPageShouldBeDisplayed() {
        // TODO: Verify navigation to hotel results page
    }

    @And("at least one hotel card should be listed")
    public void atLeastOneHotelCardShouldBeListed() {
        // TODO: Assert that hotel result cards are visible on the page
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
