/*
package bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.bussinesslogic.*;
import expertchat.usermap.TestUserMap;
import expertchat.util.ExpertChatException;
import expertchat.util.ExpertChatUtility;
import org.jbehave.core.annotations.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import static expertchat.usermap.TestUserMap.getMap;



public class E2ETestCase extends AbstractSteps implements HTTPCode {


    public static boolean isNegative = false;

    private static boolean isExpert = true;
    private ExpertChatApi expertChatApi = new ExpertChatApi();

    private ExpertProfile expertProfile = new ExpertProfile();
    private BasicProfile basicProfile = new BasicProfile ();
    private Calling call = new Calling();
    private PhoneVerification phone = new PhoneVerification();
    private SocialLinks socialLinks = new SocialLinks();
    private Searching searching = new Searching();
    private SuperAdmin superAdmin = new SuperAdmin();
    private Account account = new Account();
    private ProfileComplete profileComplete = new ProfileComplete();
    private GetStreamFeeds getStreamFeeds = new GetStreamFeeds();
    private Calender calender = new Calender();
    private MeStats stats=new MeStats ();

    public E2ETestCase(ExtentReports reports, String casName) {

        super(reports, casName);
    }

    @Given("complete $flow")
    public void flow(@Named("flow") String flow) {

        info("...." + flow + "....");
    }

    @Given("an user")
    @Then("an user")
    @When("an user")
    public void user() {
        isExpert = false;
    }

    @Given("an expert")
    @Then("an expert")
    @When("an expert")
    public void expert() {
        isExpert = true;
    }

    @Given("negative scenario")
    public void negative() {
        isNegative = true;
    }

    */
/*log out from the system*//*

    @Then("logout")
    @When("logout")
    @Aliases ( values = {"logout the expert","logout the user"})
    public void logout() {

        SessionManagement.session().setToken(null);

        if (SessionManagement.session().getToken() == null) {

            info("Logout from the system");
        }
    }


    */
/**
     * @param json
     * @param name
     *//*

    @When("register with $json as $name")
    public void register(@Named("json") String json,
                         @Named("name") String name) {

        info("Registration");
        if (isExpert) {

            if (isNegative) {

                expertChatApi.doRegistration(json, true);

                checkAndWriteToReport(response.statusCode(), "", true);
                return;
            }

            expertChatApi.doRegistration(json, true);

            checkAndWriteToReport(response.statusCode(), "Expert--" + json + "-- registered", isNegative);

            TestUserMap.setTestData(name, json);

        } else if (isExpert == false) {

            if (isNegative) {

                expertChatApi.doRegistration(json, false);

                checkAndWriteToReport(response.statusCode(), "", true);
                return;
            }

            expertChatApi.doRegistration(json, false);

            checkAndWriteToReport(response.statusCode(), "User--" + json + "-- registered", isNegative);

            TestUserMap.setTestData(name, json);

        }

        if (ExpertChatApi.REGISTRATION_ERROR) {

            fatal(jsonParser.getJsonData("errors.email[0].message", ResponseDataType.STRING));

            System.out.println(jsonParser.getJsonData("errors.email[0].message", ResponseDataType.STRING));

        }

        responseLogger.writeResponseAsLog("Registration");
    }


    */
/**
     * Email verification
     *//*

    @Then("Verify Email")
    public void verifyUser() {

        info("Verify email Address");

        expertChatApi.verifyUser();

        this.checkAndWriteToReport(response.statusCode(), "Email Verified", isNegative);

    }

    */
/**
     * @param user
     *//*

    @When("login with $user")
    @Then("login with $user")
    public void login(@Named("user") String user) {

        info("Login");

        if (user.contains("{") && isExpert) {

            expertChatApi.doLogIn(user, true);

            expertProfile.setExpertCredential(user);

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by expert--" + user, isNegative);

        } else if (user.contains("{") && isExpert == false) {

            expertChatApi.doLogIn(user, false);

            expertProfile.setuserCredential(user);

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by user--" + user, isNegative);

        } else if (user.contains("user")) {

            expertChatApi.doLogIn(TestUserMap.getUserCredentialsByKey(user), false);

            expertProfile.setuserCredential(TestUserMap.getUserCredentialsByKey(user));

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by user--" +
                    TestUserMap.getUserCredentialsByKey(user), isNegative);


        } else {
            expertChatApi.doLogIn(TestUserMap.getUserCredentialsByKey(user), true);

            expertProfile.setExpertCredential(TestUserMap.getUserCredentialsByKey(user));

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by expert--" +
                    TestUserMap.getUserCredentialsByKey(user), isNegative);
        }

        responseLogger.writeResponseAsLog("Login API");
    }

    */
/**
     * @param state
     * @param password
     * @param user
     *//*

    @Then("$state password to $password for $user")
    @When("$state password to $password for $user")
    public void change_or_reset_password(@Named("state") String state,
                                         @Named("password") String password,
                                         @Named("user") String user) {

        switch (state.toLowerCase()) {

            case "change":

                this.info("Changing Password");

                if (isNegative) {

                    expertChatApi.changePassword(password, user, isExpert);

                    responseLogger.writeResponseAsLog("Change Password");

                } else {
                    expertChatApi.changePassword(password, user, isExpert);

                    this.checkAndWriteToReport(response.statusCode(),
                            "Password has been changed", isNegative);

                    responseLogger.writeResponseAsLog("Change Password");
                    break;
                }
            case "reset":

                info("Resetting password to--" + password);

                if (isNegative) {

                    expertChatApi.resetPassword(password, user, isExpert);

                    responseLogger.writeResponseAsLog("Reset Password");

                } else {
                    expertChatApi.resetPassword(password, user, isExpert);

                    this.checkAndWriteToReport(response.statusCode(),
                            "Password has been Reset", isNegative);

                    responseLogger.writeResponseAsLog("Reset Password");
                    break;
                }

        }
    }


    */
/**
     * @param code
     *//*

    @Then("check error code $code")
    public void check_error_code(@Named("code") String code) {

        this.checkErrorCode(jsonParser.serverStatusCode(), code);
    }

    */
/**
     * @param code
     *//*

    @Then("check non-field error code $code")
    public void check_non_filed_error_code(@Named("code") String code) {

        this.checkNonFiledError(response.statusCode(),
                jsonParser.getNonFieldErrorCode(), code);
    }


    */
/**
     * @param code
     *//*

    @Then("check success code $code")
    public void check_success_code(@Named("code") String code) {

        this.checkSuccessCode(response.statusCode(), jsonParser.getSuccessCode(), code, false);
    }


    */
/**
     * @param json
     *//*

    @Then("Resend $email for $json")
    @When("Resend $email for $json")
    public void resendEmailVerification(@Named("json") String json) {

        this.info("Resend Verification Information");

        if (isNegative) {

            expertChatApi.resendEmailVerification(json, isExpert);

            responseLogger.writeResponseAsLog("Resend Email Verification");

        } else {
            expertChatApi.resendEmailVerification(json, isExpert);

            this.checkAndWriteToReport(response.statusCode(),
                    "Resend email Verification", isNegative);

            responseLogger.writeResponseAsLog("Resend Email Verification");
        }
    }
    */
/*Basic Profile Load Test Cases*//*


    */
/**
     * @param user
     *//*

    @Then("Load basic profile of $user")
    public void loadBasicProfile(@Named("user") String user) {

        this.info("Load basic profile of -" + user);

        basicProfile.loadBasicProfile(isExpert);

        this.checkAndWriteToReport(response.statusCode(),
                "Basic profile loaded", isNegative);

        responseLogger.writeResponseAsLog("My Info Load");

    }

    */
/**
     * @param user
     *//*

    @Then("verify email of the profile is same as $user")
    public void verifyEmailOfUser(@Named("user") String user) {

        this.info("Verify a Email of user-" + user);

        this.AssertAndWriteToReport(basicProfile.verifyProfileEmail(user),
                "Email is same as login user");
    }

    */
/**
     * @param name
     *//*

    @Then("add name as $name")
    @Aliases(values = {"update basic profile as $name"})
    public void addName(@Named("name") String name) {

        this.info("Add name to basic profile");
        String uName = "";

        if (isNegative) {

            basicProfile.addName(name, isExpert);

            responseLogger.writeResponseAsLog("Add name to basic info");

        } else {

            basicProfile.addName(name, isExpert);

            if (response.statusCode() != HTTP_BAD) {

                uName = jsonParser.getJsonData("results.name", ResponseDataType.STRING);
            }

            this.checkAndWriteToReport(response.statusCode(),
                    "Name added to profile--" + uName, isNegative);

            responseLogger.writeResponseAsLog("Add name to basic info");

        }
    }

    @Then("add profile photo as $image")
    public void addPhotoToProfile(@Named("image") String image) {

        String imageUrl = "";
        this.info("Adding profile image..");

        try {

            if (isNegative) {

                basicProfile.addProfilePhoto(image, isExpert);

                responseLogger.writeResponseAsLog("Me-Photo");
            } else {

                basicProfile.addProfilePhoto(image, isExpert);
            }

        } catch (IOException e) {

            this.info(e.getMessage());

        } finally {

            if (response.statusCode() == HTTP_ACCEPTED || response.statusCode() == HTTP_OK) {

                imageUrl = jsonParser.getJsonData("results.image_url", ResponseDataType.STRING);
            }

            this.checkAndWriteToReport(response.statusCode(),
                    "Profile Image Added--" + imageUrl, isNegative);

            responseLogger.writeResponseAsLog("Me-Photo");
        }

    }


    @Then("verify profile photo of $user")
    public void verifyProfilePhoto(@Named("user") String user) {

        this.info("Verifying profile photo");

        String profilePhoto = "";

        basicProfile.loadBasicProfile(isExpert);

        if (response.statusCode() != HTTP_BAD) {

            profilePhoto = jsonParser.getJsonData("results.profile_photo", ResponseDataType.STRING);
        }

        this.AssertAndWriteToReport(profilePhoto.contains("png"),
                "Profile Photo Verified");

    }

    */
/*EXPERT PROFILE TEST CASES*//*


    */
/**
     * @param profile
     *//*

    @Then("expert should be able to post expert profile as $profile")
    @When("expert should be able to post expert profile as $profile")
    @Aliases(values = {"Create a new Profile as $profile",
            "try to create expert profile as $profile"})

    public void postExpertProfile(@Named("profile") String profile) {

        this.info("Post Expert Profile");

        if (isNegative) {

            expertProfile.addExpertyProfile(profile);

            responseLogger.writeResponseAsLog("Expert profile");

        } else {

            expertProfile.addExpertyProfile(profile);
        }

        this.checkAndWriteToReport(response.statusCode(),
                "Expert Profile Created", isNegative);

        responseLogger.writeResponseAsLog("Expert profile");
    }

    */
/**
     * @param json
     *//*

    @Then("update information on expert profile as $json")
    public void updateProfile(@Named("json") String json) {

        this.info("Updating  Expert Profile");

        if (isNegative) {

            expertProfile.updateExpertProfile(json, getMap().get("expertProfileId"));
            responseLogger.writeResponseAsLog("Update Expert Profile");

        } else {

            expertProfile.updateExpertProfile(json, getMap().get("expertProfileId"));
            responseLogger.writeResponseAsLog("Update Expert Profile");

        }

        this.checkAndWriteToReport(response.statusCode(),
                "Expert Profile updated with--" + json, isNegative);

        responseLogger.writeResponseAsLog("Update Expert Profile");
    }

    */
/**
     *
     *//*

    @Then("get profile")
    @When("get profile")
    @Aliases(values = {"get the profile",
            "get the previously created expert profile", "get expert profile"})

    public void getProfile() {

        this.info("GET Expert Profile");

        //  String expertProfileID = getMap ( ).get ( "expertProfileId" );

        if (isNegative) {

            expertProfile.getProfileOfExpert("", isExpert);

        } else {

            expertProfile.getProfileOfExpert("", isExpert);
        }

        if (isExpert == false) {

            this.checkAndWriteToReport(response.statusCode(),
                    "Expert profile loaded by a user--" + expertProfile.getUserCredential()[0], isNegative);
        } else {

            this.checkAndWriteToReport(response.statusCode(),
                    "Expert profile loaded by expert--" + expertProfile.getExpertCredential()[0], isNegative);
        }
    }

    */
/**
     * @param
     *//*

    @Then("get profile of expert")
    public void getProfileWithID() {

        this.info("GET Expert Profile");

        String expertProfileID = getMap().get("expertProfileId");

        if (isNegative) {

            expertProfile.getProfileOfExpert(expertProfileID, isExpert);

            responseLogger.writeResponseAsLog("Get Expert Profile-Negative");

        } else {

            expertProfile.getProfileOfExpert(expertProfileID, isExpert);
        }

        this.checkAndWriteToReport(response.statusCode(),
                "Get Profile successfully", isNegative);

        responseLogger.writeResponseAsLog("Get Expert Profile");
    }


    @Then("upload media as $mediaPath")
    public void addMedia(@Named("mediaPath") String mediaPath) {

        info("Uploading a file..:" + mediaPath);

        expertProfile.uploadMedia(mediaPath, isExpert);

        if (!expertProfile.getResponseOfMediaUpload().contains("errors")) {

            this.checkAndWriteToReport(HTTPCode.HTTP_OK,
                    "Media Uploaded--::" + expertProfile.getResponseOfMediaUpload(), isNegative);

        } else if (expertProfile.getResponseOfMediaUpload().contains("errors") && isNegative) {

            this.checkAndWriteToReport(HTTPCode.HTTP_BAD,
                    "Negative Test passed-::" + expertProfile.getResponseOfMediaUpload(), true);

        } else if (expertProfile.getResponseOfMediaUpload().contains("errors") && isNegative == false) {

            this.checkAndWriteToReport(HTTPCode.HTTP_BAD,
                    "Something Went Wrong-::" + expertProfile.getResponseOfMediaUpload(), false);
        } else if (FileUpload.ERROR) {

            this.checkAndWriteToReport(HTTPCode.HTTP_BAD,
                    "Media Upload failed--::" + expertProfile.getResponseOfMediaUpload(), false);
        }

        responseLogger.writeResponseAsLog("Upload Media");
    }

    */
/**
     * Deleting an Expert profile
     **//*

    @Then("delete the profile")
    public void deleteProfile() {

        this.info("Deleting Expert Profile");

        boolean isDelete = false;

        if (isNegative) {

            expertProfile.deleteProfile(expertProfile.getExpertProfileID());

        } else {

            isDelete = expertProfile.deleteProfile(expertProfile.getExpertProfileID());
        }
        this.AssertAndWriteToReport(isDelete,
                "Expert profile deleted");


    }

    */
/**
     * @param id
     *//*

    @Then("delete the profile with id $id")
    public void deleteProfile(@Named("id") String id) {

        this.info("Delete Expert Profile");

        boolean isDelete = false;

        if (isNegative) {

            expertProfile.deleteProfile(id);

        } else {

            isDelete = expertProfile.deleteProfile(id);
        }
        this.AssertAndWriteToReport(isDelete,
                "Profile deleted with id->" + id);


    }

    */
/**
     * Test cases to drive the Calling API
     *//*

    */
/*Calling API Test cases*//*

    @When("register a device as $json")
    @Then("register a device as $json")
    public void registerDevice(@Named("json") String json) {

        if (isNegative) {
            call.registerDevice(json, isExpert);
            responseLogger.writeResponseAsLog("Register Device-Negative");
        } else {

            call.registerDevice(json, isExpert);
            responseLogger.writeResponseAsLog("Register Device");
        }

        checkAndWriteToReport(response.statusCode(), "Device Registered", false);
    }
    

    @Then("initiate a call of scheduled_duration $time")
    public void initiateCall(@Named("time") String time) {

        info("Initiating a call to expert...");

        if (isNegative) {

            call.doCall(time);

            responseLogger.writeResponseAsLog("Call initiated by user-Negative");

        } else {

            call.doCall(time);

            responseLogger.writeResponseAsLog("Call initiated by user-Negative");

        }

        checkAndWriteToReport(response.statusCode(), "Call initiated", isNegative);
    }


    @Then("accept the call")
    public void acceptCall() {

        this.info("accepting Call...");

        call.isAcceptCall();

        this.checkAndWriteToReport(response.statusCode(),
                "Call Accepted", isNegative);

        responseLogger.writeResponseAsLog("Accept Call");
    }

    @Then("decline the call")
    public void declineCall() {

        this.info("Declining a Call");

        call.isDecline();

        this.checkAndWriteToReport(response.statusCode(),
                "Call Declined", isNegative);

        responseLogger.writeResponseAsLog("Decline Call");
    }

    @Then("delay the call")
    public void delayCall() {

        this.info("Delaying the call");

        call.isDelay();

        this.checkAndWriteToReport(response.statusCode(),
                "Call delayed", isNegative);

        responseLogger.writeResponseAsLog("Delay Call");
    }

    @Then("disconnect the call")
    public void disconnectCall() {

        this.info("Disconnecting a call...");

        call.isDissconnectCall();

        this.checkAndWriteToReport(response.statusCode(),

                "Call Disconnected", isNegative);

        responseLogger.writeResponseAsLog("Disconnect Call");
    }

    */
/* Phone code verification test cases*//*


    @When("we provide phone number as $phone")
    @Then("we provide phone number as $phone")
    public void sendCode(@Named("phone") String phoneNo) {

        this.info("Sending phone code--" + phoneNo);

        if (isNegative) {

            phone.phoneCodeSend(phoneNo, isExpert);

            responseLogger.writeResponseAsLog("Phone code sent");

        } else {
            phone.phoneCodeSend(phoneNo, isExpert);

            this.checkAndWriteToReport(response.statusCode(),
                    "Phone code sent", false);

            responseLogger.writeResponseAsLog("Phone code sent");

            */
/*Phone code resent*//*


            this.info("Resending phone code--" + phoneNo);

            phone.phoneCodeResend(phoneNo, isExpert);

            this.checkAndWriteToReport(response.statusCode(),
                    "Phone code resent", isNegative);

            responseLogger.writeResponseAsLog("Phone code resent");
        }
    }

    @Then("verification code should be sent")
    public void verifyCode() {

        this.info("Getting verification code..");
        this.AssertAndWriteToReport(phone.isCodeSent(),
                "Code sent is-->" + phone.getCode());
    }

    @Then("phone should be verified")
    public void verifyPhone() {

        this.info("verifying the mobile number..");

        phone.verfiyPhone(phone.getCode(), isExpert);

        this.checkAndWriteToReport(response.statusCode(),
                "Mobile no verified", isNegative);

        responseLogger.writeResponseAsLog("Mobile no verify");
    }

    @Then("should not $allowed")
    public void negativeCases() {

        this.checkAndWriteToReport(response.statusCode(), jsonParser.printError(), true);
    }

    */
/*Social Links Test Cases*//*


    @When("post social links from $social")
    @Then("post social links from $social")
    @Pending
    public void postSocialLink(@Named("social") String social) {

        this.info("Posting Social Links..");
        socialLinks.postSocialLink(social);

        this.checkAndWriteToReport(response.statusCode(),
                "Social link posted", isNegative);

        responseLogger.writeResponseAsLog("Post Social Link");
    }

    @Then("get the social links")
    public void getSocialLinks() {

        this.info("Getting Social links ");

        if (isNegative) {

            socialLinks.getSocialLinks();

            responseLogger.writeResponseAsLog("Get Social Link");

        } else {

            socialLinks.getSocialLinks();
        }
        this.checkAndWriteToReport(response.statusCode(),
                "Social link get Successful", isNegative);

        responseLogger.writeResponseAsLog("Get Social Link");
    }

    */
/**
     * Adding social link to expert profile
     *//*


    @Then("add social link to expert profile")
    @When("add social link to expert profile")
    public void addSocialLinkToExpertProfile() {

        this.info("Adding social link to expert profile");
        String id = getMap().get("expertProfileId");

        if (isNegative) {

            socialLinks.addLinksToExpertProfile(id);

            responseLogger.writeResponseAsLog("Add social link to expert profile");

        } else {

            socialLinks.addLinksToExpertProfile(expertProfile.getExpertProfileID());

            this.checkAndWriteToReport(response.statusCode(),
                    "Social link added to expert profile->" + expertProfile.getExpertProfileID(), isNegative);

            responseLogger.writeResponseAsLog("Add social link to expert profile");
        }
    }

    */
/**
     *
     *//*

    @Then("list all social links of a ExpertProfile")
    @When("list all social links of a ExpertProfile")
    public void listAllSocialLinks() {

        this.info("Getting social links linked to expert profile");
        String id = getMap().get("expertProfileId");

        if (isNegative) {

            socialLinks.getLinkedListsOfExpertProfile(id);

        } else {
            socialLinks.getLinkedListsOfExpertProfile(id);

            this.checkAndWriteToReport(response.statusCode(),
                    "Successfully get all links from expert profile id:->" + expertProfile.getExpertProfileID(), isNegative);

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
        } else if (!count.equals(countOfSocialLinks) && isNegative) {

            negativeCases();
        }
    }


    @Then("add $url as RSS Feed")
    @When("add $media as RSS Feed")
    public void addFeed(@Named("url") String url) {

        this.info("Adding RSS feed ");

        if (isNegative) {

            socialLinks.addFeedsToSocialLink(url);
        } else {
            socialLinks.addFeedsToSocialLink(url);

            this.checkAndWriteToReport(response.statusCode(),
                    "Successfully added RSS feed->" + expertProfile.getExpertProfileID(), isNegative);

            responseLogger.writeResponseAsLog("Add RSS feed");
        }
    }

    @Then("remove one social link")
    @When("remove one social link")
    public void removeSocialLink() {

        boolean isDelete = false;
        this.info("Removing social link");

        if (isNegative) {

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

        } else if (!count.equals(countOfSocialLinks) && isNegative) {

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
        } else if (!count.equals(countOfSocialLinks) && isNegative) {

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

        if (response.statusCode() != HTTP_BAD) {

            socialLinks.setContentID(jsonParser.getJsonData("results[0].id", ResponseDataType.STRING));
            getMap().put("unpublishedContentId", socialLinks.getContentID());
        }

        this.checkAndWriteToReport(response.statusCode(), "Feed listed", isNegative);

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
    @Aliases(values = {"try to publish a ignored content", "publish the same content again"})

    public void publish() {

        info("Publishing a content");

        String cId = getMap().get("unpublishedContentId");

        if (isNegative) {

            socialLinks.publishContent(cId);

        } else {

            socialLinks.publishContent(cId);
        }

        this.checkAndWriteToReport(response.statusCode(), "Content published", isNegative);

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

        this.checkAndWriteToReport(response.statusCode(), "All published content listed", isNegative);

        responseLogger.writeResponseAsLog("Get Published contents");
    }

    @Then("get a particular content")
    public void getContent() {

        info("Listing a particular content");

        String id = getMap().get("publishedContentID");

        if (isNegative) {

            socialLinks.getContent(id);

        } else {
            socialLinks.getContent(id);

            this.checkAndWriteToReport(response.statusCode(), "Content with id->" + socialLinks.getPublishedContentId() + " listed", isNegative);

            responseLogger.writeResponseAsLog("Get a particular Published contents");
        }
    }

    @Then("delete that content")
    public void deleteContent() {

        info("Deleting a particular content");

        if (isNegative) {

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

        if (isNegative) {

            socialLinks.ignoreContent(getMap().get("unpublishedContentId"));

        } else {

            socialLinks.ignoreContent(getMap().get("unpublishedContentId"));
        }

        this.checkAndWriteToReport(response.statusCode(), "Content Ignored", isNegative);

        responseLogger.writeResponseAsLog("Ignore Content API");
    }

    @Then("check count of unpblished feed again")
    public void feedCount2() {

        socialLinks.getFeedListing();

        socialLinks.setCountOfFeeds(socialLinks.getSocialLinkCount());

        this.AssertAndWriteToReport(true, "Number of unpublished feeds are:->" + socialLinks.getcountOfFeeds());
    }


    */
/*Searching TestCases*//*


    @Given("a $type $text")
    public void getText(@Named("text") String text,
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

    @Then("search all the expert by $by")
    public void searchExpert(@Named("by") String by) {

        info("Searching through " + by);

        switch (by.toLowerCase()) {

            case "freetext":
                String searchText = searching.getSearchText();

                if (isNegative) {
                    searching.search(searchText, Searching.SearchType.BY_TEXT, true);
                } else {
                    searching.search(searchText, Searching.SearchType.BY_TEXT, true);
                }
                this.checkAndWriteToReport(response.statusCode(), "Expert Related to text-> " + searchText + " has been loaded", isNegative);

                responseLogger.writeResponseAsLog("Search by FreeText");
                break;

            case "tagid":
                String tId = searching.getTagId();
                if (isNegative) {
                    searching.search(tId, Searching.SearchType.BY_TAG, true);
                } else {

                    searching.search(tId, Searching.SearchType.BY_TAG, true);
                }
                this.checkAndWriteToReport(response.statusCode(), "Expert Related to tagID-> " + tId + " has been loaded", isNegative);

                responseLogger.writeResponseAsLog("Search by TagId");
                break;

            case "expertid":

                String eId = searching.getExpertId();

                System.out.println(searching.getExpertId() + "==========" + isNegative);

                if (isNegative) {

                    searching.search(eId, Searching.SearchType.BY_ID, true);
                } else {

                    searching.search(eId, Searching.SearchType.BY_ID, true);
                }
                this.checkAndWriteToReport(response.statusCode(), "Expert Related to ExpertId-> " + eId + " has been loaded", isNegative);

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

    */
/**
     * Super Admin test cases
     *//*


    @Then("create a super admin content as $json")
    public void createSuperAdminContent(@Named("json") String json) {

        info("Creating super admin content as--" + json);

        if (isNegative) {

            superAdmin.createContent(json);

        } else {

            superAdmin.createContent(json);
        }

        this.checkAndWriteToReport(response.statusCode(), "Content with id\t" + superAdmin.getContentId() + " created", isNegative);

        responseLogger.writeResponseAsLog("Create a super admin content");
    }


    @Then("$action the content")
    @Alias("$action the content again")
    public void actionOnContent(@Named("action") String action) {

        String cID = getMap().get("superAdminContentId");
        switch (action.toLowerCase()) {

            case "get":
                info(action + " the super Admin content");

                superAdmin.getContent(cID);

                this.checkAndWriteToReport(response.statusCode(), "Content\t" + superAdmin.getContentId() + "listed", isNegative);

                responseLogger.writeResponseAsLog("Super Admin Content");
                break;

            case "get all":
                info(action + " the super Admin content");

                superAdmin.getContent("");

                this.checkAndWriteToReport(response.statusCode(), "Content listed", isNegative);

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
                "count of content is--" + superAdmin.getCountOfContent(), isNegative);
    }

    @Then("update the content as $json")
    @When("update the content as $json")
    public void updateContent(@Named("json") String json) {

        info("updating the previously created content as--" + json);

        String cID = getMap().get("superAdminContentId");

        if (isNegative) {

            superAdmin.updateContent(json, cID);

        } else {

            superAdmin.updateContent(json, cID);
        }
        this.checkAndWriteToReport(response.statusCode(), "Content with id\t" + cID + " updated", isNegative);

        responseLogger.writeResponseAsLog("Update Content");
    }

    */
/**
     * Payment account test cases
     *//*


    @When("create a payment account as $json")
    @Then("create a payment account as $json")
    public void createAccount(@Named("json") String json) {

        info("Creating payment account");

        if (isNegative) {

            account.createAccount(json);

        } else {
            account.createAccount(json);
        }
        this.checkAndWriteToReport(response.statusCode(), "Account created ", isNegative);

        responseLogger.writeResponseAsLog("Payment Account");
    }

    @Then("get the account")
    public void getAccount() {

        info("listing the payment account created");

        String accountId = account.getAccountId();

        account.getAccount(accountId);

        this.checkAndWriteToReport(response.statusCode(), "Account listed", isNegative);

        responseLogger.writeResponseAsLog("get Payment Account");
    }

    @Then("check profile completness")
    public void checkProfileComplete() {

        info("Checking profile completeness");

        profileComplete.checkProfileCompletemness();

        boolean isComplete = profileComplete.isProfileComplete();

        if (isComplete) {

            this.AssertAndWriteToReport(isComplete, "Profile completed");

        } else if (!isComplete && isNegative) {

            negativeCases();

        } else {

            this.AssertAndWriteToReport(false, "Profile Not Completed");
        }
    }

    */
/**
     * GET STREAM API
     *//*

    */
/*********************************************************************//*

    @Then("get the feeds from get stream by $by")
    public void getFeeds(@Named("by") String by) {

        info("Listing feed from getStream via-->" + by);
        String eProfileId = getMap().get("expertProfileId");

        switch (by.toLowerCase()) {

            case "expertprofileid":
                getStreamFeeds.getFeeds(eProfileId, GetStreamFeeds.By.BY_EXPERTPROFILE);

                responseLogger.writeResponseAsLog("GetStream feed listing by expert Profile id");

                this.checkAndWriteToReport(response.statusCode(), "Feed listed", isNegative);
                break;

            case "expertid":
                getStreamFeeds.getFeeds(getMap().get("baseId"), GetStreamFeeds.By.BY_EXPERTID);

                this.checkAndWriteToReport(response.statusCode(), "Feed listed", isNegative);

                responseLogger.writeResponseAsLog("GetStream feed listing by expert ID");
                break;

            default:
                throw new ExpertChatException("No Suitable option found..");
        }

    }

    @Then("get the feeds from get stream using tag $tag")
    public void getFeedsByTag(@Named("tag") String tag) {

        info("Getting feeds by tag--" + tag);

        if (!isNegative) {

            getStreamFeeds.getFeeds(tag, GetStreamFeeds.By.BY_TAGID);

            this.checkAndWriteToReport(response.statusCode(), "Feed listed", isNegative);

            responseLogger.writeResponseAsLog("GetStream feed listing by Tag ID--" + tag);
        } else {

            getStreamFeeds.getFeeds(tag, GetStreamFeeds.By.BY_TAGID);

            this.checkAndWriteToReport(response.statusCode(), "Feed listed", isNegative);

            responseLogger.writeResponseAsLog("GetStream feed listing by Tag ID--" + tag);
        }
    }

    @Then("verify the tags")
    @Alias("verify the tags again")
    public void verify() {

        info("Verifying the tags");

        info("Super Admin tags..." + getMap().get("su-stringOfTags"));

        info("get stream tags..." + getMap().get("gs-stringOfTags"));

        if (getMap().get("su-stringOfTags").equals(getMap().get("gs-stringOfTags"))) {

            this.AssertAndWriteToReport(true, "tags are found same");

        } else if (!getMap().get("getStreamId").equals(getMap().get("superAdminContentId")) && isNegative) {

            this.AssertAndWriteToReport(true, "Negative Test Passed.Content id--" +

                    getMap().get("superAdminContentId") + "\t not found while searching on getStream via tag API");
        } else {

            info("Something went wrong");
        }
    }


    @Then("create a calender as $json")
    public void calender(@Named("json") String json) {

        info("Creating a calender...");
        if (isNegative) {
            calender.createCalender(json);
        } else {
            calender.createCalender(json);
        }
        checkAndWriteToReport(response.statusCode(),"Calender Created", isNegative);
        responseLogger.writeResponseAsLog("Calender API");
    }

    @Then("get the calender")
    @When("get the calender")
    @Aliases(values = {"get the calender again"})
    public void getCalender(){

        info("Get the calender...");
        calender.getCalender(calender.getCalenderId());
        checkAndWriteToReport(response.statusCode(),
                "Calender with id->"+calender.getCalenderId()+"\tloaded", isNegative);
        responseLogger.writeResponseAsLog("Get CAlender");
    }

    @Then("update the calender as $json")
    public void updateCalender(@Named("json") String json) {

        info("Updating a calender...");

        if (isNegative) {

            calender.updateCalender(json, calender.getCalenderId());
        } else {

            calender.updateCalender(json, calender.getCalenderId());
        }

        checkAndWriteToReport(response.statusCode(),"Calender with id->" + calender.getCalenderId() + "\t updated", isNegative);

        responseLogger.writeResponseAsLog("Calender API");
    }

    @Then("get the avilable slot of expert1")
    public void getAvilableSlots(){
        info("Listing all the avilable slots of expert1");

        expertProfile.getProfileOfExpert ( "",isExpert );

        String eId=getMap().get("expertId");

        calender.getAvilableSlot(eId);

        checkAndWriteToReport(response.statusCode(),"All avilable slot listed", isNegative);

        responseLogger.writeResponseAsLog("GET Avilable slots");
    }

    @Then("check the profile visits and count should be $count")
    public void profileVisits(@Named ("count")String count){

        info("Checking Number of profile visits");

        String date="";

        stats.getAllcounts ();

        int size=response.getResponse ().jsonPath ().getList ( "results.profile_visits.summary" ).size ();

        if(size>0) {

            String ActualCount=jsonParser.getJsonData ( "results.profile_visits.summary[0].count", ResponseDataType.INT);

            date=jsonParser.getJsonData ( "results.profile_visits.summary[0].date", ResponseDataType.STRING );

            if(String.valueOf (count).equals (ActualCount)){

                AssertAndWriteToReport ( true, "Number of profile visits for\t"+date+"\tis\t"+ActualCount);
            }else {

                AssertAndWriteToReport ( false, "Number of profile visits for\t"+date+"\tis\t"+ActualCount);
            }
        }else {

            AssertAndWriteToReport ( true, "Number of profile visits for\t"+date+"\tis\t"+size);
        }

    }

    @Then("check the session count and count should be $count")
    public void sessionCounr(@Named ( "count" )String count){
        info("Checking Number of session");

        String date="";

        stats.getAllcounts ();

        try{

            TimeUnit.SECONDS.sleep ( 5 );

        }catch ( Exception e ){


        }

        int size=response.getResponse ().jsonPath ().getList ( "results.sessions_count.summary" ).size ();

        if(size>0) {

            String ActualCount=jsonParser.getJsonData ( "results.sessions_count.summary[0].count", ResponseDataType.INT);

            date=jsonParser.getJsonData ( "results.sessions_count.summary[0].date", ResponseDataType.STRING );

            if(count.equals (ActualCount)){

                AssertAndWriteToReport ( true, "Number of Session for\t"+date+"\tis\t"+ActualCount);
            }else {

                AssertAndWriteToReport ( false, "Number of Session for\t"+date+"\tis\t"+ActualCount);
            }
        }else {

            AssertAndWriteToReport ( true, "Number of Session for\t"+date+"\tis\t"+size);
        }
    }

    @Then("check the total statistics between $from and $to")
    public void getStats(@Named ( "to" )String to,
                         @Named ( "from" )String from){


        info("Getting all expert statistics..");
        String toDate;

        if(to.toLowerCase ().equals ( "today" )){

            toDate=new SimpleDateFormat ( "yyyy-mm-dd" ).format (new Date());
        }else {
            toDate=to;
        }

        stats.getAllStats(toDate, from);
        checkAndWriteToReport (response.statusCode (),

                "Total profile visit bewtween\t"+toDate+"\t"+from+"\tis"+stats.getNumProfileVisit ()+"\n"+
                            "Total Number of session bewtween\t"+toDate+"\t"+from+"\tis"+stats.getNumSession (), isNegative);
    }

//    @Then("extend the session for $time")
//    @When("extend the session for $time")
//    @Given("extend the session for $time")
//    @Aliases ( values = {"extend the session for $time"})
//    public void extendSession(@Named ( "time" )String time){
//
//        info("Extending the current ongoing session by\t"+time);
//
//        String realTime= time.substring (0,time.indexOf( "m" )-1);
//
//        call.extendSession(realTime);
//
//        checkAndWriteToReport ( response.statusCode (), "Call extended by\t"+time , isNegative);
//
//        responseLogger.writeResponseAsLog ( "Extend call" );
//
//    }

}


*/
