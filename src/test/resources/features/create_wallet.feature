Feature: Create Wallet Management

  Scenario: Create wallet
    When I request to create a wallet
    Then I should receive a wallet