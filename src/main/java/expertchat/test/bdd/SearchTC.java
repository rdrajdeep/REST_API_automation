package expertchat.test.bdd;// Created by Kishor on 4/25/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.bussinesslogic.Searching;
import expertchat.params.parameter;
import expertchat.util.ExpertChatUtility;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;

import static expertchat.usermap.TestUserMap.getMap;

public class SearchTC  extends AbstractSteps{

    public SearchTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private Searching searching = new Searching();

    @Given ("a $type $text")
    public void getText(@Named ("text") String text,
                        @Named("type") String type) {

        if (text.isEmpty() && type.equals("tag_id")) {


            searching.setTagId(text);
        }

        switch (type.toLowerCase()) {

            case "text":
                searching.setSearchText(text);
                break;

            case "tag_id":
                searching.setTagId(text);
                break;

            case "expert_id":
                searching.setExpertId(text);
                break;
        }
    }

    @Then ("search all the expert by $by")
    public void searchExpert(@Named("by") String by) {

        info("Searching through " + by);

        switch (by.toLowerCase()) {

            case "freetext":
                String searchText = searching.getSearchText();

                if ( parameter.isNegative ()) {
                    searching.search(searchText, Searching.SearchType.BY_TEXT, true);
                } else {
                    searching.search(searchText, Searching.SearchType.BY_TEXT, true);
                }
                this.checkAndWriteToReport(response.statusCode(), "Expert Related to text-> " + searchText + " has been loaded", parameter.isNegative ());

                responseLogger.writeResponseAsLog("Search by FreeText");
                break;

            case "tagid":
                String tId = searching.getTagId();
                if (parameter.isNegative ()) {
                    searching.search(tId, Searching.SearchType.BY_TAG, true);
                } else {

                    searching.search(tId, Searching.SearchType.BY_TAG, true);
                }
                this.checkAndWriteToReport(response.statusCode(), "Expert Related to tagID-> " + tId + " has been loaded", parameter.isNegative ());

                responseLogger.writeResponseAsLog("Search by TagId");
                break;

            case "expertid":

                String eId = searching.getExpertId();

                System.out.println(searching.getExpertId() + "==========" + parameter.isNegative ());

                if (parameter.isNegative ()) {

                    searching.search(eId, Searching.SearchType.BY_ID, true);
                } else {

                    searching.search(eId, Searching.SearchType.BY_ID, true);
                }
                this.checkAndWriteToReport(response.statusCode(), "Expert Related to ExpertId-> " + eId + " has been loaded", parameter.isNegative ());

                responseLogger.writeResponseAsLog("Search by ExpertId");
                break;

        }

    }

    @Then("search $expert")
    public void searchByDynamicExpertID(@Named ( "expert" )String expert) {

        info("Dynamic Expert id Search");

        String expertID = getMap().get("expertProfileId");

        if(expert.contains ( "anonymously" )){

            info("Searching Expert anonymously");

            searching.search(expertID, Searching.SearchType.BY_ID, true);
        }else {

            searching.search(expertID, Searching.SearchType.BY_ID, false);
        }

        if (searching.verifyExpertInResult(expertID)) {

            this.AssertAndWriteToReport(true, "Newly created Expert found in the result");
        }
    }


    @Then("wait for 5 mintues to update SOLR")
    public void waitFor5mint() {

        info("wait for 5 mintues to update SOLR");

        System.out.println("Waiting.....");

        ExpertChatUtility.delay();
    }

}
