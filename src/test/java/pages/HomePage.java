package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.BrowserUtils;
import utilities.DriverManager;

import java.time.Duration;
import java.util.List;

import static utilities.DriverManager.driver;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Constructor
    public HomePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }



    private final By roundTripLabel = By.xpath("//label[@data-testid='search-round-trip-label']");
    private final By fromCityInput = By.xpath("//input[@data-testid='endesign-flight-origin-autosuggestion-input']");
    private final By toCityInput = By.xpath("//input[@data-testid='endesign-flight-destination-autosuggestion-input']");
    private final By secondToCityOption = By.xpath("//li[@data-testid='endesign-flight-destination-autosuggestion-option-item-0']");
    private final By firstToCityOption = By.xpath("//li[@data-testid='endesign-flight-origin-autosuggestion-option-item-0']");
    private final By departureDateButton = By.xpath("//div[@data-testid='enuygun-homepage-flight-departureDate-datepicker-popover-button']");
    private final By returnDateInput = By.xpath("//input[@data-testid='enuygun-homepage-flight-returnDate-datepicker-input']");
    private final By searchButton = By.xpath("//button[@data-testid='enuygun-homepage-flight-submitButton']");
    private final By oneWayCheckboxInput = By.xpath("//input[@data-testid='flight-oneWayCheckbox-input']");
    private final By oneWayCheckboxLabel = By.xpath("//label[@data-testid='flight-oneWayCheckbox-label']");


    public void selectRoundTrip(){
        BrowserUtils.waitForClickability(driver, roundTripLabel, 10).click();
    }

    public void enterFromCity(String fromCity){
        WebElement fromInput = BrowserUtils.waitForClickability(driver, fromCityInput, 10);
        fromInput.clear();
        fromInput.sendKeys(fromCity);
        BrowserUtils.waitForClickability(driver, firstToCityOption, 10);
        fromInput.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void enterToCity(String toCity){
        WebElement toInput = BrowserUtils.waitForClickability(driver, toCityInput, 10);
        toInput.clear();
        toInput.sendKeys(toCity);
        BrowserUtils.waitForClickability(driver, secondToCityOption, 10);
        toInput.sendKeys(Keys.ENTER);
    }


    public void activeDayPickerIsAvailable(){
        BrowserUtils.waitForPresence(driver,By.xpath("//button[@data-testid='datepicker-active-day']"),10);

    }

    public void clickDepartureDate(){
        BrowserUtils.waitForClickability(driver, departureDateButton, 10).click();
    }

    public void clickReturnDate() {
        BrowserUtils.waitForClickability(driver, returnDateInput, 10).click();
    }

    public void clickSearchButton() {
        BrowserUtils.waitForClickability(driver, searchButton, 10).click();
    }
    public void departurePicker(String departureDate){
        final By departurePicker = By.xpath("//button[@title='" + departureDate + "']");
        BrowserUtils.waitForClickability(driver, departureDateButton, 10).click();
    }

    public void returnPicker(String returnDate){
        final By returnPicker = By.xpath("//button[@title='" + returnDate + "']");
        BrowserUtils.waitForClickability(driver, returnPicker, 10).click();
    }

    public void clickOneWayCheckbox() {
        List<WebElement> checkBoxes = driver.findElements(oneWayCheckboxInput);

        System.out.println("checkBoxInputs: " + checkBoxes.get(1).getText());
        if (!checkBoxes.isEmpty()) {
            WebElement checkBoxInput = checkBoxes.get(1);
            boolean isSelected = checkBoxInput.isSelected();
            System.out.println("isSelected: " + isSelected);

            if (isSelected) {
                List<WebElement> label = driver.findElements(oneWayCheckboxLabel);
                label.get(1).click();
            }
        }
    }


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
            WebElement rightClick = BrowserUtils.waitForClickability(driver,By.xpath("//button[@data-testid='enuygun-homepage-flight-"+route+"-month-forward-button']"),25);
            rightClick.click();


            List<WebElement> nowDates = BrowserUtils.findElements(driver,By.xpath("//div[@data-testid='enuygun-homepage-flight-"+route+"-datepicker-calendar-month']"),20);
            String testIdValues = nowDates.get(0).getAttribute("id");
            String[] parts2 = testIdValues.split("-");
            yearNow = parts2[2];
            monthNow = parts2[3];

            yearNowInt = Integer.parseInt(yearNow);
            monthNowInt = Integer.parseInt(monthNow);
        }


    }

}
