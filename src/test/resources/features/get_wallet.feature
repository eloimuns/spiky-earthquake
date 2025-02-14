Feature: Get Wallet Management

  Scenario Outline: Get existing wallet dto without transactions
    Given a wallet with ID "<id>" and balance <balance> with transactions 0
    When I request the wallet with ID "<id>"
    Then I should receive a wallet with ID "<id>" and balance <balance>
    Then I shouldn't receive transactions
    Examples:
      | id                       | balance |
      | 67af5a0408455bb038e97bf9 | 5       |
      | 67af5a086852cfcb12a59198 | 10      |
      | 67af5a0eeef9f8a571a058e4 | 15      |

  Scenario Outline: Get existing wallet dto with transactions
    Given a wallet with ID "<id>" and balance <balance> with transactions <transactions>
    When I request the wallet with transactions with ID "<id>"
    Then I should receive a wallet with ID "<id>" and balance <balance>
    Then I should receive <transactions> transactions
    Examples:
      | id                       | balance | transactions |
      | 67af5a0408455bb038e97bf9 | 5       | 1            |
      | 67af5a086852cfcb12a59198 | 10      | 2            |
      | 67af5a0eeef9f8a571a058e4 | 15      | 0            |

  Scenario: Get non-existing wallet
    When I request the wallet with ID "67af2fe6a3a928cfe4f83724"
    Then I should receive a null wallet