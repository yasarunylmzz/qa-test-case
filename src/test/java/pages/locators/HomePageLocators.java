package pages.locators;

import org.openqa.selenium.By;

public class HomePageLocators {
    public static final By ROUND_TRIP_LABEL = By.xpath("//label[@data-testid='search-round-trip-label']");
    public static final By FROM_CITY_INPUT = By.xpath("//input[@data-testid='endesign-flight-origin-autosuggestion-input']");
    public static final By TO_CITY_INPUT = By.xpath("//input[@data-testid='endesign-flight-destination-autosuggestion-input']");
    public static final By SECOND_TO_CITY_OPTION = By.xpath("//li[@data-testid='endesign-flight-destination-autosuggestion-option-item-0']");
    public static final By FIRST_TO_CITY_OPTION = By.xpath("//li[@data-testid='endesign-flight-origin-autosuggestion-option-item-0']");
    public static final By DEPARTURE_DATE_BUTTON = By.xpath("//div[@data-testid='enuygun-homepage-flight-departureDate-datepicker-popover-button']");
    public static final By RETURN_DATE_INPUT = By.xpath("//input[@data-testid='enuygun-homepage-flight-returnDate-datepicker-input']");
    public static final By SEARCH_BUTTON = By.xpath("//button[@data-testid='enuygun-homepage-flight-submitButton']");
    public static final By ONE_WAY_CHECKBOX_INPUT = By.xpath("//input[@data-testid='flight-oneWayCheckbox-input']");
    public static final By ONE_WAY_CHECKBOX_LABEL = By.xpath("//label[@data-testid='flight-oneWayCheckbox-label']");
    public static final By DATEPICKER_ACTIVE_DAY = By.xpath("//button[@data-testid='datepicker-active-day']");
    public static final By ORIGIN_ERROR = By.xpath("//div[@data-testid='flight-origin-error-message']");
    public static final By DESTINATION_ERROR = By.xpath("//div[@data-testid='flight-destination-error-message']");
}
