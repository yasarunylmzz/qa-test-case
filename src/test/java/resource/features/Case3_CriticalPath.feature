Feature: Hotel search on Enuygun

  Scenario Outline: User searches for a hotel and sees results
    Given I am on the Enuygun homepage
    And I navigate to the Hotel search section
    When I type "<location>" in the Location field
    And I select check-in date "<checkin>" and check-out date "<checkout>"
    And I set the guests to "<adult>" and "<child>"
    And I click the "Find Hotel" button
    Then the hotel results page should be displayed
    And at least one hotel card should be listed

    Examples:
      | location  | checkin    | checkout   | adult | child |
      | Antalya   | 2024-05-19 | 2024-05-22 | 2     | 1     |
      | Istanbul  | 2024-06-01 | 2024-06-05 | 1     | 0     |
      | Izmir     | 2024-07-10 | 2024-07-15 | 2     | 1     |

