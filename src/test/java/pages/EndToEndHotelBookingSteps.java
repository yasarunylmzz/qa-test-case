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
        BrowserUtils.findElement(By.xpath("//a[@data-testid='header-nav-links-1']")).click();

        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        Assert.assertTrue(currentUrl.contains("https://www.enuygun.com/otel/"));
    }

    public void hotelSelect(String location){
        BrowserUtils.findElement(By.xpath("//input[@data-testid='endesign-hotel-autosuggestion-input']")).sendKeys(location);

        BrowserUtils.findElement(By.xpath("//li[@data-testid='endesign-hotel-autosuggestion-option-item-0']")).click();
    }

    public void checkInCheckOut(String checkin, String checkout) {
        BrowserUtils.findElement(By.xpath("//div[@data-testid='hotel-datepicker-popover-button']")).click();
        navigateToCheckInMonth(checkin);
        BrowserUtils.findElement(By.xpath("//button[@title='"+checkin+"']")).click();
        BrowserUtils.findElement(By.xpath("//button[@title='"+checkout+"']")).click();
        BrowserUtils.findElement(By.xpath("//div[@data-testid='hotel-datepicker-popover-button']")).click();


        List<WebElement> selectedDates = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@data-testid='datepicker-active-day']")));
        List<String> dates = selectedDates.stream()
                .map(e -> e.getAttribute("title"))
                .collect(Collectors.toList());

        BrowserUtils.findElement(By.xpath("//div[@data-testid='hotel-datepicker-popover-button']")).click();


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
                By.xpath("//div[@data-testid='hotel-adult-counter-count']"),
                By.xpath("//button[@data-testid='hotel-adult-counter-plus-button']"),
                By.xpath("//button[@data-testid='hotel-adult-counter-minus-button']")
        );

        //child
        adjustGuestCount(
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

        String isValue = BrowserUtils.findElement(By.xpath("//input[@data-testid='hotel-popover-button']")).getAttribute("value");

        if(child.isEmpty()){
            Assert.assertEquals(justAdultText,isValue);
        }
        Assert.assertEquals(childAndAdultText,isValue);

    }

    public void SubmitButton(){
        BrowserUtils.findElement(By.xpath("//button[@data-testid='hotel-submit-search-button']")).click();
        WebElement cityIsCorrect = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h3[@data-testid='hotel-result-title']")
        ));
        Assert.assertTrue(cityIsCorrect.isDisplayed());
    }

    public void hotelPricesAscendingOrder(){
        BrowserUtils.findElement(By.xpath("//button[@data-testid='sort-fiyat-artan-button']")).click();

        List<WebElement> hotels = BrowserUtils.findElements(By.xpath("//div[@data-testid='result-available-hotel']"));
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
        BrowserUtils.findElements(By.xpath("//div[@data-testid='offer-item-container']")).get(0).click();;

        //book a room buttons
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@data-testid='offer-select-room-button']"))).get(0).click();
    }

    public void guessDetails(String firstName, String lastName, String email, String phone, String gender) {
        // email fields
        BrowserUtils.findElement(By.xpath("//input[@data-testid='contact-email']")).sendKeys(email);

        // phone fields
        BrowserUtils.findElement(By.xpath("//input[@data-testid='contactPhone']")).sendKeys(phone);

        // firstname fields
        List<WebElement> names = BrowserUtils.findElements( By.xpath("//input[@data-testid='room-first-name']"));

        for (WebElement element : names) {
            element.sendKeys(firstName);
        }

        // lastname fields
        List<WebElement> lastNames = BrowserUtils.findElements(By.xpath("//input[@data-testid='room-last-name']"));
        for (WebElement element : lastNames) {
            element.sendKeys(lastName);
        }

        // gender fields
        BrowserUtils.findElement(By.xpath("//label[@data-testid='"+gender+"-label']")).click();

        // click button
        BrowserUtils.findElement(By.xpath("//button[@data-testid='reservation-form-submit-button']")).click();
    }

    public void cardDetails(String number, String expiryMonth, String expiryYear, String CVV) {
        // card number
        BrowserUtils.findElement(By.xpath("//input[@data-testid='cardNumber']")).sendKeys(number);

        // card month
        BrowserUtils.findElement(By.xpath("//input[@data-testid='cardMonth-input']")).click();

        List<WebElement> monthOption = driver.findElements(By.xpath("//button[starts-with(@data-testid, 'cardMonth-option-')]"));
        for (WebElement month : monthOption) {
            if (month.getText().equals(expiryMonth)) {
                month.click();
                break;
            }
        }

        // card year
        WebElement yearDropdown = BrowserUtils.findElement(By.xpath("//span[@data-testid='cardYear-input-box']"));
        yearDropdown.click();

        List<WebElement> yearOptions = driver.findElements(By.xpath("//button[starts-with(@data-testid, 'cardYear-option-')]"));

        for (WebElement option : yearOptions) {
            if (option.getText().trim().equals(expiryYear)) {
                option.click();
                break;
            }
        }

        // card cvv
        BrowserUtils.findElement(By.xpath("//input[@data-testid='CVV']")).sendKeys(CVV);


        // card submit button
        BrowserUtils.waitForClickability(By.xpath("//button[@data-testid='payment-form-submit-button']")).click();

        // payment screen is displayed
        WebElement waitingMessage = BrowserUtils.findElement(By.xpath("//div[@data-testid='[unnamed]-alert-body']"));
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
