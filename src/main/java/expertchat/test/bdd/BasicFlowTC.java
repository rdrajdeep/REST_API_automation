package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.bussinesslogic.ExpertChatApi;
import expertchat.usermap.TestUserMap;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import expertchat.params.*;

public class BasicFlowTC extends AbstractSteps{

    public BasicFlowTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private ExpertChatApi expertChatApi = new ExpertChatApi();
    private Credentials credentials=Credentials.getCredentials ();

    /**
     * @param json
     * @param name
     */
    @When ("register with $json as $name")
    public void register(@Named ("json") String json,
                         @Named("name") String name) {

        info("Registration");
        if (parameter.isExpert ()) {

            if (parameter.isNegative()) {

                expertChatApi.doRegistration(json, true);

                checkAndWriteToReport(response.statusCode(), "", true);
                return;
            }

            expertChatApi.doRegistration(json, true);

            checkAndWriteToReport(response.statusCode(), "Expert--" + json + "-- registered", parameter.isNegative ());

            TestUserMap.setTestData(name, json);

        } else if (parameter.isExpert() == false) {

            if (parameter.isNegative()) {

                expertChatApi.doRegistration(json, false);

                checkAndWriteToReport(response.statusCode(), "", true);
                return;
            }

            expertChatApi.doRegistration(json, false);

            checkAndWriteToReport(response.statusCode(), "User--" + json + "-- registered", parameter.isNegative());

            TestUserMap.setTestData(name, json);

        }

        if (ExpertChatApi.REGISTRATION_ERROR) {

            fatal(jsonParser.getJsonData("errors.email[0].message", ResponseDataType.STRING));

            System.out.println(jsonParser.getJsonData("errors.email[0].message", ResponseDataType.STRING));

        }

        responseLogger.writeResponseAsLog("Registration");
    }


    /**
     * Email verification
     */
    @Then ("Verify Email")
    public void verifyUser() {

        info("Verify email Address");

        expertChatApi.verifyUser();

        this.checkAndWriteToReport(response.statusCode(), "Email Verified", parameter.isNegative ());

    }

    /**
     * @param user
     */
    @When("login with $user")
    @Then("login with $user")
    public void login(@Named("user") String user) {

        info("Login");

        if (user.contains("{") && parameter.isExpert ()) {

            expertChatApi.doLogIn(user, true);

            credentials.setExpertCredential(user);

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by expert--" + user, parameter.isNegative ());

        } else if (user.contains("{") && parameter.isExpert () == false) {

            expertChatApi.doLogIn(user, false);

            credentials.setuserCredential(user);

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by user--" + user, parameter.isNegative ());

        } else if (user.contains("user")) {

            expertChatApi.doLogIn(TestUserMap.getUserCredentialsByKey(user), false);

            credentials.setuserCredential(TestUserMap.getUserCredentialsByKey(user));

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by user--" +
                    TestUserMap.getUserCredentialsByKey(user), parameter.isNegative ());


        } else {
            expertChatApi.doLogIn(TestUserMap.getUserCredentialsByKey(user), true);

            credentials.setExpertCredential(TestUserMap.getUserCredentialsByKey(user));

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by expert--" +
                    TestUserMap.getUserCredentialsByKey(user), parameter.isNegative ());
        }

        responseLogger.writeResponseAsLog("Login API");
    }

    /**
     * @param state
     * @param password
     * @param user
     */
    @Then("$state password to $password for $user")
    @When("$state password to $password for $user")
    public void change_or_reset_password(@Named("state") String state,
                                         @Named("password") String password,
                                         @Named("user") String user) {

        switch (state.toLowerCase()) {

            case "change":

                this.info("Changing Password");

                if (parameter.isNegative ()) {

                    expertChatApi.changePassword(password, user, parameter.isExpert ());

                    responseLogger.writeResponseAsLog("Change Password");

                } else {
                    expertChatApi.changePassword(password, user, parameter.isExpert ());

                    this.checkAndWriteToReport(response.statusCode(),
                            "Password has been changed", parameter.isNegative ());

                    responseLogger.writeResponseAsLog("Change Password");
                    break;
                }
            case "reset":

                info("Resetting password to--" + password);

                if (parameter.isNegative ()) {

                    expertChatApi.resetPassword(password, user, parameter.isExpert ());

                    responseLogger.writeResponseAsLog("Reset Password");

                } else {
                    expertChatApi.resetPassword(password, user, parameter.isExpert ());

                    this.checkAndWriteToReport(response.statusCode(),
                            "Password has been Reset", parameter.isNegative ());

                    responseLogger.writeResponseAsLog("Reset Password");
                    break;
                }

        }
    }
    @Then("Resend $email for $json")
    @When("Resend $email for $json")
    public void resendEmailVerification(@Named("json") String json) {

        this.info("Resend Verification Information");

        if (parameter.isNegative ()) {

            expertChatApi.resendEmailVerification(json, parameter.isExpert ());

            responseLogger.writeResponseAsLog("Resend Email Verification");

        } else {
            expertChatApi.resendEmailVerification(json, parameter.isExpert ());

            this.checkAndWriteToReport(response.statusCode(),
                    "Resend email Verification", parameter.isNegative ());

            responseLogger.writeResponseAsLog("Resend Email Verification");
        }
    }


}
