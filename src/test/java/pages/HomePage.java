package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import pages.locators.HomePageLocators;
import utilities.BrowserUtils;
import utilities.DriverManager;

import java.util.List;

public class HomePage {
    private final WebDriver driver;
    public static final Logger logger = org.slf4j.LoggerFactory.getLogger(HomePage.class);

    // Constructor
    public HomePage() {
        this.driver = DriverManager.getDriver();
    }


    public void selectRoundTrip(){
        BrowserUtils.waitForClickability(HomePageLocators.ROUND_TRIP_LABEL).click();
    }

    public void enterFromCity(String fromCity){
        WebElement fromInput = BrowserUtils.waitForClickability(HomePageLocators.FROM_CITY_INPUT);
        fromInput.clear();
        fromInput.sendKeys(fromCity);
        BrowserUtils.waitForClickability(HomePageLocators.FIRST_TO_CITY_OPTION);
        fromInput.sendKeys(Keys.ENTER);

    }

    public void enterToCity(String toCity){
        WebElement toInput = BrowserUtils.waitForClickability(HomePageLocators.TO_CITY_INPUT);
        toInput.clear();
        toInput.sendKeys(toCity);
        BrowserUtils.waitForClickability(HomePageLocators.SECOND_TO_CITY_OPTION);
        toInput.sendKeys(Keys.ENTER);

    }


    public void activeDayPickerIsAvailable(){
        BrowserUtils.waitForPresence(By.xpath("//button[@data-testid='datepicker-active-day']"));
    }

    public void clickDepartureDate(){
        BrowserUtils.waitForClickability(HomePageLocators.DEPARTURE_DATE_BUTTON).click();
    }

    public void clickReturnDate() {
        BrowserUtils.waitForClickability(HomePageLocators.RETURN_DATE_INPUT).click();
    }

    public void clickSearchButton() {
        BrowserUtils.waitForClickability(HomePageLocators.SEARCH_BUTTON).click();

    }
    public void departurePicker(String departureDate){
        // final By departurePicker = By.xpath("//button[@title='" + departureDate + "']");
        BrowserUtils.waitForClickability(HomePageLocators.DEPARTURE_DATE_BUTTON).click();

       }

    public void returnPicker(String returnDate){
        final By returnPicker = By.xpath("//button[@title='" + returnDate + "']");
        BrowserUtils.waitForClickability(returnPicker).click();
    }

    public void clickOneWayCheckbox() {
        List<WebElement> checkBoxes = driver.findElements(HomePageLocators.ONE_WAY_CHECKBOX_INPUT);

        System.out.println("checkBoxInputs: " + checkBoxes.get(1).getText());
        if (!checkBoxes.isEmpty()) {
            WebElement checkBoxInput = checkBoxes.get(1);
            boolean isSelected = checkBoxInput.isSelected();
            logger.info("isSelected: " + isSelected);

            if (isSelected) {
                List<WebElement> label = BrowserUtils.findElements(HomePageLocators.ONE_WAY_CHECKBOX_LABEL);
                label.get(1).click();
            }
        }
    }


    public void moveToTargetMonth( String dateValue, String route){
        List<WebElement> dateElements = BrowserUtils.findElements(By.xpath("//button[@data-testid='datepicker-active-day']"));
        String testIdValue = dateElements.get(0).getDomAttribute("title");

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
            WebElement rightClick = BrowserUtils.waitForClickability(By.xpath("//button[@data-testid='enuygun-homepage-flight-"+route+"-month-forward-button']"));
            rightClick.click();


            List<WebElement> nowDates = BrowserUtils.findElements(By.xpath("//div[@data-testid='enuygun-homepage-flight-"+route+"-datepicker-calendar-month']"));
            String testIdValues = nowDates.get(0).getDomAttribute("id");
            String[] parts2 = testIdValues.split("-");
            yearNow = parts2[2];
            monthNow = parts2[3];

            yearNowInt = Integer.parseInt(yearNow);
            monthNowInt = Integer.parseInt(monthNow);
        }


    }

}
