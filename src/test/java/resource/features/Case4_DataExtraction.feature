Feature: Data Extraction

  Scenario Outline: Search for flights and export search results to CSV
    Given I am on the Enuygun homepage
    When I search for a round-trip flight from "<fromCity>" to "<toCity>"
    And I select departure date as "<departureDate>" and return date as "<returnDate>"
    And I apply a departure time filter between <departureTime> and <returnTime>
    Then the search results should be displayed
    And I extract departure and arrival times from each flight
    And I extract airline names from each flight
    And I extract prices from each flight
    And I extract connection information from each flight
    And I extract flight durations from each flight
    And I save all extracted data into a CSV file named "<fileName>"

    Examples:
      | fromCity | toCity  | departureDate | returnDate | departureTime | returnTime | fileName                 |
      | İstanbul | Lefkoşa | 2025-09-10     | 2025-09-15 | 10            | 18         | flights_istanbul_lefkoşa.csv |
