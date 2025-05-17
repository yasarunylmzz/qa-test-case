package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Before;
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
        // TODO: Click on the Hotel tab or navigate to hotel search
    }

    @When("I type {string} in the Location field")
    public void iTypeInTheLocationField(String location) {
        // TODO: Enter the location into the input field
    }

    @And("I select check-in date {string} and check-out date {string}")
    public void iSelectCheckInDateAndCheckOutDate(String checkin, String checkout) {
        // TODO: Select check-in and check-out dates from the calendar
    }

    @And("I set the guests to {string}")
    public void iSetTheGuestsTo(String guests) {
        // TODO: Set the number of guests (adults/children)
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
