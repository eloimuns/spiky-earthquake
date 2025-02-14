# Wallets Service
I created a service that has 4 methods.

Post Wallet - With 0 balance Just to make test easier. 
    POST /wallet

Get Wallet by id
    GET /wallet/{id}

Get Wallet with transaction details
    GET /wallet/{id}/detail

Put Wallet for top-up
    PUT wallet/{id}/top-up

    BODY: {
        "amount": 200,
        "cardNumber": "4242 4242 4242 4242",
        "expireMonth": "12",
        "expireYear": "30",
        "cvc": "123"
    }

# BD MODEL
I've created 2x collections:

Wallets:
 - Id
 - Balance

Transactions:
 - Id
 - WalletId
 - Date
 - PaymentId
 - Status {SUCCESS / FAILED}
 - Type {TOP-UP / SPEND / REFUND}


# TESTS
I've added unit tests for each service, also I've added gherkins tests (BDD) using cucumber.

For cucumber, see:
test/resources/features/
java/com.playtomic.tests.wallet/stepDefs

# RUN COLLECTION - POSTMAN
I've also attached Playtomic.postman_collection.json with Postman run collections for this code challenge.



