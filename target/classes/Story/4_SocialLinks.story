                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description
Given complete Social link flow
Given an expert
When add {"url": "https://staff.tumblr.com/rss/"} as RSS Feed

When add {"url": "http://feeds.feedburner.com/ndtvnews-world-news"} as RSS Feed

When add facebook to social Link

When add instagram to social Link

When add youtube to social Link

Then get the social links

Then count of social links should be 2

Then get all the feeds

Then check count of unpblished feed

When publish a content

Then check count of unpblished feed again

Given negative scenario
When publish the same content again
Then check non-field error code 1055
Then check count of unpblished feed again

Then get all contents

Then get a particular content

Then delete that content

Then ignore a content

Then Ignore the same content again

Then try to publish a ignored content

Then check count of unpblished feed again

When remove one social link

Then after removing one link,count of social links should be 1



