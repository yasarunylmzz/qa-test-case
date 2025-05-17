package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.SearchPage;
import utilities.DriverManager;

import java.time.Duration;

public class Case3Steps {
    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
    Actions actions = new Actions(driver);
    SearchPage searchPage = new SearchPage();

    @And("I select the first available departure flight")
    public void iSelectTheFirstAvailableDepartureFlight() {
        // TODO: Departure flight seçme işlemi buraya yazılacak
    }

    @And("I select the first available return flight")
    public void iSelectTheFirstAvailableReturnFlight() {
        // TODO: Return flight seçme işlemi buraya yazılacak
    }

    @When("I enter contact details with email {string} and phone {string}")
    public void iEnterContactDetailsWithEmailAndPhone(String email, String phone) {
        // TODO: Email ve telefon bilgilerini girme işlemi buraya yazılacak
    }

    @And("I enter passenger information with name {string}, surname {string}, birth date {string}, and ID {string}")
    public void iEnterPassengerInformationWithNameSurnameBirthDateAndID(String firstName, String lastName, String birthDate, String tcNumber) {
        // TODO: Yolcu bilgileri girme işlemi buraya yazılacak
    }

    @And("I click the continue button")
    public void iClickTheContinueButton() {
        // TODO: Devam butonuna tıklama işlemi buraya yazılacak
    }

    @Then("I should see the {string} button on the payment screen")
    public void iShouldSeeTheButtonOnThePaymentScreen(String buttonName) {
        // TODO: Butonun görünür olup olmadığını kontrol etme işlemi buraya yazılacak
    }




    @After
    public void tearDown() {
        driver.quit();
    }
}
