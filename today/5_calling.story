Given an user
Then register a device as {
                          "device_type": "ios",
                          "device_name": "USer device",
                          "device_sub_type": "sas",
                          "device_id": "sas",
                          "device_token": "sas",
                          "device_os": "sas"
                          }


Given an expert
Then register a device as {
                          "device_type": "ios",
                          "device_name": "Expert device",
                          "device_sub_type": "sas",
                          "device_id": "sas",
                          "device_token": "sas",
                          "device_os": "sas"
                          }


Given an user
Then create a card with fake-masterpass-mastercard-nonce
Then schedule a session

Then check the session status
Then initiate the call

Given an expert
Then accept the call
Then check the session status
Given an user
Then extend an ongoing session by scheduled_duration of 20

Given an expert
Then disconnect the call
Then check the session status
Given an user
Then provide review as {
                           "overall_rating": 1,
                           "knowledge_rating": 1,
                           "communication_rating": 1,
                           "professionalism_rating": 1,
                           "text_review": "dsdsadsd"
                       }

Then check the revenue

Then schedule a session again

Then cancel the session

Then get all the past session and verify

Then get all the future session and verify

Then check the session status

Then check the notification