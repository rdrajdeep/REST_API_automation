Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description
Given complete Social link flow
Given an expert
When login with expert1
Then Create a new Profile as {
                                 "tags": [
                                     ],
                                  "medias":[],
                                 "headline": "Java-8 Lambda",
                                 "summary": "Java Expert",
                                 "my_experience": "In test automation",
                                 "year_of_experience":"3",
                                 "educational_background": "Bachelor of Technology (Computer Science and Information Engineering)"
                             }

When add {"url": "https://staff.tumblr.com/rss/"} as RSS Feed


When add {"url": "http://feeds.feedburner.com/ndtvnews-world-news"} as RSS Feed


When add facebook to social Link

When add instagram to social Link

When add youtube to social Link

Then get the social links

Then count of social links should be 2

Then add social link to expert profile

When list all social links of a ExpertProfile

Then count of social links should be 2

Then get all the feeds

Then check count of unpblished feed

When publish a content

Then check count of unpblished feed again

Given negative scenario

When publish the same content again

Then check non-field error code 1055

Then get all contents

Then get a particular content

Then delete that content

Then ignore a content

Then Ignore the same content again

Then try to publish a ignored content

Then check count of unpblished feed again

When remove one social link

Then after removing one link,count of social links should be 1

Then count of social links added in expert profile should be 1 after removing one



