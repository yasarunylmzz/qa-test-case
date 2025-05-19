package pages;

import analysis.FlightAnalyzer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    public void selectAllFlights() {
        List<WebElement> flyCards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='flight-summary-infos']")));
        List<WebElement> departureTime = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='flight-departure-time']")));
        List<WebElement> returnTime = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='flight-arrival-time']")));
        List<WebElement> airlineName = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='summary-marketing-airlines ']")));
        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='summary-average-price']")));
        List<WebElement> connectionINFO = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'summary-transit')]")));
        List<WebElement> durationInfo = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@data-testid='departureFlightTime']")));

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


    public void saveAllFlightsInCSV(String toCity, String fromCity){
        String toLowerCase =  toCity.toLowerCase();
        String fromCityCase = fromCity.toLowerCase();
        String csvFileName = "flights_" + fromCityCase + "_" + toLowerCase + ".csv";
        System.out.println("CSV file name: " + csvFileName);
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
            System.out.println("CSV dosyası başarıyla kaydedildi: " + csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("CSV dosyası kaydedilirken hata oluştu!");
        }
    }


    public void analyzeFunctions(String fromCity, String toCity) {
        String lowerCaseFromCity = fromCity.toLowerCase();
        String lowerCaseToCity = toCity.toLowerCase();

        FlightAnalyzer.flightGraphs(lowerCaseFromCity, lowerCaseToCity);
        FlightAnalyzer.paretoOptimal(lowerCaseFromCity, lowerCaseToCity);
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
