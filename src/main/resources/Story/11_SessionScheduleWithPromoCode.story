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
                        "coupon_code": "PR26",
                        "status": 1,
                        "is_deleted": false,
                        "user_usage_limit": 1,
                        "success_message": null,
                        "error_message": null,
                        "payment_type": 1,
                        "allowed_experts": [],
                        "allowed_users": []
                      }

Given an expert
When i login with {"email":"kishor+expert@atlogys.com","password":"testing123"}

Then get profile of the logged in expert

Then i am creating a calender as {
                              "title": "dsfsadf",
                              "start_time": "8:00:00",
                              "end_time": "23:45:00",
                              "timezone": "Asia/Kolkata",
                              "week_days": [
                                  1,2,3,4,5,6,7
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

When schedule a session using promo code PR26 and duration 20

Then it should return session id

When I pass on session id in session details API

Then user revenue should be 0.00 since 100% promo is applied

And expert estimated revenue should be 21.62 since payment type is experchat

And session status should be future-session

When i initiate the session

Then validate that session cannot be initiated before scheduled time


When I schedule a session 10 min prior to available slot for duration 20 min
