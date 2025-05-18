Feature: Data Extraction

  Scenario Outline: Search for flights and export search results to CSV
    Given I am on the Enuygun homepage
    When I search for a round-trip flight from "<fromCity>" to "<toCity>"
    And I select departure date as "<departureDate>" and return date as "<returnDate>"
    And I extract flight information times from each flight
    And I save all extracted data into a CSV file named "<fileName>"

    Examples:
      | fromCity | toCity  | departureDate | returnDate |  fileName                 |
      | İstanbul | Lefkoşa | 2025-09-10     | 2025-09-15 | flights_istanbul_lefkoşa.csv |
