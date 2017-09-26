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
                        "coupon_code": "45",
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
When i login with {"email":"rajdeep+expert@atlogys.com","password":"testing123"}

Then get profile of the logged in expert

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

When schedule a session using promo code 45 and duration 10

Then i initiate the session






