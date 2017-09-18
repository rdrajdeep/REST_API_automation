Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description
Given complete paymrent account flow of Expert
Given an expert
When login with expert1
Then create a payment account as {
                                    "account_number": "54784",
                                    "routing_number": "123456789",
                                    "account_name": "PayPal"
                                  }

Given negative scenario

When create a payment account as {
                                     "account_number": "retrtrt",
                                     "routing_number": "trtrtrt",
                                     "account_name": "test2"
                                 }

Then check error code 1061


Given negative scenario

When create a payment account as {
                                     "account_number": "6788",
                                     "routing_number": "5555",
                                     "account_name": "test2"
                                 }
Then check error code 1063