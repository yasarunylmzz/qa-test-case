package pages;

import analysis.FlightAnalyzer;
import pages.locators.CSVPageLocators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import utilities.DriverManager;
import utilities.FlightData;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVPage {

    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
    List<FlightData> flightDataList = new ArrayList<>();
    public static final Logger logger = org.slf4j.LoggerFactory.getLogger(HomePage.class);


    /**
     * Selects all flight cards and extracts relevant information.
     */
    public void selectAllFlights() {
        List<WebElement> flyCards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CSVPageLocators.FLY_CARDS));
        List<WebElement> departureTime = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CSVPageLocators.DEPARTURE_TIME));
        List<WebElement> returnTime = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CSVPageLocators.RETURN_TIME));
        List<WebElement> airlineName = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CSVPageLocators.AIRLINE_NAME));
        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CSVPageLocators.PRICES));
        List<WebElement> connectionINFO = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CSVPageLocators.CONNECTION_INFO));
        List<WebElement> durationInfo = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CSVPageLocators.DURATION_INFO));

        for (int j = 0; j < flyCards.size(); j++) {
            String depTime = departureTime.get(j).getText();
            String retTime = returnTime.get(j).getText();
            String airName = airlineName.get(j).getText();
            String priceINT = prices.get(j).getText();
            String connectionInfo = connectionINFO.get(j).getText();
            String duration = durationInfo.get(j).getText();

            int durationInt = convertDurationToMinutes(duration);
            String durationString = String.valueOf(durationInt);

            String price = (priceINT.replace(".", "").replace(" TL", ""));

            int stops = parseConnectionInfo(connectionInfo);
            String stopsStr = String.valueOf(stops);


            flightDataList.add(new FlightData(depTime, retTime, airName, price, stopsStr, durationString));

        }
    }

    /**
     * Saves all flight data to a CSV file.
     *
     * @param toCity   The destination city.
     * @param fromCity The departure city.
     */
    public void saveAllFlightsInCSV(String toCity, String fromCity){
        String toLowerCase =  toCity.toLowerCase();
        String fromCityCase = fromCity.toLowerCase();
        String csvFileName = "flights_" + toLowerCase + "_" + fromCityCase + ".csv";
        logger.info("CSV file name: " + csvFileName);
        try (FileWriter writer = new FileWriter(csvFileName)) {
            writer.append("DepartureTime,ArrivalTime,AirlineName,Price,ConnectionInfo,Duration\n");

            for (FlightData fd : flightDataList) {
                writer.append(fd.getDepartureTime()).append(",")
                        .append(fd.getArrivalTime()).append(",")
                        .append(fd.getAirlineName()).append(",")
                        .append(fd.getPrice()).append(",")
                        .append(fd.getConnectionInfo()).append(",")
                        .append(fd.getDuration()).append("\n");
            }

            writer.flush();
            logger.info("CSV file saved successfully: " + csvFileName);
        } catch (IOException e) {
            logger.error("Error occurred while saving CSV file: " + e.getMessage());
        }
    }

    /**
     * Analyzes the flight data and generates graphs and Pareto optimality.
     *
     * @param fromCity The departure city.
     * @param toCity   The destination city.
     */
    public void analyzeFunctions(String fromCity, String toCity) {
        String lowerCaseFromCity = fromCity.toLowerCase();
        String lowerCaseToCity = toCity.toLowerCase();

        FlightAnalyzer.flightGraphs(lowerCaseFromCity, lowerCaseToCity);
        FlightAnalyzer.paretoOptimalPenalty(lowerCaseFromCity, lowerCaseToCity);
    }

    public int parseConnectionInfo(String connectionInfo) {
        if (connectionInfo == null) {
            return -1;
        }

        connectionInfo = connectionInfo.toLowerCase().trim();

        if (connectionInfo.contains("direkt")) {
            return 0;
        } else if (connectionInfo.contains("1 aktarma")) {
            return 1;
        } else if (connectionInfo.contains("2 aktarma")) {
            return 2;
        } else {

            return -1;
        }
    }

    /**
     * Converts a duration string in the format "Xg Ysa Zdk" to total minutes.
     * Example: "2g 3sa 15dk" -> 2 * 24 * 60 + 3 * 60 + 15 = total minutes
     *
     * @param duration The duration string to convert.
     * @return The total duration in minutes.
     */
    public int convertDurationToMinutes(String duration) {
        int totalMinutes = 0;

        Pattern pattern = Pattern.compile("(?:(\\d+)g)?\\s*(?:(\\d+)sa)?\\s*(?:(\\d+)dk)?");
        Matcher matcher = pattern.matcher(duration.trim());

        if (matcher.matches()) {
            String days = matcher.group(1); // gün
            String hours = matcher.group(2); // saat
            String minutes = matcher.group(3); // dakika

            if (days != null) {
                totalMinutes += Integer.parseInt(days) * 24 * 60;
            }
            if (hours != null) {
                totalMinutes += Integer.parseInt(hours) * 60;
            }
            if (minutes != null) {
                totalMinutes += Integer.parseInt(minutes);
            }
        }

        return totalMinutes;
    }

}
