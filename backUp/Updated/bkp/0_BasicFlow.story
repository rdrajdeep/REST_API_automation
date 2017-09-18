Meta:

Given complete Expert registration and login flow

Given an expert

When register with {"email": "kishor+expert230@atlogys.com","password": "testing123" } as expert1

Then check success code 2001

Then Resend email verification instructions for {"email": "kishor+expert230@atlogys.com"}

Then check success code 2041

Then Verify Email

Then check success code 2042

Then login with expert1

Then Change password to jyoti1032 for expert1

Then check success code 2021

Then Reset password to qerty123 for expert1
Then check success code 2023

Given negative scenario

Then login with {"email": "kishor+expert9000@atlogys.com","password": "jyoti1032" }
Then check error code 1012

Given an user

When register with {"email": "kishor+user230@atlogys.com","password": "testing123" } as user1

Then Verify Email