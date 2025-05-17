Feature: Basic Flight Search and Time Filter

  Scenario Outline: Filter flights by departure time range
    Given I am on the Enuygun homepage
    When I search for a round-trip flight from "<fromCity>" to "<toCity>"
    And I select departure date as "<departureDate>" and return date as "<returnDate>"
    And I apply a departure time filter between <departureTime> and <returnTime>

    Examples:
      | fromCity | toCity  | departureDate | departureTime | returnDate | returnTime |
      | Istanbul | Ankara  | 2025-06-01    | 10         | 2025-06-05 | 18      |

