package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.locators.HotelPageLocators;
import utilities.BrowserUtils;
import utilities.DriverManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EndToEndHotelBookingSteps {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public EndToEndHotelBookingSteps() {
        this.driver = DriverManager.getDriver();
        this.wait = DriverManager.getWait();
    }

    public void hotelSearch() {
        BrowserUtils.findElement(HotelPageLocators.HOTEL_NAV_LINK).click();

        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        Assert.assertTrue(currentUrl.contains("https://www.enuygun.com/otel/"));
    }

    public void hotelSelect(String location){
        BrowserUtils.findElement(HotelPageLocators.HOTEL_AUTOSUGGEST_INPUT).sendKeys(location);

        BrowserUtils.findElement(HotelPageLocators.HOTEL_AUTOSUGGEST_OPTION_ITEM_0).click();
    }

    public void checkInCheckOut(String checkin, String checkout) {
        BrowserUtils.findElement(HotelPageLocators.HOTEL_DATEPICKER_POPOVER_BUTTON).click();
        navigateToCheckInMonth(checkin);
        BrowserUtils.findElement(By.xpath("//button[@title='"+checkin+"']")).click();
        BrowserUtils.findElement(By.xpath("//button[@title='"+checkout+"']")).click();
        BrowserUtils.findElement(HotelPageLocators.HOTEL_DATEPICKER_POPOVER_BUTTON).click();


        List<WebElement> selectedDates = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(HotelPageLocators.DATEPICKER_ACTIVE_DAY));
        List<String> dates = selectedDates.stream()
                .map(e -> e.getAttribute("title"))
                .collect(Collectors.toList());

        BrowserUtils.findElement(HotelPageLocators.HOTEL_DATEPICKER_POPOVER_BUTTON).click();


        Assert.assertTrue(dates.contains(checkin));
        Assert.assertTrue(dates.contains(checkout));
    }

    public void setGuestAndSetChild(String adult, String child, String childsAge){
        // adult guest button
        BrowserUtils.waitForClickability(By.xpath("//div[@data-testid='hotel']")).click();


        String childAndAdultText = adult + " Yetişkin, " + child + " Çocuk," + " 1 Oda";
        String justAdultText = adult + "Yetişkin," + "1 Oda";
        int childInt = Integer.parseInt(child);

        //adult
        adjustGuestCount(
                wait,
                Integer.parseInt(adult),
                HotelPageLocators.HOTEL_ADULT_COUNTER_COUNT,
                HotelPageLocators.HOTEL_ADULT_COUNTER_PLUS_BUTTON,
                HotelPageLocators.HOTEL_ADULT_COUNTER_MINUS_BUTTON
                );

        //child
        adjustGuestCount(
                wait,
                Integer.parseInt(child),
                HotelPageLocators.HOTEL_CHILD_COUNTER_COUNT,
                HotelPageLocators.HOTEL_CHILD_COUNTER_PLUS_BUTTON,
                HotelPageLocators.HOTEL_CHILD_COUNTER_MINUS_BUTTON
        );

        if (childInt > 0 && !childsAge.isEmpty()) {
            String[] ageList = childsAge.split(",");

            for (int i = 0; i < childInt; i++) {
                String age = ageList.length > i ? ageList[i].trim() : "10"; // Default 10 if missing
                By dropdownOpener = By.xpath("//select[@data-testid='hotel-child-select-0-"+i+"']");
                WebElement selectAge = driver.findElement(dropdownOpener);
                Select select = new Select(selectAge);
                select.selectByValue(age);


                System.out.println("Selected age for child " + i + ": " + age);

            }
            System.out.println("child ages: " + childsAge);
        }

        String isValue = BrowserUtils.findElement(HotelPageLocators.HOTEL_POPOVER_BUTTON).getAttribute("value");

        if(child.isEmpty()){
            Assert.assertEquals(justAdultText,isValue);
        }
        Assert.assertEquals(childAndAdultText,isValue);

    }

    public void SubmitButton(){
        BrowserUtils.findElement(HotelPageLocators.HOTEL_SUBMIT_SEARCH_BUTTON).click();

        WebElement cityIsCorrect = wait.until(ExpectedConditions.visibilityOfElementLocated(HotelPageLocators.HOTEL_RESULT_TITLE));

        Assert.assertTrue(cityIsCorrect.isDisplayed());
    }

    public void hotelPricesAscendingOrder(){
        BrowserUtils.findElement(By.xpath("//button[@data-testid='sort-fiyat-artan-button']")).click();

        List<WebElement> hotels = BrowserUtils.findElements(HotelPageLocators.RESULT_AVAILABLE_HOTEL);
        WebElement hotelNameElement = hotels.get(0).findElement(HotelPageLocators.RESULT_TITLE);
        String hotelName = hotelNameElement.getText();
        hotels.get(0).click();

        // new tab and changes tab
        String originalWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        WebElement hotelNameDOM = wait.until(ExpectedConditions.visibilityOfElementLocated(HotelPageLocators.HOTEL_TITLE));

        Assert.assertEquals(hotelName, hotelNameDOM.getText());
    }

    public void verifyHotelAndBookRoomButton() {
        //offer radio buttons
        BrowserUtils.findElements(HotelPageLocators.OFFER_ITEM_CONTAINER).get(0).click();;

        //book a room buttons
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(HotelPageLocators.OFFER_SELECT_ROOM_BUTTON)).get(0).click();
    }

    public void guessDetails(String firstName, String lastName, String email, String phone, String gender) {
        // email fields
        BrowserUtils.findElement(HotelPageLocators.CONTACT_EMAIL).sendKeys(email);

        // phone fields
        BrowserUtils.findElement(HotelPageLocators.CONTACT_PHONE).sendKeys(phone);

        // firstname fields
        List<WebElement> names = BrowserUtils.findElements(HotelPageLocators.ROOM_FIRST_NAME);

        for (WebElement element : names) {
            element.sendKeys(firstName);
        }

        // lastname fields
        List<WebElement> lastNames = BrowserUtils.findElements(HotelPageLocators.ROOM_LAST_NAME);
        for (WebElement element : lastNames) {
            element.sendKeys(lastName);
        }

        // gender fields
        BrowserUtils.findElement(By.xpath("//label[@data-testid='"+gender+"-label']")).click();

        // click button
        BrowserUtils.findElement(HotelPageLocators.RESERVATION_FORM_SUBMIT_BUTTON).click();
    }

    public void cardDetails(String number, String expiryMonth, String expiryYear, String CVV) {
        // card number
        BrowserUtils.findElement(HotelPageLocators.CARD_NUMBER).sendKeys(number);

        // card month
        BrowserUtils.findElement(HotelPageLocators.CARD_MONTH_INPUT).click();

        List<WebElement> monthOption = BrowserUtils.findElements(By.xpath("//button[starts-with(@data-testid, 'cardMonth-option-')]"));
        for (WebElement month : monthOption) {
            if (month.getText().equals(expiryMonth)) {
                month.click();
                break;
            }
        }

        // card year
        WebElement yearDropdown = BrowserUtils.findElement(HotelPageLocators.CARD_YEAR_INPUT_BOX);
        yearDropdown.click();

        List<WebElement> yearOptions = BrowserUtils.findElements(By.xpath("//button[starts-with(@data-testid, 'cardYear-option-')]"));

        for (WebElement option : yearOptions) {
            if (option.getText().trim().equals(expiryYear)) {
                option.click();
                break;
            }
        }

        // card cvv
        BrowserUtils.findElement(HotelPageLocators.CVV).sendKeys(CVV);


        // card submit button
        BrowserUtils.waitForClickability(HotelPageLocators.PAYMENT_FORM_SUBMIT_BUTTON).click();

        // payment screen is displayed
        WebElement waitingMessage = BrowserUtils.findElement(HotelPageLocators.PAYMENT_ALERT_BODY);

        Assert.assertTrue(waitingMessage.isDisplayed());
        Assert.assertEquals(waitingMessage.getText(),"İşlem başarısız, lütfen başka bir kart ile tekrar deneyin.");
    }


    public void navigateToCheckInMonth(String checkinDate){
        List<WebElement> nowDate = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(HotelPageLocators.BUTTON_DATA_DAY_1));
        String NowDateAttribute = nowDate.get(0).getAttribute("title");

        String[] checkInDates = checkinDate.split("-");
        String[] checkNowDate = NowDateAttribute.split("-");

        int checkInYear = Integer.parseInt(checkInDates[0]);
        int checkInMonth = Integer.parseInt(checkInDates[1]);


        String yearNow = checkNowDate[0];
        String monthNow = checkNowDate[1];

        int yearNowInt = Integer.parseInt(yearNow);
        int monthNowInt = Integer.parseInt(monthNow);


        while(checkInYear > yearNowInt || (checkInYear == yearNowInt && checkInMonth > monthNowInt)) {
            WebElement rightClick = wait.until(ExpectedConditions.elementToBeClickable((HotelPageLocators.HOTEL_MONTH_FORWARD_BUTTON)));
            rightClick.click();


            List<WebElement> nowDates = BrowserUtils.findElements(HotelPageLocators.BUTTON_DATA_DAY_1);
            String testIdValues = nowDates.get(0).getAttribute("title");
            String[] parts2 = testIdValues.split("-");
            yearNow = parts2[0];
            monthNow = parts2[1];

            yearNowInt = Integer.parseInt(yearNow);
            monthNowInt = Integer.parseInt(monthNow);
        }
    }

    public static void adjustGuestCount(WebDriverWait wait,int desiredCount, By countLocator, By plusButtonLocator, By minusButtonLocator) {
        WebElement countElement = wait.until(ExpectedConditions.visibilityOfElementLocated(countLocator));
        int currentCount = Integer.parseInt(countElement.getText());

        WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(plusButtonLocator));
        WebElement minusButton = wait.until(ExpectedConditions.elementToBeClickable(minusButtonLocator));
        while (currentCount < desiredCount) {
            plusButton.click();
            currentCount++;
        }

        while (currentCount > desiredCount) {
            minusButton.click();
            currentCount--;
        }
    }
}
