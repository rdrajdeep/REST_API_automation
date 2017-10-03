Meta:

Narrative:
As a super user
I want to create a PROMO code
As a Expert
I want to create a chating slots
As a user
I want to register a device
So that I can schedule a session with the expert


When login as super user {"email":"kishor+super@atlogys.com","password":"testing123"}
Then create promocode {
                        "promo_code_type": "promo",
                        "value_type": "percent%",
                        "value": 100,
                        "start_datetime": "2017-09-15T00:45:00Z",
                        "expiry_datetime": "2017-10-30T02:25:00Z",
                        "usage_limit": 10,
                        "description": "100 % Discount on every user",
                        "coupon_code": "78",
                        "status": 1,
                        "is_deleted": false,
                        "user_usage_limit":20,
                        "success_message": null,
                        "error_message": null,
                        "payment_type": 1,
                        "allowed_experts": [],
                        "allowed_users": []
                      }

Given an expert
When i login with {"email":"rajdeep+expert@atlogys.com","password":"testing123"}
Then get profile of the logged in expert
And i create a calender of 20 min for today

Given an user
When i login with {"email":"kishor+user@atlogys.com","password":"testing123"}
Then i register a device as {
                              "device_type":"ios",
                              "device_name": "iPhone 6",
                              "device_sub_type": "iPhone 6",
                              "device_id": "25634",
                              "device_token": "25634",
                              "device_os": "ios"
                            }
Then get a slot
When schedule a session using promo code 78 and duration 10
Then it should return session id

Then I cancel my scheduled session

When I get a slot
Then schedule a session using promo code 78 and duration 10

Then I initiate the session

Given an expert
Then i register a expert device as {
                              "device_type":"ios",
                              "device_name": "iPhone 6",
                              "device_sub_type": "iPhone 6",
                              "device_id": "12345",
                              "device_token": "12345",
                              "device_os": "ios"
                            }

When I get a call from user
Then I will accept it
And Call should be in accepted status

Then I will disconnect the call
And Call should be in disconnected status

Given an user
Then I will reconnect the same call
And Call should be reconnected sucessfully

Given an expert
Then I will decline the call
And status should be declined

Given an user
Then I will reconnect the same call
And Call should be reconnected sucessfully

Given an expert
Then I will accept it
And status should be accepted

Given an user
Then wait for session extenstion
And verify if session extension is possible
Then If possible, Extend the call for 10 min
Then Call should be in extended status

When I get the session details
Then I wait for another session extenstion
And verify if session extension is possible

Given negative scenario
When No slot is available for extension
Then User should not allowed to extend the call
