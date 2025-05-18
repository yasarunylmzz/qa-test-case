package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;

import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


import pages.HomePage;
import pages.SearchPage;
import utilities.ConfigurationReader;
import utilities.DriverManager;

public class Case1Steps {
    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
    SearchPage searchPage = new SearchPage();
    HomePage homePage = new HomePage();

    @Before
    @Given("I am on the Enuygun homepage")
    public void setUp() {
        String browser = ConfigurationReader.getProperty("browser");
        System.setProperty("browser", browser);

        String url = ConfigurationReader.getProperty("url");
        driver.manage().window().maximize();
        driver.get(url);

    }


    @When("I search for a round-trip flight from {string} to {string}")
    public void iSearchForARoundTripFlightFromToCity(String fromCity, String toCity) {

        homePage.selectRoundTrip();

        homePage.enterFromCity(fromCity);

        homePage.enterToCity(toCity);

    }

    @And("I select departure date as {string} and return date as {string}")
    public void iSelectDepartureDateAsReturnDateAs(String departureDate, String returnDate) {

        homePage.clickDepartureDate();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@data-testid='datepicker-active-day']")));

        homePage.moveToTargetMonth(departureDate, "departureDate");

        homePage.departurePicker(departureDate);

        homePage.clickReturnDate();

        homePage.moveToTargetMonth(returnDate, "returnDate");

        homePage.returnPicker(returnDate);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        homePage.clickOneWayCheckbox();

        homePage.clickSearchButton();

    }

    @And("I apply a departure time filter between {int} and {int}")
    public void iApplyADepartureTimeFilterBetweenAnd(int departureTime, int returnTime) {
        searchPage.openDepartureTimeFilter();

        searchPage.setDepartureTimeRange(departureTime, returnTime);

    }

    @Then("all displayed flights should have departure times between {int} and {int}")
    public void allDisplayedFlightsShouldHaveDepartureTimesBetweenAnd(int departureTime, int returnTime) {
        searchPage.isCorrectTime(departureTime,returnTime);

    }

    @And("the flight list should be properly displayed")
    public void theFlightListShouldBeProperlyDisplayed() {
        searchPage.flightListIsAvailable();
    }

    @And("the search results should match the selected route from {string} to {string}")
    public void theSearchResultsShouldMatchTheSelectedRouteFromFromCityToTo(String fromCity,String toCity) {
        searchPage.routeIsCorrect(fromCity,toCity);
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }


}
