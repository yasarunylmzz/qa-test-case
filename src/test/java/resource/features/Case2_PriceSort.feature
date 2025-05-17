Feature: Flight search with Turkish Airlines filter and price sorting

  Scenario Outline: Verify price sorting and airline filter for Turkish Airlines flights
    Given I am on the Enuygun homepage
    When I search for a round-trip flight from "<fromCity>" to "<toCity>"
    And I select departure date as "<departureDate>" and return date as "<returnDate>"
    And I apply a departure time filter between <departureTime> and <returnTime>
    And I filter flights by Turkish Airlines only
    Then all displayed flights should be Turkish Airlines flights
    And the flight prices should be sorted in ascending order


    Examples:
      | fromCity | toCity | departureDate | departureTime | returnDate | returnTime |
      | İstanbul | Ankara | 2025-06-01    | 10            | 2025-06-10 | 18         |
