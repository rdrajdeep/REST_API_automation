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
                        "coupon_code": "NEW8",
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
Then register a device as {
                               "device_type": "ios",
                               "device_name": "Expert device",
                               "device_sub_type": "ios",
                               "device_id": "13456",
                               "device_token": "token123",
                               "device_os": "ios"
                               }

Then get profile of the logged in expert
Then i am creating a calender as {
                              "title": "dsfsadf",
                              "start_time": "10:00:00",
                              "end_time": "10:30:00",
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

When schedule a session using promo code NEW8 and duration 20 min
Then it should return session id

When I pass on session id in session details API
Then user revenue should be 0.00 since 100% promo is applied
And expert estimated revenue should be 21.62 since payment type is experchat
And session status should be future-session

Given negative scenario
When i initiate the session
Then validate that session cannot be initiated before scheduled time