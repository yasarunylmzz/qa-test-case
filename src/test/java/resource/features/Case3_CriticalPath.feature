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
    And I fill in the Guest details with first name "<firstName>", last name "<lastName>", email "<email>", phone "<phone>", and gender "<gender>"
    And I enter the card information with number "<cardNumber>", expiry month "<expiryMonth>", expiry year "<expiryYear>", and CVV "<cvv>"

    Examples:
      | location  | checkin    | checkout   | adult | child | childAges | firstName | lastName | email | phone | gender |  cardNumber | expiryMonth | expiryYear | cvv |
      | Milano   | 2025-06-19 | 2025-06-22 | 2     | 1     |     10    |  Ahmet    |  Yılmaz  | test@test.com | 5555555555 | male | 4111111111111111 | 12 | 2030 |  123   |
      | Istanbul  | 2025-06-01 | 2025-06-05 | 3     | 2     |   11,12   |  Ahmet    |  Yılmaz  | test@test.com | 5555555555 | male | 5555555555554444 | 11 | 2031 |  456    |
      | Izmir     | 2026-01-10 | 2026-01-15 | 2     | 1     |     9     |  Ahmet    |  Yılmaz  | test@test.com | 5555555555 | male | 378282246310005 | 10 | 2032 |   789   |

