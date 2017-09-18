Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description

Given complete profile status check flow

Given an expert

Given an expert

When register with {"email": "kishor+expert23@atlogys.com","password": "testing123" } as expert2
Then Verify Email
And login with expert2
And get expert profile

Then update information on expert profile as
{
    "tags": [
       1,2,3,4
        ],
    "medias":[],
    "headline": "Java-8 Lambda",
    "summary": "Java Expert",
    "my_experience": "In test automation",
    "year_of_experience":"3",
    "educational_background": "Bachelor of Technology (Computer Science and Information Engineering)"
}

Then check profile completness

Then add name as {"name": "Sarma Kishor","display_name": "sarma", "toc_and_privacy_policy_accepted":true}

Then add profile photo as TestData/4.jpg

Then check profile completness

Then add {"url": "http://feeds.feedburner.com/ndtvnews-world-news"} as RSS Feed

Then check profile completness

Then we provide phone number as {
                                    "country_code":91 ,
                                    "mobile": "8447053658"
                                }
Then phone should be verified

Then check profile completness


Then create a calender as
    {
    "title": "a test",
    "start_time": "02:00",
    "end_time": "04:00",
    "timezone": "Asia/Kolkata",
    "week_days": [3]
}

Then check profile completness