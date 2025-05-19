package pages.locators;

import org.openqa.selenium.By;

public class SearchPageLocators {


    public final static By departureTimeFilterButton = By.xpath("//div[@class='ctx-filter-departure-return-time card-header']");
    public final static By sliderStep = By.className("rc-slider-step");
    public final static By sliderLeftHandle = By.cssSelector(".rc-slider-handle.rc-slider-handle-1");
    public final static By sliderRightHandle = By.cssSelector(".rc-slider-handle.rc-slider-handle-2");
    public final static By filterLoading = By.xpath("//div[@class='filter-loading']");
    public final static By filterSliderContent = By.xpath("//div[@class='filter-slider-content']");
    public final static By flightListDiv = By.xpath("//div[@class='flight-list flight-list-departure    domesticList']");
    public final static By flights = By.xpath("//div[@data-flight-index]");

    
}
