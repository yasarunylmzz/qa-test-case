package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CSVPage;
import pages.HomePage;
import utilities.DriverManager;
import utilities.FlightData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import utilities.ScreenShotUtil;


public class Case4Steps {

    List<FlightData> flightDataList = new ArrayList<>();
    HomePage homePage= new HomePage();
    ScreenShotUtil screenShotUtil = new ScreenShotUtil();
    CSVPage csvPage =  new CSVPage();

    // this section is different from case1 where there is no return time
    @And("I select departure date as {string}")
    public void iSelectDepartureDateAsReturnDateAs(String departureDate) {

        homePage.clickDepartureDate();

        homePage.activeDayPickerIsAvailable();

        homePage.moveToTargetMonth(departureDate, "departureDate");

        homePage.departurePicker(departureDate);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        homePage.clickOneWayCheckbox();

        homePage.clickSearchButton();

    }


    @And("I extract flight information times from each flight")
    public void extractFlightInformation() {
        csvPage.selectAllFlights();
    }

    @And("I save all extracted data into a CSV file named {string} and {string}")
    public void saveAllExtractedDataIntoCSVFileNamed(String fromCity,String toCity) {
        csvPage.saveAllFlightsInCSV(fromCity, toCity);
    }

    @Then("I analyze the search results {string} and {string}")
    public void analyzeSearchResults(String fromCity, String toCity) {
        csvPage.analyzeFunctions(fromCity, toCity);
    }



}
