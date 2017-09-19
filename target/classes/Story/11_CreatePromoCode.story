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
                        "coupon_code": "TEST1",
                        "status": 1,
                        "is_deleted": false,
                        "user_usage_limit": 1,
                        "success_message": null,
                        "error_message": null,
                        "payment_type": 1,
                        "allowed_experts": [],
                        "allowed_users": []
                      }
Then login as user {"email":"kishor+user@atlogys.com","password":"testing123"}
Then Search an expert with id 12 and its slots 2017-09-20T15:30:00
And  schedule a call with expert 12 using promocode USERS100