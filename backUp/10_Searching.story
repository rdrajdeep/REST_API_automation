Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Given an user

Given a text beauty

When register with {"email": "kishor+user31@atlogys.com", "password": "testing123"} as user2

Then Verify Email

Then login with user2

Then search all the expert by freetext

Given a tag_id 1

Then search all the expert by tagid

Given a expert_id 50

Then search all the expert by expertid

When login with expert1

Then Create a new Profile as {
                                 "tags": [
                                    1,2,3,4
                                     ],
                                 "medias":[],
                                 "headline": "Solr search",
                                 "summary": "Java Expert",
                                 "my_experience": "In test automation",
                                 "year_of_experience":"3",
                                 "educational_background": "Bachelor of Technology (Computer Science and Information Engineering)"
                             }

Then wait for 5 mintues to update SOLR

Then  login with user2

Then search expert by the newly created expert id