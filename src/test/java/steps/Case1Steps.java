package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.DriverManager;
import java.time.Duration;
import java.util.List;

public class Case1Steps {
    WebDriver driver = BaseTest.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    Actions actions = new Actions(driver);

    @Given("I am on the Enuygun homepage")
    public void iAmOnTheEnuygunHomepage() {
        WebDriverManager.chromedriver().setup();

        driver.manage().window().maximize();
        driver.get("https://www.enuygun.com");

    }

    @When("I search for a round-trip flight from {string} to {string}")
    public void iSearchForARoundTripFlightFromToCity(String fromCity, String toCity) {

        WebElement radioButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@data-testid='search-round-trip-label']")));
        radioButton.click();

        WebElement fromCityInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-testid='endesign-flight-origin-autosuggestion-input']")));
        fromCityInput.clear();
        fromCityInput.sendKeys(fromCity);
        fromCityInput.sendKeys(Keys.ENTER);

        WebElement toCitys = driver.findElement(By.xpath("//input[@data-testid='endesign-flight-destination-autosuggestion-input']"));
        toCitys.clear();
        toCitys.sendKeys(toCity);

        WebElement toCityIsCorrect = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@data-testid='autosuggestion-custom-item-ankara-esenboga-havalimani']")));
        toCitys.sendKeys(Keys.ENTER);

    }

    @And("I select departure date as {string} and return date as {string}")
    public void iSelectDepartureDateAsReturnDateAs(String departureDate, String returnDate) {
        WebElement depertureDateInputButton = driver.findElement(By.xpath("//div[@data-testid='enuygun-homepage-flight-departureDate-datepicker-popover-button']"));
        depertureDateInputButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@data-testid='datepicker-active-day']")));

        List<WebElement> dateElements = driver.findElements(By.xpath("//button[@data-testid='datepicker-active-day']"));
        String testIdValue = dateElements.get(0).getAttribute("title");

        String[] parts = testIdValue.split("-");
        String yearNow = parts[0];
        String monthNow = parts[1];

        String[] date = departureDate.split("-");
        String year = date[0];
        String month = date[1];

        int yearNowInt = Integer.parseInt(yearNow);
        int monthNowInt = Integer.parseInt(monthNow);
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);

        while(yearInt > yearNowInt || (yearInt == yearNowInt && monthInt > monthNowInt)) {
            WebElement rightClick = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//button[@data-testid='enuygun-homepage-flight-departureDate-month-forward-button']"))));
            rightClick.click();

            List<WebElement> nowDates = driver.findElements(By.xpath("//div[@data-testid='enuygun-homepage-flight-departureDate-datepicker-calendar-month']"));
            String testIdValues = nowDates.get(0).getAttribute("id");
            String[] parts2 = testIdValues.split("-");
            yearNow = parts2[2];
            monthNow = parts2[3];

            yearNowInt = Integer.parseInt(yearNow);
            monthNowInt = Integer.parseInt(monthNow);
        }

        WebElement datePicker = driver.findElement(By.xpath("//button[@title='" + departureDate + "']"));
        datePicker.click();

        WebElement returnButton = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//input[@data-testid='enuygun-homepage-flight-returnDate-datepicker-input']"))));
        returnButton.click();


        List<WebElement> returnDateElements = driver.findElements(By.xpath("//button[@data-testid='datepicker-active-day']"));
        String returnTestIdValue = returnDateElements.get(0).getAttribute("title");

        String[] returnParts = returnTestIdValue.split("-");
        String returnYearNow = returnParts[0];
        String returnMonthNow = returnParts[1];

        String[] returnDates = returnDate.split("-");
        String returnYear = returnDates[0];
        String returnMonth = returnDates[1];

        int returnYearNowInt = Integer.parseInt(returnYearNow);
        int returnMonthNowInt = Integer.parseInt(returnMonthNow);
        int returnYearInt = Integer.parseInt(returnYear);
        int returnMonthInt = Integer.parseInt(returnMonth);

        while (returnYearInt > returnYearNowInt || (returnYearInt == returnYearNowInt && returnMonthInt > returnMonthNowInt)){
            WebElement rightClick = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//button[@data-testid='enuygun-homepage-flight-returnDate-month-forward-button']"))));
            rightClick.click();

            List<WebElement> nowDates = driver.findElements(By.xpath("//div[@data-testid='enuygun-homepage-flight-returnDate-datepicker-calendar-month']"));
            String testIdValues = nowDates.get(0).getAttribute("id");
            String[] parts2 = testIdValues.split("-");
            returnYearNow = parts2[2];
            returnMonthNow = parts2[3];

            returnYearNowInt = Integer.parseInt(returnYearNow);
            returnMonthNowInt = Integer.parseInt(returnMonthNow);
        }



        WebElement returnPicker = driver.findElement(By.xpath("//button[@title='" + returnDate + "']"));
        returnPicker.click();

        WebElement searchButton = driver.findElement(By.xpath("//button[@data-testid='enuygun-homepage-flight-submitButton']"));
        searchButton.click();

    }

    @And("I apply a departure time filter between {int} and {int}")
    public void iApplyADepartureTimeFilterBetweenAnd(int departureTime, int returnTime) {
        WebElement departureTimeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ctx-filter-departure-return-time card-header']")));
        departureTimeButton.click();

        WebElement sliderStep = driver.findElement(By.className("rc-slider-step"));
        int width = sliderStep.getSize().getWidth();

        int OffSet1 = (width / 24) * departureTime;

        int OffSet2 = (width / 24) *  returnTime;

        WebElement slider = driver.findElement(By.cssSelector(".rc-slider-handle.rc-slider-handle-1"));
        actions.clickAndHold(slider)
                .moveByOffset(OffSet1,0)
                .release()
                .perform();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='filter-loading']")));

        WebElement slider2 = driver.findElement(By.cssSelector(".rc-slider-handle.rc-slider-handle-2"));

        actions.dragAndDropBy(slider2, -(width - OffSet2),0).perform();

    }

        @Then("all displayed flights should have departure times between {int} and {int}")
        public void allDisplayedFlightsShouldHaveDepartureTimesBetweenAnd(int departureTime, int returnTime) {
        WebElement testIsOk = driver.findElement(By.xpath("//div[@class='filter-slider-content']"));

        String expectedText = String.format("%02d:%02d ile %02d:%02d arası", departureTime, 0, returnTime, 0);

        Assert.assertEquals(expectedText, testIsOk.getText());

        }

        @And("the flight list should be properly displayed")
        public void theFlightListShouldBeProperlyDisplayed() {
            WebElement flightList = driver.findElement(By.xpath("//div[@class='flight-list flight-list-departure    domesticList']"));
            Assert.assertTrue( flightList.isDisplayed());

            List<WebElement> flights = driver.findElements(By.xpath("//div[@data-flight-index]"));
            Assert.assertFalse("Uçuş listesi boş!", flights.isEmpty());

        }

    @And("the search results should match the selected route from {string} to {string}")
    public void theSearchResultsShouldMatchTheSelectedRouteFromFromCityToTo(String fromCity,String toCity) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = driver.findElement(By.xpath("//div[@class='form-header active']//strong[@class='graphic-strong']"));

        String text = "";
        if(element.isDisplayed()) {
            text = element.getText().trim();
            // Büyük küçük harf ayrımı yaparak araya boşluk koyuyoruz
            String fixedText = text.replaceAll("([a-zçğıöşü])([A-ZÇĞIÖŞÜ])", "$1 $2");
            System.out.println("deneme: '" + fixedText + "'");

            String expectedText = (fromCity + " " + toCity).trim();
            Assert.assertEquals(expectedText, fixedText);
        } else {
            System.out.println("Element DOM'da var ama görünür değil");
            Assert.fail("Element DOM'da var ama görünür değil");
        }
        BaseTest.quitDriver();

    }


}
