Meta:

Narrative:
As a expert
Create a calender
As a user
Get a slot of 10 min
Schedule a session
Initiate call

Scenario: scenario description

Given an expert
When i login with {"email":"rajdeep+expert@atlogys.com","password":"testing123"}
Then get profile of the logged in expert
Then i am creating a calender as {
                              "title": "dsfsadf",
                              "start_time": "13:00:00",
                              "end_time": "13:30:00",
                              "timezone": "Asia/Kolkata",
                              "week_days": [
                                  1,2,3,4,5
                              ]
                          }

Given an user
When i login with {"email":"kishor+user@atlogys.com","password":"testing123"}

Then i register a device as {
                              "device_type":"ios",
                              "device_name": "iPhone 6",
                              "device_sub_type": "iPhone 6",
                              "device_id": "12345",
                              "device_token": "12345",
                              "device_os": "ios"
                            }


Then get a slot

When schedule a session using promo code null and duration 10 min

Then check the session status
Then initiate a call if schedule time is equal or less than to current time

Given an expert
Then accept the call
Then check the session status
