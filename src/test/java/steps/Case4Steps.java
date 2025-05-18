package steps;

import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FlightPage;
import utilities.DriverManager;
import utilities.FlightData;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Case4Steps {
    WebDriver driver;
    WebDriverWait wait;
    List<FlightData> flightDataList = new ArrayList<>();
    FlightPage flightPage;

    public Case4Steps() {
        this.driver = DriverManager.getDriver();  // Örneğin driver'ını bu şekilde alıyorsan
        this.flightPage = new FlightPage();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(45));
    }


    @And("I extract flight information times from each flight")
    public void extractFlightInformation() {
        List<WebElement> flyCards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='flight-summary-infos']")));
        System.out.println("Number of flights: " + flyCards.size());

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


            String price = (priceINT.replace(".", "").replace(" TL", ""));

            int stops = flightPage.parseConnectionInfo(connectionInfo);
            String stopsStr = String.valueOf(stops);


            flightDataList.add(new FlightData(depTime, retTime, airName, price, stopsStr, duration));

        }

    }

    @And("I save all extracted data into a CSV file named {string}")
    public void saveAllExtractedDataIntoCSVFileNamed(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
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
            System.out.println("CSV dosyası başarıyla kaydedildi: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("CSV dosyası kaydedilirken hata oluştu!");
        }
    }

}
