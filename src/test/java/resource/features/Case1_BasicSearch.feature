Feature: Basic Flight Search and Time Filter

  Scenario Outline: Filter flights by departure time range
    Given I am on the Enuygun homepage
    When I search for a round-trip flight from "<fromCity>" to "<toCity>"
    And I select departure date as "<departureDate>" and return date as "<returnDate>"
    And I apply a departure time filter between <departureTime> and <returnTime>
    Then all displayed flights should have departure times between <departureTime> and <returnTime>
    And the flight list should be properly displayed
    And the search results should match the selected route from "<fromCity>" to "<toCity>"

    Examples:
      | fromCity | toCity  | departureDate | departureTime | returnDate | returnTime |
      | İstanbul | Ankara  | 2025-09-10    | 10         | 2025-09-15 | 18      |
      | İstanbul | Ankara  | 2025-07-12    | 10         | 2026-01-15 | 18      |


