package pages.locators;

import org.openqa.selenium.By;

public class SearchPageLocators {


    public final static By DEPERTURE_TIME_FILTER_BUTTON = By.xpath("//div[@class='ctx-filter-departure-return-time card-header']");
    public final static By  SLIDER_STEP = By.className("rc-slider-step");
    public final static By  SLIDER_LEFT_HANDLE = By.cssSelector(".rc-slider-handle.rc-slider-handle-1");
    public final static By  SLIDER_RIGHT_HANDLE = By.cssSelector(".rc-slider-handle.rc-slider-handle-2");
    public final static By  FILTER_LOADING = By.xpath("//div[@class='filter-loading']");
    public final static By  FILTER_SLIDER_CONTENT = By.xpath("//div[@class='filter-slider-content']");
    public final static By  FLIGHT_LIST_DIV = By.xpath("//div[@class='flight-list flight-list-departure    domesticList']");
    public final static By  FLIGHTS = By.xpath("//div[@data-flight-index]");

    
}
