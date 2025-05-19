package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.FilteredFlightPage;


public class Case2Steps {
    FilteredFlightPage filteredFlightPage = new FilteredFlightPage();

    @And("I filter flights by Turkish Airlines only")
    public void filterFlightsByTurkishAirlinesOnly() {
        filteredFlightPage.filterFlight();
    }

    @Then("all displayed flights should be Turkish Airlines flights")
    public void allDisplayedFlightsShouldBeTurkishAirlinesFlights() {
       filteredFlightPage.allFlightsIsCorrect();
    }

    @And("the flight prices should be sorted in ascending order")
    public void theFlightPricesShouldBeSortedInAscendingOrder() {
        filteredFlightPage.allFlightIsAscendingOrder();

    }

}
