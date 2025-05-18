package steps;

import io.cucumber.java.en.And;

public class Case4Steps {
    @And("I extract departure and arrival times from each flight")
    public void extractDepartureAndArrivalTimesFromEachFlight() {
        // TODO: Extract departure and arrival times from flight elements
    }

    @And("I extract airline names from each flight")
    public void extractAirlineNamesFromEachFlight() {
        // TODO: Extract airline names from flight elements
    }

    @And("I extract prices from each flight")
    public void extractPricesFromEachFlight() {
        // TODO: Extract flight prices from the UI
    }

    @And("I extract connection information from each flight")
    public void extractConnectionInformationFromEachFlight() {
        // TODO: Extract connection (direct/layover) details
    }

    @And("I extract flight durations from each flight")
    public void extractFlightDurationsFromEachFlight() {
        // TODO: Extract total flight durations
    }

    @And("I save all extracted data into a CSV file named {string}")
    public void saveAllExtractedDataIntoCSVFileNamed(String fileName) {
        // TODO: Combine all extracted data and write to CSV using fileName
    }

}
