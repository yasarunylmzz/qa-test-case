Feature: Complete Flight Booking Journey

  Scenario Outline: Book a Turkish Airlines flight and verify payment button
    Given I am on the Enuygun homepage
    When I search for a round-trip flight from "<fromCity>" to "<toCity>"
    And I select departure date as "<departureDate>" and return date as "<returnDate>"
    And I apply a departure time filter between "<departureTime>" and "<returnTime>"
    And I filter flights by Turkish Airlines only
    And I select the first available departure flight
    And I select the first available return flight

    When I enter contact details with email "<email>" and phone "<phone>"
    And I enter passenger information with name "<firstName>", surname "<lastName>", birth date "<birthDate>", and ID "<tcNumber>"
    And I click the continue button
    Then I should see the "Make Payment" button on the payment screen

    Examples:
      | fromCity | toCity | departureDate | departureTime | returnDate | returnTime | email         | phone        | firstName | lastName | birthDate  | tcNumber   |
      | İstanbul | Ankara | 2025-06-01    | 10            | 2025-06-10 | 18         | test@test.com | 05554443322  | Ahmet     | Yılmaz   | 1990-01-01 | 12345678901 |
