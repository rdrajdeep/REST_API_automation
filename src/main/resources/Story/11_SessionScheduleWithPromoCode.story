Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description

When login as super user {"email":"kishor+super@atlogys.com","password":"testing123"}

Then create promocode {
                        "promo_code_type": "promo",
                        "value_type": "percent%",
                        "value": 100,
                        "start_datetime": "2017-09-15T00:45:00Z",
                        "expiry_datetime": "2017-10-30T02:25:00Z",
                        "usage_limit": 10,
                        "description": "100 % Discount on every user",
                        "coupon_code": "FU1002",
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

Then get a slot


Given an user
When i login with {"email":"kishor+user@atlogys.com","password":"testing123"}

Then i register a device as {
                              "device_type":"ios",
                              "device_name": "test",
                              "device_sub_type": "ewe",
                              "device_id": "323",
                              "device_token": "323",
                              "device_os": "ios"
                          }

When schedule a session using promo code FU1002 and duration 20

Then it should return session id

When I get the session details

Then user revenue should be 0.00 since 100% promo is applied

And expert revenue should be 21.62 since payment type is experchat

And session status should be future-session

When i initiate the session

Then  validate that session can not be intiated before time