package expertchat.test.bdd;// Created by Kishor on 4/25/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.bussinesslogic.Calender;
import expertchat.bussinesslogic.ExpertProfile;
import expertchat.bussinesslogic.FileUpload;
import expertchat.params.Credentials;
import expertchat.params.parameter;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static expertchat.usermap.TestUserMap.getMap;

public class ExpertProfileTC extends AbstractSteps{

    public ExpertProfileTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private ExpertProfile expertProfile = new ExpertProfile();
    private Credentials credentials=Credentials.getCredentials ();
    private Calender calender=new Calender ();

    /**
     * @param profile
     */
    @Then ("expert should be able to post expert profile as $profile")
    @When ("expert should be able to post expert profile as $profile")
    @Aliases (values = {"Create a new Profile as $profile",
            "try to create expert profile as $profile"})

    public void postExpertProfile(@Named ("profile") String profile) {

        this.info("Post Expert Profile");

        if (parameter.isNegative ()) {

            expertProfile.addExpertyProfile(profile);

            responseLogger.writeResponseAsLog("Expert profile");

        } else {

            expertProfile.addExpertyProfile(profile);
        }

        this.checkAndWriteToReport(response.statusCode(),
                "Expert Profile Created", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Expert profile");
    }

    /**
     * @param json
     */
    @Then("update information on expert profile as $json")
    public void updateProfile(@Named("json") String json) {

        this.info("Updating  Expert Profile");

        if (parameter.isNegative ()) {

            expertProfile.updateExpertProfile(json, getMap().get("expertProfileId"));
            responseLogger.writeResponseAsLog("Update Expert Profile");

        } else {

            expertProfile.updateExpertProfile(json, getMap().get("expertProfileId"));
            responseLogger.writeResponseAsLog("Update Expert Profile");

        }

        this.checkAndWriteToReport(response.statusCode(),
                "Expert Profile updated with--" + json, parameter.isNegative ());

        responseLogger.writeResponseAsLog("Update Expert Profile");
    }

    /**
     *
     */
    @Then("get profile")
    @When("get profile")
    @Aliases(values = {"get the profile",
            "get the previously created expert profile", "get expert profile"})

    public void getProfile() {

        this.info("GET Expert Profile");

        //  String expertProfileID = getMap ( ).get ( "expertProfileId" );

        if (parameter.isNegative ()) {

            expertProfile.getProfileOfExpert("", parameter.isExpert ());

        } else {

            expertProfile.getProfileOfExpert("", parameter.isExpert ());
        }

        if (parameter.isExpert () == false) {

            this.checkAndWriteToReport(response.statusCode(),
                    "Expert profile loaded by a user--" + credentials.getUserCredential()[0], parameter.isNegative ());
        } else {

            this.checkAndWriteToReport(response.statusCode(),
                    "Expert profile loaded by expert--" + credentials.getExpertCredential()[0], parameter.isNegative ());
        }
    }

    /**
     * @param
     */
    @Then("get profile of expert")
    public void getProfileWithID() {

        this.info("GET Expert Profile");

        String expertProfileID = getMap().get("expertProfileId");

        if (parameter.isNegative ()) {

            expertProfile.getProfileOfExpert(expertProfileID, parameter.isExpert ());

            responseLogger.writeResponseAsLog("Get Expert Profile-Negative");

        } else {

            expertProfile.getProfileOfExpert(expertProfileID, parameter.isExpert ());
        }

        this.checkAndWriteToReport(response.statusCode(),
                "Get Profile successfully", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Get Expert Profile");
    }


    @Then("upload media as $mediaPath")
    public void addMedia(@Named("mediaPath") String mediaPath) {

        info("Uploading a file..:" + mediaPath);

        expertProfile.uploadMedia(mediaPath, parameter.isExpert ());

        if (!expertProfile.getResponseOfMediaUpload().contains("errors")) {

            this.checkAndWriteToReport( HTTPCode.HTTP_OK,
                    "Media Uploaded--::" + expertProfile.getResponseOfMediaUpload(), parameter.isNegative ());

        } else if (expertProfile.getResponseOfMediaUpload().contains("errors") && parameter.isNegative ()) {

            this.checkAndWriteToReport(HTTPCode.HTTP_BAD,
                    "Negative Test passed-::" + expertProfile.getResponseOfMediaUpload(), true);

        } else if (expertProfile.getResponseOfMediaUpload().contains("errors") && parameter.isNegative () == false) {

            this.checkAndWriteToReport(HTTPCode.HTTP_BAD,
                    "Something Went Wrong-::" + expertProfile.getResponseOfMediaUpload(), false);
        } else if ( FileUpload.ERROR) {

            this.checkAndWriteToReport(HTTPCode.HTTP_BAD,
                    "Media Upload failed--::" + expertProfile.getResponseOfMediaUpload(), false);
        }

        responseLogger.writeResponseAsLog("Upload Media");
    }

    /**
     * Deleting an Expert profile
     **/
    @Then("delete the profile")
    public void deleteProfile() {

        this.info("Deleting Expert Profile");

        boolean isDelete = false;

        if (parameter.isNegative ()) {

            expertProfile.deleteProfile(expertProfile.getExpertProfileID());

        } else {

            isDelete = expertProfile.deleteProfile(expertProfile.getExpertProfileID());
        }
        this.AssertAndWriteToReport(isDelete,
                "Expert profile deleted");


    }

    /**
     * @param id
     */
    @Then("delete the profile with id $id")
    public void deleteProfile(@Named("id") String id) {

        this.info("Delete Expert Profile");

        boolean isDelete = false;

        if (parameter.isNegative ()) {

            expertProfile.deleteProfile(id);

        } else {

            isDelete = expertProfile.deleteProfile(id);
        }
        this.AssertAndWriteToReport(isDelete,
                "Profile deleted with id->" + id);
    }

    @Then("create a calender as $json")
    public void calender(@Named("json") String json) throws Exception {

        info("Creating a calender...");
        if (parameter.isNegative ()) {
            calender.createCalender(20);
        } else {
            calender.createCalender(20);
        }
        checkAndWriteToReport(response.statusCode(),"Calender Created", parameter.isNegative ());
        responseLogger.writeResponseAsLog("Calender API");
    }

    @Then("get the calender")
    @When("get the calender")
    @Aliases(values = {"get the calender again"})
    public void getCalender(){

        info("Get the calender...");
        calender.getCalender(calender.getCalenderId());
        checkAndWriteToReport(response.statusCode(),
                "Calender with id->"+calender.getCalenderId()+"\tloaded", parameter.isNegative ());
        responseLogger.writeResponseAsLog("Get CAlender");
    }

    @Then("update the calender as $json")
    public void updateCalender(@Named("json") String json) {

        info("Updating a calender...");

        if (parameter.isNegative ()) {

            calender.updateCalender(json, calender.getCalenderId());
        } else {

            calender.updateCalender(json, calender.getCalenderId());
        }

        checkAndWriteToReport(response.statusCode(),"Calender with id->" + calender.getCalenderId() + "\t updated", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Calender API");
    }

    @Then("get the avilable slot of expert1")
    public void getAvilableSlots(){
        info("Listing all the avilable slots of expert1");

        expertProfile.getProfileOfExpert ( "",parameter.isExpert () );

        String eId=getMap().get("expertId");

        calender.getAvilableSlot(eId);

        checkAndWriteToReport(response.statusCode(),"All available slot listed", parameter.isNegative ());

        responseLogger.writeResponseAsLog("GET Available slots");
    }

}
