package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Before;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HotelSearchPage;
import pages.SearchPage;
import utilities.DriverManager;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Case3Steps {
    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
    Actions actions = new Actions(driver);
    SearchPage searchPage = new SearchPage();

    @Before
    public void setUp() {}


    @And("I navigate to the Hotel search section")
    public void iNavigateToTheHotelSearchSection() {
        WebElement hotelButton = driver.findElement(By.xpath("//a[@data-testid='header-nav-links-1']"));
        hotelButton.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("https://www.enuygun.com/otel/"));
    }

    @When("I type {string} in the Location field")
    public void iTypeInTheLocationField(String location) {
        WebElement locationField = driver.findElement(By.xpath("//input[@data-testid='endesign-hotel-autosuggestion-input']"));
        locationField.sendKeys(location);

        WebElement locationFieldAutoSuggestions = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@data-testid='endesign-hotel-autosuggestion-option-item-0']")));
        locationFieldAutoSuggestions.click();
    }

    @And("I select check-in date {string} and check-out date {string}")
    public void iSelectCheckInDateAndCheckOutDate(String checkin, String checkout) {
        WebElement dateButton = driver.findElement(By.xpath("//div[@data-testid='hotel-datepicker-popover-button']"));
        dateButton.click();

        List<WebElement> nowDate = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@data-day='1']")));
        String NowDateAttribute = nowDate.get(0).getAttribute("title");

        String[] checkInDates = checkin.split("-");
        String[] checkOutDates = checkout.split("-");
        String[] checkNowDate = NowDateAttribute.split("-");

        int checkInYear = Integer.parseInt(checkInDates[0]);
        int checkInMonth = Integer.parseInt(checkInDates[1]);
        int checkInDay = Integer.parseInt(checkInDates[2]);

        int checkOutMonth = Integer.parseInt(checkOutDates[1]);
        int checkOutYear = Integer.parseInt(checkOutDates[0]);
        int checkOutDay = Integer.parseInt(checkOutDates[2]);

        String yearNow = checkNowDate[0];
        String monthNow = checkNowDate[1];
        String dayNow = checkNowDate[2];

        int yearNowInt = Integer.parseInt(yearNow);
        int monthNowInt = Integer.parseInt(monthNow);
        int dayNowInt = Integer.parseInt(dayNow);

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
        WebElement checkInButton = driver.findElement(By.xpath("//button[@title='"+checkin+"']"));
        checkInButton.click();

        WebElement checkOutButton = driver.findElement(By.xpath("//button[@title='"+checkout+"']"));
        checkOutButton.click();

        dateButton.click();

        List<WebElement> selectedDates = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@data-testid='datepicker-active-day']")));
        List<String> dates = selectedDates.stream()
                .map(e -> e.getAttribute("title"))
                .collect(Collectors.toList());
        System.out.println("Dates: " + dates);

        dateButton.click();

        Assert.assertTrue(dates.contains(checkin));
        Assert.assertTrue(dates.contains(checkout));
    }

    @And("I set the guests to {string} and {string} with child ages {string}")
    public void iSetTheGuestsTo(String adult, String child, String childsAge) {
        WebElement guestButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-testid='hotel']")));
        guestButton.click();

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

        WebElement isCorrect = driver.findElement(By.xpath("//input[@data-testid='hotel-popover-button']"));
        String isValue = isCorrect.getAttribute("value");

        if(child.isEmpty()){
            Assert.assertEquals(justAdultText,isValue);
        }
        Assert.assertEquals(childAndAdultText,isValue);



    }

    @And("I click the Find Hotel button")
    public void iClickTheButton() {
        WebElement submitButton = driver.findElement(By.xpath("//button[@data-testid='hotel-submit-search-button']"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement cityIsCorrect = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h3[@data-testid='hotel-result-title']")
        ));

        Assert.assertTrue(cityIsCorrect.isDisplayed());
    }

    @Then("hotel prices should be sorted in ascending order the hotel detail tab should open")
    public void hotelPricesShouldBeSortedInAscendingOrder() {
        WebElement ascendigOrderButton = driver.findElement(By.xpath("//button[@data-testid='sort-fiyat-artan-button']"));
        ascendigOrderButton.click();

        List<WebElement> hotels = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-testid='result-available-hotel']")));
        WebElement hotelNameElement = hotels.get(0).findElement(By.xpath(".//h4[@data-testid='result-title']"));
        String hotelName = hotelNameElement.getText();
        hotels.get(0).click();



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

    @And("the hotel name should match between tabs and the Book a Room button should be visible")
    public void verifyHotelNameAndBookRoomButton() {

        List<WebElement> offerRadioButtons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-testid='offer-item-container']")));
        offerRadioButtons.get(0).click();

        List<WebElement> bookARoom = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@data-testid='offer-select-room-button']")));
        bookARoom.get(0).click();

    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
