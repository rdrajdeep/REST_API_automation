Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description

Given complete registration and login flow
And an user

When register with {"email": "kishor+user229@atlogys.com","password": "testing123" } as user2

Then check success code 2001

Then Resend email verification instructions for {"email": "kishor+user229@atlogys.com"}

Then check success code 2041

Then Verify Email

Then check success code 2042

Then login with user2

Then Change password to jyoti1032 for user2

Then check success code 2021

Given negative scenario

When Reset password to 2 for user2

Then should not allowed

Then check error code 2000

When Reset password to qwerty12 for user2

Then check success code 2023

Then login with user2

Given complete phone verification flow

When we provide phone number as {
                                    "country_code":91,
                                    "mobile":"8447053658"
                                }

Then verification code should be sent

Then phone should be verified

Given negative scenario

When we provide phone number as {
                                    "country_code":91,
                                    "mobile":"7841"
                                }

Then should not get Verified

Given complete media upload flow

Then upload media as C:\Users\Kishor\Desktop\4.jpg

Given complete expert profile get flow by an user

Given an user

When login with user1

Then get profile of expert

Then logout

Then login with user1