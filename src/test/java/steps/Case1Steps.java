package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.SearchPage;
import utilities.BrowserUtils;



public class Case1Steps {
    SearchPage searchPage = new SearchPage();
    HomePage homePage = new HomePage();
    BrowserUtils browserUtils = new BrowserUtils();

    @When("I search for a round-trip flight from {string} to {string}")
    public void iSearchForARoundTripFlightFromToCity(String fromCity, String toCity) {

        homePage.enterFromCity(fromCity);

        homePage.enterToCity(toCity);

    }

    @And("I select departure date as {string} and return date as {string}")
    public void iSelectDepartureDateAsReturnDateAs(String departureDate, String returnDate) {

        homePage.selectRoundTrip();

        homePage.clickDepartureDate();

        homePage.activeDayPickerIsAvailable();

        homePage.moveToTargetMonth(departureDate, "departureDate");

        homePage.departurePicker(departureDate);

        homePage.clickReturnDate();

        homePage.moveToTargetMonth(returnDate, "returnDate");

        homePage.returnPicker(returnDate);

        browserUtils.sleepFunc(2);

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



}
