Feature: Hotel search on Enuygun

  Scenario Outline: User searches for a hotel and sees results
    Given I am on the Enuygun homepage
    And I navigate to the Hotel search section
    When I type "<location>" in the Location field
    And I select check-in date "<checkin>" and check-out date "<checkout>"
    And I set the guests to "<adult>" and "<child>" with child ages "<childAges>"
    And I click the Find Hotel button
    Then hotel prices should be sorted in ascending order the hotel detail tab should open
    And the hotel name should match between tabs and the Book a Room button should be visible

    Examples:
      | location  | checkin    | checkout   | adult | child | childAges |
      | Hamburg   | 2025-06-19 | 2025-06-22 | 2     | 1     |     10    |
      | Istanbul  | 2025-06-01 | 2025-06-05 | 3     | 2     |   11,12   |
      | Izmir     | 2026-01-10 | 2026-01-15 | 2     | 1     |     9     |

