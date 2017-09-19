Meta:

Given complete Expert registration and login flow

Given an expert

When register with {"email": "kishor+61expert@atlogys.com","password": "testing123" } as expert1

Then check success code 2001

When Resend email verification instructions for {"email": "kishor+61expert@atlogys.com"}

Then check success code 2042

And Verify Email

Then check success code 2041

Then login with expert1

Then Change password to jyoti1032 for expert1

Then check success code 2021

Then Reset password to qerty123 for expert1

Then check success code 2032

Given an user
When register with {"email": "kishor+61user@atlogys.com","password": "testing123" } as user1
Then Verify Email

Given an expert
Then login with expert1

Given an user
Then login with user1