Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description

Given an expert

Given a complete phone no verify flow

When we provide phone number as {
                                    "country_code":91 ,
                                    "mobile": "8447053658"
                                }

Then verification code should be sent

Then phone should be verified

Given a negative scenario

When we provide phone number as {
                                    "country_code":91 ,
                                    "mobile": "4584"
                                }


Then phone should not get Verified
