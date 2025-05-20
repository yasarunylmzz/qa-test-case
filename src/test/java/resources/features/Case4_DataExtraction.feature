Feature: Data Extraction

  Scenario Outline: Search for flights and export search results to CSV
    Given I am on the Enuygun homepage
    When I search for a round-trip flight from "<fromCity>" to "<toCity>"
    And I select departure date as "<departureDate>"
    And I extract flight information times from each flight
    And I save all extracted data into a CSV file named "<fromCity>" and "<toCity>"
    Then I analyze the search results "<fromCity>" and "<toCity>"

    Examples:
      | fromCity | toCity  | departureDate |
      | İstanbul | Lefkoşa | 2025-09-10    |
      | Londra | Hamburg | 2025-09-10     |
      | Moskova | Baku | 2025-09-10     |
      | İstanbul | Berlin | 2025-05-24  |


