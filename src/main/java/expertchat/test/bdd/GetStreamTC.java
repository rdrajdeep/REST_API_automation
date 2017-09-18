package expertchat.test.bdd;// Created by Kishor on 4/25/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.bussinesslogic.GetStreamFeeds;
import expertchat.params.parameter;
import expertchat.util.ExpertChatException;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;

import static expertchat.usermap.TestUserMap.getMap;

public class GetStreamTC  extends AbstractSteps{

    public GetStreamTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private GetStreamFeeds getStreamFeeds = new GetStreamFeeds();

    /**
     * GET STREAM API
     */
    /*********************************************************************/
    @Then ("get the feeds from get stream by $by")
    public void getFeeds(@Named ("by") String by) {

        info("Listing feed from getStream via-->" + by);
        String eProfileId = getMap().get("expertProfileId");

        switch (by.toLowerCase()) {

            case "expertprofileid":
                getStreamFeeds.getFeeds(eProfileId, GetStreamFeeds.By.BY_EXPERTPROFILE);

                responseLogger.writeResponseAsLog("GetStream feed listing by expert Profile id");

                this.checkAndWriteToReport(response.statusCode(), "Feed listed", parameter.isNegative ());
                break;

            case "expertid":
                getStreamFeeds.getFeeds(getMap().get("baseId"), GetStreamFeeds.By.BY_EXPERTID);

                this.checkAndWriteToReport(response.statusCode(), "Feed listed", parameter.isNegative ());

                responseLogger.writeResponseAsLog("GetStream feed listing by expert ID");
                break;

            default:
                throw new ExpertChatException ("No Suitable option found..");
        }

    }

    @Then("get the feeds from get stream using tag $tag")
    public void getFeedsByTag(@Named("tag") String tag) {

        info("Getting feeds by tag--" + tag);

        if (!parameter.isNegative ()) {

            getStreamFeeds.getFeeds(tag, GetStreamFeeds.By.BY_TAGID);

            this.checkAndWriteToReport(response.statusCode(), "Feed listed", parameter.isNegative ());

            responseLogger.writeResponseAsLog("GetStream feed listing by Tag ID--" + tag);
        } else {

            getStreamFeeds.getFeeds(tag, GetStreamFeeds.By.BY_TAGID);

            this.checkAndWriteToReport(response.statusCode(), "Feed listed", parameter.isNegative ());

            responseLogger.writeResponseAsLog("GetStream feed listing by Tag ID--" + tag);
        }
    }

    @Then("verify the tags")
    @Alias ("verify the tags again")
    public void verify() {

        info("Verifying the tags");

        info("Super Admin tags..." + getMap().get("su-stringOfTags"));

        info("get stream tags..." + getMap().get("gs-stringOfTags"));

        if (getMap().get("su-stringOfTags").equals(getMap().get("gs-stringOfTags"))) {

            this.AssertAndWriteToReport(true, "tags are found same");

        } else if (!getMap().get("getStreamId").equals(getMap().get("superAdminContentId")) && parameter.isNegative ()) {

            this.AssertAndWriteToReport(true, "Negative Test Passed.Content id--" +

                    getMap().get("superAdminContentId") + "\t not found while searching on getStream via tag API");
        } else {

            info("Something went wrong");
        }
    }
}
