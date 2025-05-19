package pages.locators;

import org.openqa.selenium.By;

public class FilteredFlightLocators {
    public static final By FILTER_LOADING = By.xpath("//div[@class='filter-loading']");
    public static final By FILTER_LOADING_EFFECT = By.className("filter-loading-effect");
    public static final By FILTER_AIRLINE_HEADER = By.xpath("//div[@class='filter-card card']//div[@class='ctx-filter-airline card-header']");
    public static final By FILTER_SHOW_MORE = By.xpath("//div[@class='filter-show-more']");
    public static final By AIRLINE_TK_LABEL = By.xpath("//label[@for='TKairlines']");
    public static final By SUMMARY_MARKETING_AIRLINES = By.xpath("//div[@class='summary-marketing-airlines ']");
    public static final By SORT_BUTTONS_0 = By.xpath("//div[@data-testid='sortButtons0']");
    public static final By FLIGHT_INFO_PRICE = By.xpath("//div[@data-testid='flightInfoPrice']");

}
