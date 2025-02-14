Feature: Top-Up Wallet Management

  Scenario Outline: Top-Up a wallet success
    Given a wallet with ID "<id>" and balance <balance> with transactions 0
    And a success charge
    And a top up with cardNumber: "<cardNumber>", expireMonth : "<expireMonth>", expireYear: "<expireYear>", cvc: "<cvc>" and amount: <top-up>
    When I request to top up wallet "<id>"
    Then I request the wallet with transactions with ID "<id>"
    Then I should receive success paymentResponse
    Then I should receive a paymentId
    Then I should receive a wallet with ID "<id>" and balance <new-balance>
    Then I should receive 1 transactions
    Examples:
      | id                       | balance | cardNumber       | expireMonth | expireYear | cvc | top-up | new-balance |
      | 67af5a0408455bb038e97bf9 | 100     | 4242424242424242 | 12          | 30         | 123 | 200    | 300         |
      | 67af710a88dede77ee2a51cb | 50      | 4242424242424242 | 06          | 25         | 123 | 150    | 200         |

  Scenario Outline: Top-Up a wallet failed on charge (small amount)
    Given a wallet with ID "<id>" and balance <balance> with transactions 0
    And a exception on charge
    And a top up with cardNumber: "<cardNumber>", expireMonth : "<expireMonth>", expireYear: "<expireYear>", cvc: "<cvc>" and amount: <top-up>
    When I request to top up wallet "<id>"
    Then I should receive failed paymentResponse
    Then I request the wallet with transactions with ID "<id>"
    Then I should receive a wallet with ID "<id>" and balance <balance>
    Then I should receive 1 transactions
    Examples:
      | id                       | balance | cardNumber       | expireMonth | expireYear | cvc | top-up |
      | 67af77a18bfd242773bbb40d | 100     | 4242424242424242 | 12          | 30         | 123 | 1      |
      | 67af77a77bfcd630a0d5bb11 | 50      | 4242424242424242 | 06          | 25         | 123 | 3      |

  Scenario Outline: Top-Up a wallet failed on charge (invalid request)
    Given a wallet with ID "<id>" and balance <balance> with transactions 0
    And a exception on charge
    And a top up with cardNumber: "<cardNumber>", expireMonth : "<expireMonth>", expireYear: "<expireYear>", cvc: "<cvc>" and amount: <top-up>
    When I request to top up wallet "<id>" should throw exception
    Then I request the wallet with transactions with ID "<id>"
    Then I should receive a wallet with ID "<id>" and balance <balance>
    Then I should receive 0 transactions
    Examples:
      | id                       | balance | cardNumber       | expireMonth | expireYear | cvc | top-up |
      | 67af77d86e6d45d401502974 | 50      |                  | 06          | 25         | 123 | 3      |
      | 67af77e46c1abd7b77155d43 | 50      | 4242424242424242 |             | 25         | 123 | 3      |
      | 67af77e941fc5f114eb21ed3 | 50      | 4242424242424242 | 06          |            | 123 | 3      |
      | 67af77ec22abbe2122d7c1ec | 50      | 4242424242424242 | 06          | 25         |     | 3      |

