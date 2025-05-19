package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.BrowserUtils;
import utilities.DriverManager;

import java.time.Duration;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EndToEndHotelBookingSteps {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public EndToEndHotelBookingSteps() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void hotelSearch() {
        BrowserUtils.findElement(driver,By.xpath("//a[@data-testid='header-nav-links-1']"),10).click();
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        Assert.assertTrue(currentUrl.contains("https://www.enuygun.com/otel/"));
    }

    public void hotelSelect(String location){
        BrowserUtils.findElement(driver,By.xpath("//input[@data-testid='endesign-hotel-autosuggestion-input']"),10).sendKeys(location);

        BrowserUtils.findElement(driver,By.xpath("//li[@data-testid='endesign-hotel-autosuggestion-option-item-0']"),30).click();
    }

    public void checkInCheckOut(String checkin, String checkout) {
        BrowserUtils.findElement(driver,By.xpath("//div[@data-testid='hotel-datepicker-popover-button']"),10).click();
        navigateToCheckInMonth(checkin);
        BrowserUtils.findElement(driver,By.xpath("//button[@title='"+checkin+"']"),10).click();
        BrowserUtils.findElement(driver,By.xpath("//button[@title='"+checkout+"']"),10).click();
        BrowserUtils.findElement(driver,By.xpath("//div[@data-testid='hotel-datepicker-popover-button']"),10).click();


        List<WebElement> selectedDates = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@data-testid='datepicker-active-day']")));
        List<String> dates = selectedDates.stream()
                .map(e -> e.getAttribute("title"))
                .collect(Collectors.toList());

        BrowserUtils.findElement(driver,By.xpath("//div[@data-testid='hotel-datepicker-popover-button']"),10).click();


        Assert.assertTrue(dates.contains(checkin));
        Assert.assertTrue(dates.contains(checkout));
    }

    public void setGuestAndSetChild(String adult, String child, String childsAge){
        // adult guest button
        BrowserUtils.waitForClickability(driver,By.xpath("//div[@data-testid='hotel']"),10).click();


        String childAndAdultText = adult + " Yetişkin, " + child + " Çocuk," + " 1 Oda";
        String justAdultText = adult + "Yetişkin," + "1 Oda";
        int childInt = Integer.parseInt(child);

        //adult
        HotelSearchPage.adjustGuestCount(
                wait,
                Integer.parseInt(adult),
                By.xpath("//div[@data-testid='hotel-adult-counter-count']"),
                By.xpath("//button[@data-testid='hotel-adult-counter-plus-button']"),
                By.xpath("//button[@data-testid='hotel-adult-counter-minus-button']")
        );

        //child
        HotelSearchPage.adjustGuestCount(
                wait,
                Integer.parseInt(child),
                By.xpath("//div[@data-testid='hotel-child-counter-count']"),
                By.xpath("//button[@data-testid='hotel-child-counter-plus-button']"),
                By.xpath("//button[@data-testid='hotel-child-counter-minus-button']")
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

        String isValue = BrowserUtils.findElement(driver,By.xpath("//input[@data-testid='hotel-popover-button']"),10).getAttribute("value");

        if(child.isEmpty()){
            Assert.assertEquals(justAdultText,isValue);
        }
        Assert.assertEquals(childAndAdultText,isValue);

    }

    public void SubmitButton(){
        BrowserUtils.findElement(driver,By.xpath("//button[@data-testid='hotel-submit-search-button']"),10).click();
        WebElement cityIsCorrect = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h3[@data-testid='hotel-result-title']")
        ));
        Assert.assertTrue(cityIsCorrect.isDisplayed());
    }

    public void hotelPricesAscendingOrder(){
        BrowserUtils.findElement(driver,By.xpath("//button[@data-testid='sort-fiyat-artan-button']"),10).click();

        List<WebElement> hotels = BrowserUtils.findElements(driver,By.xpath("//div[@data-testid='result-available-hotel']"),10);
        WebElement hotelNameElement = hotels.get(0).findElement(By.xpath(".//h4[@data-testid='result-title']"));
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

        WebElement hotelNameDOM = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@data-testid='hotel-title']")));

        Assert.assertEquals(hotelName, hotelNameDOM.getText());
    }

    public void verifyHotelAndBookRoomButton() {
        //offer radio buttons
        BrowserUtils.findElements(driver,By.xpath("//div[@data-testid='offer-item-container']"),10).get(0).click();;

        //book a room buttons
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@data-testid='offer-select-room-button']"))).get(0).click();
    }

    public void guessDetails(String firstName, String lastName, String email, String phone, String gender) {
        // email fields
        BrowserUtils.findElement(driver,By.xpath("//input[@data-testid='contact-email']"),10).sendKeys(email);

        // phone fields
        BrowserUtils.findElement(driver,By.xpath("//input[@data-testid='contactPhone']"),10).sendKeys(phone);

        // firstname fields
        List<WebElement> names = BrowserUtils.findElements(driver, By.xpath("//input[@data-testid='room-first-name']"), 10);

        for (WebElement element : names) {
            element.sendKeys(firstName);
        }

        // lastname fields
        List<WebElement> lastNames = BrowserUtils.findElements(driver,By.xpath("//input[@data-testid='room-last-name']"),10);
        for (WebElement element : lastNames) {
            element.sendKeys(lastName);
        }

        // gender fields
        BrowserUtils.findElement(driver,By.xpath("//label[@data-testid='"+gender+"-label']"),10).click();

        // click button
        BrowserUtils.findElement(driver,By.xpath("//button[@data-testid='reservation-form-submit-button']"),10).click();
    }

    public void cardDetails(String number, String expiryMonth, String expiryYear, String CVV) {
        // card number
        BrowserUtils.findElement(driver,By.xpath("//input[@data-testid='cardNumber']"),10).sendKeys(number);

        // card month
        BrowserUtils.findElement(driver,By.xpath("//input[@data-testid='cardMonth-input']"),10).click();

        List<WebElement> monthOption = driver.findElements(By.xpath("//button[starts-with(@data-testid, 'cardMonth-option-')]"));
        for (WebElement month : monthOption) {
            if (month.getText().equals(expiryMonth)) {
                month.click();
                break;
            }
        }

        // card year
        WebElement yearDropdown = BrowserUtils.findElement(driver, By.xpath("//span[@data-testid='cardYear-input-box']"), 10);
        yearDropdown.click();

        List<WebElement> yearOptions = driver.findElements(By.xpath("//button[starts-with(@data-testid, 'cardYear-option-')]"));

        for (WebElement option : yearOptions) {
            if (option.getText().trim().equals(expiryYear)) {
                option.click();
                break;
            }
        }

        // card cvv
        BrowserUtils.findElement(driver,By.xpath("//input[@data-testid='CVV']"),10).sendKeys(CVV);


        // card submit button
        BrowserUtils.waitForClickability(driver,By.xpath("//button[@data-testid='payment-form-submit-button']"),20).click();

        // payment screen is displayed
        WebElement waitingMessage = BrowserUtils.findElement(driver,By.xpath("//div[@data-testid='[unnamed]-alert-body']"),20);
        Assert.assertEquals(waitingMessage.getText(),"İşlem başarısız, lütfen başka bir kart ile tekrar deneyin.");
    }


    public void navigateToCheckInMonth(String checkinDate){
        List<WebElement> nowDate = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@data-day='1']")));
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
            WebElement rightClick = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//button[@data-testid='hotel-month-forward-button']"))));
            rightClick.click();


            List<WebElement> nowDates = driver.findElements(By.xpath("//button[@data-day='1']"));
            String testIdValues = nowDates.get(0).getAttribute("title");
            String[] parts2 = testIdValues.split("-");
            yearNow = parts2[0];
            monthNow = parts2[1];

            yearNowInt = Integer.parseInt(yearNow);
            monthNowInt = Integer.parseInt(monthNow);
        }
    }
}
