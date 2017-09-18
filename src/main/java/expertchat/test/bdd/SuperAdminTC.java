package expertchat.test.bdd;// Created by Kishor on 4/25/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.bussinesslogic.SuperAdmin;
import expertchat.params.parameter;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static expertchat.usermap.TestUserMap.getMap;

public class SuperAdminTC  extends AbstractSteps{

    public SuperAdminTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private SuperAdmin superAdmin = new SuperAdmin();

    /**
     * Super Admin test cases
     */

    @Then ("create a super admin content as $json")
    public void createSuperAdminContent(@Named ("json") String json) {

        info("Creating super admin content as--" + json);

        if ( parameter.isNegative ( )) {

            superAdmin.createContent(json);

        } else {

            superAdmin.createContent(json);
        }

        this.checkAndWriteToReport(response.statusCode(), "Content with id\t" + superAdmin.getContentId() + " created", parameter.isNegative ( ));

        responseLogger.writeResponseAsLog("Create a super admin content");
    }


    @Then("$action the content")
    @Alias ("$action the content again")
    public void actionOnContent(@Named("action") String action) {

        String cID = getMap().get("superAdminContentId");
        switch (action.toLowerCase()) {

            case "get":
                info(action + " the super Admin content");

                superAdmin.getContent(cID);

                this.checkAndWriteToReport(response.statusCode(), "Content\t" + superAdmin.getContentId() + "listed", parameter.isNegative ( ));

                responseLogger.writeResponseAsLog("Super Admin Content");
                break;

            case "get all":
                info(action + " the super Admin content");

                superAdmin.getContent("");

                this.checkAndWriteToReport(response.statusCode(), "Content listed", parameter.isNegative ( ));

                responseLogger.writeResponseAsLog("Super Admin Content");
                break;

            case "delete":
                info(action + " the super Admin content");

                boolean isDelete = superAdmin.deleteContent(cID);

                if (isDelete) {
                    this.AssertAndWriteToReport(isDelete, "Content with id\t" + superAdmin.getContentId() + " deleted");
                }
                break;

            case "unhide":
                info(action + " the super Admin content");

                boolean isUnhide = superAdmin.unhide(cID);

                if (isUnhide) {
                    this.AssertAndWriteToReport(isUnhide, "Content with id\t" + superAdmin.getContentId() + " get visible again");
                }
                break;
        }
    }

    @Then("check the count of $content")
    public void checkSuperAdminContentCount(@Named("content") String content) {

        info("checking the count of\t" + content);

        superAdmin.getContent("");

        this.checkAndWriteToReport(response.statusCode(),
                "count of content is--" + superAdmin.getCountOfContent(), parameter.isNegative ( ));
    }

    @Then("update the content as $json")
    @When ("update the content as $json")
    public void updateContent(@Named("json") String json) {

        info("updating the previously created content as--" + json);

        String cID = getMap().get("superAdminContentId");

        if (parameter.isNegative ( )) {

            superAdmin.updateContent(json, cID);

        } else {

            superAdmin.updateContent(json, cID);
        }
        this.checkAndWriteToReport(response.statusCode(), "Content with id\t" + cID + " updated", parameter.isNegative ( ));

        responseLogger.writeResponseAsLog("Update Content");
    }

}
