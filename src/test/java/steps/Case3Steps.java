package steps;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.EndToEndHotelBookingSteps;

public class Case3Steps {
    EndToEndHotelBookingSteps endToEndHotelBookingSteps = new EndToEndHotelBookingSteps();


    @And("I navigate to the Hotel search section")
    public void iNavigateToTheHotelSearchSection() {
        endToEndHotelBookingSteps.hotelSearch();
    }

    @When("I type {string} in the Location field")
    public void iTypeInTheLocationField(String location) {
       endToEndHotelBookingSteps.hotelSelect(location);
    }

    @And("I select check-in date {string} and check-out date {string}")
    public void iSelectCheckInDateAndCheckOutDate(String checkin, String checkout) {
       endToEndHotelBookingSteps.checkInCheckOut(checkin,checkout);
    }

    @And("I set the guests to {string} and {string} with child ages {string}")
    public void iSetTheGuestsTo(String adult, String child, String childsAge) {
       endToEndHotelBookingSteps.setGuestAndSetChild(adult,child,childsAge);
    }

    @And("I click the Find Hotel button")
    public void iClickTheButton() {
        endToEndHotelBookingSteps.SubmitButton();
    }

    @Then("hotel prices should be sorted in ascending order the hotel detail tab should open")
    public void hotelPricesShouldBeSortedInAscendingOrder() {
        endToEndHotelBookingSteps.hotelPricesAscendingOrder();
    }

    @And("the hotel name should match between tabs and the Book a Room button should be visible")
    public void verifyHotelNameAndBookRoomButton() {
        endToEndHotelBookingSteps.verifyHotelAndBookRoomButton();
    }

    @And("I fill in the Guest details with first name {string}, last name {string}, email {string}, phone {string}, and gender {string}")
    public void fillGuestDetails(String firstName, String lastName, String email, String phone, String gender) {
        endToEndHotelBookingSteps.guessDetails(firstName,lastName,email,phone,gender);
    }

    @And("I enter the card information with number {string}, expiry month {string}, expiry year {string}, and CVV {string}")
    public void enterCardInformation(String number, String expiryMonth, String expiryYear, String CVV) {
        endToEndHotelBookingSteps.cardDetails(number,expiryMonth,expiryYear,CVV);
    }


}
