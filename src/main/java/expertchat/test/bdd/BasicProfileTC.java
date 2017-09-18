package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.bussinesslogic.BasicProfile;
import expertchat.params.parameter;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;

import java.io.IOException;

public class BasicProfileTC extends AbstractSteps implements HTTPCode{

    public BasicProfileTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private BasicProfile basicProfile = new BasicProfile ();


    /**
     * @param user
     */
    @Then ("Load basic profile of $user")
    public void loadBasicProfile(@Named ("user") String user) {

        this.info("Load basic profile of -" + user);

        basicProfile.loadBasicProfile(parameter.isExpert ());

        this.checkAndWriteToReport(response.statusCode(),
                "Basic profile loaded", parameter.isNegative ());

        responseLogger.writeResponseAsLog("My Info Load");

    }

    /**
     * @param name
     */
    @Then("add name as $name")
    @Aliases (values = {"update basic profile as $name"})
    public void addName(@Named("name") String name) {

        this.info("Add name to basic profile");
        String uName = "";

        if (parameter.isNegative ()) {

            basicProfile.addName(name, parameter.isExpert ());

            responseLogger.writeResponseAsLog("Add name to basic info");

        } else {

            basicProfile.addName(name, parameter.isExpert ());

            if (response.statusCode() != HTTP_BAD) {

                uName = jsonParser.getJsonData("results.name", ResponseDataType.STRING);
            }

            this.checkAndWriteToReport(response.statusCode(),
                    "Name added to profile--" + uName, parameter.isNegative ());

            responseLogger.writeResponseAsLog("Add name to basic info");

        }
    }

    @Then("add profile photo as $image")
    public void addPhotoToProfile(@Named("image") String image) {

        String imageUrl = "";
        this.info("Adding profile image..");

        try {

            if (parameter.isNegative ()) {

                basicProfile.addProfilePhoto(image, parameter.isExpert ());

                responseLogger.writeResponseAsLog("Me-Photo");
            } else {

                basicProfile.addProfilePhoto(image, parameter.isExpert ());
            }

        } catch (IOException e) {

            this.info(e.getMessage());

        } finally {

            if (response.statusCode() == HTTP_ACCEPTED || response.statusCode() == HTTP_OK) {

                imageUrl = jsonParser.getJsonData("results.image_url", ResponseDataType.STRING);
            }

            this.checkAndWriteToReport(response.statusCode(),
                    "Profile Image Added--" + imageUrl, parameter.isNegative ());

            responseLogger.writeResponseAsLog("Me-Photo");
        }

    }


    @Then("verify profile photo of $user")
    public void verifyProfilePhoto(@Named("user") String user) {

        this.info("Verifying profile photo");

        String profilePhoto = "";

        basicProfile.loadBasicProfile( parameter.isExpert ());

        if (response.statusCode() != HTTP_BAD) {

            profilePhoto = jsonParser.getJsonData("results.profile_photo", ResponseDataType.STRING);
        }

        this.AssertAndWriteToReport(profilePhoto.contains("png"),
                "Profile Photo Verified");

    }

    @Then("verify email of the profile is same as $user")
    public void verifyEmailOfUser(@Named("user") String user) {

        this.info("Verify a Email of user-" + user);

        this.AssertAndWriteToReport(basicProfile.verifyProfileEmail(user),
                "Email is same as login user");
    }

}
