Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal
Scenario: scenario description

Given complete expert profile flow
Given an expert
Then get the profile

Given negative scenario
Then try to create expert profile as
{
    "tags": [
       1,2,3,4
        ],
    "medias":[],
    "headline": "fdfd",
    "summary": "fdfd",
    "my_experience": "fdfdff",
    "year_of_experience":"2",
    "educational_background": "Bachelor of Technology (Computer Science and Information Engineering)"
}

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

Then create a calender as
    {
    "title": "a test",
    "start_time": "02:00",
    "end_time": "04:00",
    "timezone": "Asia/Kolkata",
    "week_days": [3]
}

Then get the calender

And update the calender as  {
                               "title": "a test",
                               "start_time":"08:00",
                               "end_time":"10:00",
                               "timezone": "Asia/Kolkata",
                               "week_days": [2]
                           }

Then get the calender again

And get the avilable slot of expert1

Then upload media as TestData/4.jpg