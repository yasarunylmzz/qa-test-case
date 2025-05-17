package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import org.junit.Assert;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import pages.SearchPage;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;
import utilities.DriverManager;

public class Case1Steps {
    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
    Actions actions = new Actions(driver);
    SearchPage searchPage = new SearchPage();

    @Before
    @Given("I am on the Enuygun homepage")
    public void setUp() {
        String browser = ConfigurationReader.getProperty("browser");
        System.setProperty("browser", browser);

        String url = ConfigurationReader.getProperty("url");
        driver.manage().window().maximize();
        driver.get(url);

    }


    @When("I search for a round-trip flight from {string} to {string}")
    public void iSearchForARoundTripFlightFromToCity(String fromCity, String toCity) {


        BrowserUtils.waitForClickability(driver,By.xpath("//label[@data-testid='search-round-trip-label']"),10)
                .click();

        WebElement fromCityInput = BrowserUtils.waitForClickability(driver,By.xpath("//input[@data-testid='endesign-flight-origin-autosuggestion-input']"),10);
        fromCityInput.clear();
        fromCityInput.sendKeys(fromCity);
        fromCityInput.sendKeys(Keys.ENTER);

        WebElement toCitys = BrowserUtils.findElement(driver,By.xpath("//input[@data-testid='endesign-flight-destination-autosuggestion-input']"),10);
        toCitys.clear();
        toCitys.sendKeys(toCity);

        BrowserUtils.waitForClickability(driver, By.xpath("//li[@data-testid='endesign-flight-destination-autosuggestion-option-item-0']"),10);

        toCitys.sendKeys(Keys.ENTER);



    }

    @And("I select departure date as {string} and return date as {string}")
    public void iSelectDepartureDateAsReturnDateAs(String departureDate, String returnDate) {

        WebElement depertureDateInputButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-testid='enuygun-homepage-flight-departureDate-datepicker-popover-button']")));
        depertureDateInputButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@data-testid='datepicker-active-day']")));

        searchPage.moveToTargetMonth(departureDate, "departureDate");

        WebElement datePicker = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='" + departureDate + "']")));
        datePicker.click();

        WebElement returnButton = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//input[@data-testid='enuygun-homepage-flight-returnDate-datepicker-input']"))));
        returnButton.click();


        searchPage.moveToTargetMonth(returnDate, "returnDate");

        WebElement returnPicker = driver.findElement(By.xpath("//button[@title='" + returnDate + "']"));
        returnPicker.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<WebElement> checkBoxInputs = driver.findElements(By.xpath("//input[@data-testid='flight-oneWayCheckbox-input']"));

        System.out.println("checkBoxInputs: " + checkBoxInputs.get(1).getText());
        if (!checkBoxInputs.isEmpty()) {
            WebElement checkBoxInput = checkBoxInputs.get(1);
            boolean isSelected = checkBoxInput.isSelected();
            System.out.println("isSelected: " + isSelected);

            if (isSelected) {
                List<WebElement> label = driver.findElements(By.xpath("//label[@data-testid='flight-oneWayCheckbox-label']"));
                label.get(1).click();
            }
        }

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='enuygun-homepage-flight-submitButton']")));
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

    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }


}
