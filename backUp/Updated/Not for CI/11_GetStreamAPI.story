Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description

Given complete GetStream feed flow

Given an expert
When login with expert1
Then expert should be able to post expert profile as
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

Then get the feeds from get stream by expertProfileID

Then get the feeds from get stream by expertID




