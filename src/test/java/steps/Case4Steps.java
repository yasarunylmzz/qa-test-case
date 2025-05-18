package steps;

import io.cucumber.java.en.And;
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

public class Case4Steps {
    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
    List<FlightData> flightDataList = new ArrayList<>();

    List<WebElement> flyCard = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='flight-summary-infos']")));

    @And("I extract flight information times from each flight")
    public void extractFlightInformation() {
        List<WebElement> flyCards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='flight-summary-infos']")));

        for (WebElement card : flyCards) {
            String depTime = card.findElement(By.xpath(".//div[@data-testid='departureTime']")).getText();
            String arrTime = card.findElement(By.xpath(".//div[@data-testid='arrivalTime']")).getText();
            String airlineName = card.findElement(By.xpath(".//div[@class='summary-marketing-airlines ']")).getText();
            String price = card.findElement(By.xpath(".//span[@class='money-int']")).getText();
            String connectionInfo = card.findElement(By.xpath(".//div[@data-testid='transferStateDirect']")).getText();
            String duration = card.findElement(By.xpath(".//span[@data-testid='departureFlightTime']")).getText();

            FlightData fd = new FlightData(depTime, arrTime, airlineName, price, connectionInfo, duration);
            flightDataList.add(fd);
        }

        System.out.println("Toplam uçuş sayısı: " + flightDataList.size());
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
