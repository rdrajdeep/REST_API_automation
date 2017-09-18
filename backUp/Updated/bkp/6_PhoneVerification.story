Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description

Given complete phone no verify flow of Expert
Given an expert
When login with expert1
Then get profile

When we provide phone number as {
                                    "country_code":91 ,
                                    "mobile": "8447053658"
                                }

Then verification code should be sent

Then phone should be verified

Given negative scenario

When we provide phone number as {
                                    "country_code":91 ,
                                    "mobile": "4584"
                                }


Then should not get Verified
