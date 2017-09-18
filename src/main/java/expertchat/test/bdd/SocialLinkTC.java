package expertchat.test.bdd;

// Created by Kishor on 4/25/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.bussinesslogic.SocialLinks;
import expertchat.params.parameter;
import org.jbehave.core.annotations.*;
import static expertchat.usermap.TestUserMap.getMap;

public class SocialLinkTC extends AbstractSteps implements HTTPCode{

    public SocialLinkTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private SocialLinks socialLinks = new SocialLinks();
    @When ("post social links from $social")
    @Then ("post social links from $social")
    @Pending
    public void postSocialLink(@Named ("social") String social) {

        this.info("Posting Social Links..");
        socialLinks.postSocialLink(social);

        this.checkAndWriteToReport(response.statusCode(),
                "Social link posted", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Post Social Link");
    }

    @Then("get the social links")
    public void getSocialLinks() {

        this.info("Getting Social links ");

        if (parameter.isNegative ()) {

            socialLinks.getSocialLinks();

            responseLogger.writeResponseAsLog("Get Social Link");

        } else {

            socialLinks.getSocialLinks();
        }
        this.checkAndWriteToReport(response.statusCode(),
                "Social link get Successful", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Get Social Link");
    }

   /* *//**
     * Adding social link to expert profile
     *//*

    @Then("add social link to expert profile")
    @When("add social link to expert profile")
    public void addSocialLinkToExpertProfile() {

        this.info("Adding social link to expert profile");
        String id = getMap().get("expertProfileId");

        if (parameter.isNegative ()) {

            socialLinks.addLinksToExpertProfile(id);

            responseLogger.writeResponseAsLog("Add social link to expert profile");

        } else {

            socialLinks.addLinksToExpertProfile(expertProfile.getExpertProfileID());

            this.checkAndWriteToReport(response.statusCode(),
                    "Social link added to expert profile->" + expertProfile.getExpertProfileID(), parameter.isNegative ());

            responseLogger.writeResponseAsLog("Add social link to expert profile");
        }
    }

    *//**
     *
     *//*
    @Then("list all social links of a ExpertProfile")
    @When("list all social links of a ExpertProfile")
    public void listAllSocialLinks() {

        this.info("Getting social links linked to expert profile");
        String id = getMap().get("expertProfileId");

        if (parameter.isNegative ()) {

            socialLinks.getLinkedListsOfExpertProfile(id);

        } else {
            socialLinks.getLinkedListsOfExpertProfile(id);

            this.checkAndWriteToReport(response.statusCode(),
                    "Successfully get all links from expert profile id:->" + expertProfile.getExpertProfileID(), parameter.isNegative ());

            responseLogger.writeResponseAsLog("Get social link from expert profile");
        }
    }

    @Then("count of social links added in expert profile should be $count after removing one")
    public void listAllSocialLinksAfterRemove(@Named("count") String count) {

        socialLinks.getLinkedListsOfExpertProfile(expertProfile.getExpertProfileID());

        String countOfSocialLinks = socialLinks.getSocialLinkCount();

        if (count.equals(countOfSocialLinks)) {

            this.AssertAndWriteToReport(true,
                    "count of social links added in expert profile after removing one is" + countOfSocialLinks);
        } else if (!count.equals(countOfSocialLinks) && parameter.isNegative ()) {

            negativeCases();
        }
    }*/


    @Then("add $url as RSS Feed")
    @When("add $media as RSS Feed")
    public void addFeed(@Named("url") String url) {

        this.info("Adding RSS feed ");

        if (parameter.isNegative ()) {

            socialLinks.addFeedsToSocialLink(url);
        } else {
            socialLinks.addFeedsToSocialLink(url);

            this.checkAndWriteToReport(response.statusCode(),
                    "Successfully added RSS feed->", parameter.isNegative ());

            responseLogger.writeResponseAsLog("Add RSS feed");
        }
    }

    @Then("remove one social link")
    @When("remove one social link")
    public void removeSocialLink() {

        boolean isDelete = false;
        this.info("Removing social link");

        if (parameter.isNegative ()) {

            socialLinks.deleteSocialLink(socialLinks.getSocialLinkId().get(0));
        } else {

            isDelete = socialLinks.deleteSocialLink(socialLinks.getSocialLinkId().get(0));
        }

        this.AssertAndWriteToReport(isDelete,
                "Successfully removed social links");
    }

    @Then("count of social links should be $count")
    public void assertCount(@Named("count") String count) {

        this.info("Asserting social link counts");

        String countOfSocialLinks = socialLinks.getSocialLinkCount();

        if (count.equals(countOfSocialLinks)) {

            this.AssertAndWriteToReport(true, "count matched. Count of social link is:" + countOfSocialLinks);

        } else if (!count.equals(countOfSocialLinks) && parameter.isNegative ()) {

            negativeCases();
        }
    }

    @Then("after removing one link,count of social links should be $count")
    public void assertCountAfterRemove(@Named("count") String count) {

        this.info("Asserting social link counts");

        socialLinks.getSocialLinks();

        String countOfSocialLinks = socialLinks.getSocialLinkCount();

        if (count.equals(countOfSocialLinks)) {

            this.AssertAndWriteToReport(true,
                    "after removing one link,count of social link is:" + countOfSocialLinks);
        } else if (!count.equals(countOfSocialLinks) && parameter.isNegative ()) {

            negativeCases();
        }
    }

    @When("add $socialMedia to social Link")
    @Then("add $socialMedia to social Link")
    public void addSocialMedia(@Named("SocialMedia") String SocialMedia) {


    }

    @Then("get all the feeds")
    @When("get all the feeds")
    public void getFeeds() {

        this.info("Listing All the feed ");

        socialLinks.getFeedListing();

        if(response.statusCode()==HTTP_UNAVAILABLE) {

            this.checkAndWriteToReport ( response.statusCode ( ), "Feed listing failed--->" + response.getResponse ( ).prettyPrint ( ), parameter.isNegative ( ) );
            return;
        }

        if (response.statusCode() != HTTP_BAD) {

            socialLinks.setContentID(jsonParser.getJsonData("results[0].id", ResponseDataType.STRING));

            getMap().put("unpublishedContentId", socialLinks.getContentID());

          }
        this.checkAndWriteToReport(response.statusCode(), "Feed listed", parameter.isNegative ());

        responseLogger.writeResponseAsLog("FeedListing API");
    }

    @Then("check count of unpblished feed")
    public void feedCount1() {

        socialLinks.getFeedListing();
        socialLinks.setCountOfFeeds(socialLinks.getSocialLinkCount());

        this.AssertAndWriteToReport(true, "Number of feeds are:->" + socialLinks.getcountOfFeeds());
    }

    @Then("publish a content")
    @When("publish a content")
    @Aliases (values = {"try to publish a ignored content", "publish the same content again"})

    public void publish() {

        info("Publishing a content");

        String cId = getMap().get("unpublishedContentId");

        if (parameter.isNegative ()) {

            socialLinks.publishContent(cId);

        } else {

            socialLinks.publishContent(cId);
        }

        this.checkAndWriteToReport(response.statusCode(), "Content published", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Publish Content API");
    }

    @Then("get all contents")
    public void getcontents() {

        info("get all published contents");

        socialLinks.getContents();

        if (response.statusCode() != HTTP_BAD) {

            socialLinks.setPublishedContentId(jsonParser.getJsonData("results[0].id", ResponseDataType.INT));

            getMap().put("publishedContentID", socialLinks.getPublishedContentId());
        }

        this.checkAndWriteToReport(response.statusCode(), "All published content listed", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Get Published contents");
    }

    @Then("get a particular content")
    public void getContent() {

        info("Listing a particular content");

        String id = getMap().get("publishedContentID");

        if (parameter.isNegative ()) {

            socialLinks.getContent(id);

        } else {
            socialLinks.getContent(id);

            this.checkAndWriteToReport(response.statusCode(), "Content with id->" + socialLinks.getPublishedContentId() + " listed", parameter.isNegative ());

            responseLogger.writeResponseAsLog("Get a particular Published contents");
        }
    }

    @Then("like the content published")
    public void like() {

        String id = getMap ( ).get ( "publishedContentID" );

        socialLinks.like_contemt ( id );

        if ( socialLinks.verify_like ( id ) ) {

            this.checkAndWriteToReport ( response.statusCode ( ), "Content liked by user", parameter.isNegative ( ) );
        }
    }

    @Then("dislike the content published")
    public void dislike() {

        String id = getMap ( ).get ( "publishedContentID" );

        socialLinks.dislike_contemt ( id );

        if ( ! socialLinks.verify_like ( id ) ) {

            this.checkAndWriteToReport ( response.statusCode ( ), "Content disliked by user", parameter.isNegative ( ) );

        }
    }

    @Then("favorite the content published")
    public void favorite() {

        String id = getMap ( ).get ( "publishedContentID" );

        socialLinks.bookmark_contemt ( id );

        if ( socialLinks.verify_bookmark ( id ) ) {

            this.checkAndWriteToReport ( response.statusCode ( ), "Content bookmarked by user", parameter.isNegative ( ) );

        }
    }

    @Then("remove bookmark from the content published")
    public void remove_bookmark(){

        String id = getMap().get("publishedContentID");

        socialLinks.remove_bookmark ( id );

        if(!socialLinks.verify_bookmark ( id )){

        this.checkAndWriteToReport(response.statusCode(), "remove bookmark by user", parameter.isNegative ());
      }
    }


    @Then("delete that content")
    public void deleteContent() {

        info("Deleting a particular content");

        if (parameter.isNegative ()) {

            socialLinks.deleteContent(
                    getMap().get("publishedContentID"));
        } else {

            boolean isDeleted = socialLinks.deleteContent(
                    getMap().get("publishedContentID"));

            socialLinks.getContent(socialLinks.getPublishedContentId());

            this.AssertAndWriteToReport(isDeleted, "Content with id" + socialLinks.getPublishedContentId() + "deleted");
        }
    }

    @Then("ignore a content")
    @Alias("Ignore the same content again")
    public void ignore() {

        info("Ignore a content");
        info("Refresh the feeds again");
        getFeeds();

        if (parameter.isNegative ()) {

            socialLinks.ignoreContent(getMap().get("unpublishedContentId"));

        } else {

            socialLinks.ignoreContent(getMap().get("unpublishedContentId"));
        }

        this.checkAndWriteToReport(response.statusCode(), "Content Ignored", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Ignore Content API");
    }

    @Then("check count of unpblished feed again")
    public void feedCount2() {

        socialLinks.getFeedListing();

        socialLinks.setCountOfFeeds(socialLinks.getSocialLinkCount());

        this.AssertAndWriteToReport(true, "Number of unpublished feeds are:->" + socialLinks.getcountOfFeeds());
    }


}
