package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.DriverManager;

import java.time.Duration;
import java.util.List;

public class SearchPage {
    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    public void moveToTargetMonth( String dateValue, String route){
        List<WebElement> dateElements = driver.findElements(By.xpath("//button[@data-testid='datepicker-active-day']"));
        String testIdValue = dateElements.get(0).getAttribute("title");

        String[] parts = testIdValue.split("-");
        String yearNow = parts[0];
        String monthNow = parts[1];

        String[] date = dateValue.split("-");
        String year = date[0];
        String month = date[1];

        int yearNowInt = Integer.parseInt(yearNow);
        int monthNowInt = Integer.parseInt(monthNow);
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);

        while(yearInt > yearNowInt || (yearInt == yearNowInt && monthInt > monthNowInt)) {
            WebElement rightClick = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//button[@data-testid='enuygun-homepage-flight-"+route+"-month-forward-button']"))));
            rightClick.click();


            List<WebElement> nowDates = driver.findElements(By.xpath("//div[@data-testid='enuygun-homepage-flight-"+route+"-datepicker-calendar-month']"));
            String testIdValues = nowDates.get(0).getAttribute("id");
            String[] parts2 = testIdValues.split("-");
            yearNow = parts2[2];
            monthNow = parts2[3];

            yearNowInt = Integer.parseInt(yearNow);
            monthNowInt = Integer.parseInt(monthNow);
        }


    }


}
