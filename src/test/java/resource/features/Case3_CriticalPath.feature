Feature: Hotel search on Enuygun

  Scenario Outline: User searches for a hotel and sees results
    Given I am on the Enuygun homepage
    And I navigate to the Hotel search section
    When I type "<location>" in the Location field
    And I select check-in date "<checkin>" and check-out date "<checkout>"
    And I set the guests to "<guests>"
    And I click the "Find Hotel" button
    Then the hotel results page should be displayed
    And at least one hotel card should be listed

    Examples:
      | location  | checkin   | checkout  | guests             |
      | Antalya   | 19 May    | 22 May    | 2 Adults, 1 Child  |
      | Istanbul  | 1 June    | 5 June    | 1 Adult            |
      | Izmir     | 10 July   | 15 July   | 2 Adults           |
