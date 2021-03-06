Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description

Given complete profile status check flow

Given an expert

When register with {"email":"kishor+expert140@atlogys.com","password":"testing123"} as expert2

Then Verify Email

Then login with expert2

Then check profile completness

Then add name as {"name": "Sarma Kishor", "toc_and_privacy_policy_accepted":true}

Then add profile photo as C:\Users\Kishor\Desktop\4.jpg

Then check profile completness

Then Create a new Profile as
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

Then add {"url": "http://feeds.feedburner.com/ndtvnews-world-news"} as RSS Feed

Then check profile completness

Then we provide phone number as {
                                    "country_code":91 ,
                                    "mobile": "8447053658"
                                }
Then phone should be verified

Then check profile completness
