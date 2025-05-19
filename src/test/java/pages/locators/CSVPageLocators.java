package pages.locators;

import org.openqa.selenium.By;

public class CSVPageLocators {
    public static final By FLY_CARDS = By.xpath("//div[@class='flight-summary-infos']");
    public static final By DEPARTURE_TIME = By.xpath("//div[@class='flight-departure-time']");
    public static final By RETURN_TIME = By.xpath("//div[@class='flight-arrival-time']");
    public static final By AIRLINE_NAME = By.xpath("//div[@class='summary-marketing-airlines ']");
    public static final By PRICES = By.xpath("//div[@class='summary-average-price']");
    public static final By CONNECTION_INFO = By.xpath("//div[contains(@class, 'summary-transit')]");
    public static final By DURATION_INFO = By.xpath("//span[@data-testid='departureFlightTime']");
}
