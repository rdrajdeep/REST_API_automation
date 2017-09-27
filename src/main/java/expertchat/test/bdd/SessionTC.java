package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.bussinesslogic.*;
import expertchat.params.Credentials;
import expertchat.params.parameter;
import expertchat.usermap.TestUserMap;
import expertchat.util.DatetimeUtility;
import org.jbehave.core.annotations.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;

import static expertchat.usermap.TestUserMap.getMap;

/*@
 * This serve the purpose of creating PROMO CODE,
 * Scheduling a session with that PROMO CODE
 * Validate the esitimated Revenue calculation
 * ** Author: Rajdeep**
 */

public class SessionTC extends AbstractSteps {

    public SessionTC(ExtentReports reports, String casName) {
        super(reports, casName);
    }

    SessionUtil pcode = new SessionUtil();

    ExpertChatApi expertChatApi = new ExpertChatApi();

    Credentials credentials = Credentials.getCredentials();

    private ApiResponse response = ApiResponse.getObject();
    private Calling call = new Calling();
    private ExpertProfile expertProfile = new ExpertProfile();
    private Calender calender = new Calender();
    private List<Long> slots = new ArrayList<>();
    private String sessionId = null;
    private String userDeviceId = null;
    private DatetimeUtility dateUtil=new DatetimeUtility();
    private boolean isCallArived=false;
    private boolean ISACTIONTAKEN =false;
    private boolean isExtensible=false;

    @When("login as super user $json")
    public void superUserlogin(@Named("json") String json) {

        expertChatApi.doLogIn(json, false);

        credentials.setuserCredential(json);

        this.checkAndWriteToReport(response.statusCode(), "Logged in as Super User" + json, parameter.isNegative());

    }

    /*
    * @param = All details of promocode as Json
    * */
    @Then("create promocode $promoCode")
    public void PromoCode(@Named("promoCode") String promoCode) {

        System.out.println("-- Creatin a promo code--");

        pcode.createPromoCode(promoCode);

        String coupon = jsonParser.getJsonData("results.coupon_code", ResponseDataType.STRING);

        this.checkAndWriteToReport(response.statusCode(), "New Promo code " + "\"" + coupon + "\"" + " created successfully", parameter.isNegative());

        return;
    }

    /*
    * @Param promo code
    * */

    @Pending
    @Then("schedule a call with expert $expertId using promocode $promoCode")
    public void scheduleCall(@Named("promoCode") String promoCode,
                             @Named("expertId") int expertId) {
        // call.scheduleSession();

    }

    /**
     * @ Getting a slot from that expert
     */

    @Then("get a slot")
    public void getSlot() {


        System.out.println("--- GETTING A SLOT NOW---");

       // for(String caleder: pcode)
        slots=pcode.getAllSlots("10");
        System.out.println("slot checked- getSlot-->"+slots);
        System.out.println(slots.get(0));
        //slot=slots.get(0).toString();

        System.out.println("Extracted slot is: "+slots.get(0));

        if (!slots.isEmpty()) {

            this.checkAndWriteToReport(response.statusCode(), "Slot extracted", parameter.isNegative());

        }
    }


    /*@
     * Scheduling a session using that PROMO CODE and duration provided**
     * @Param - PROMO CODE and Call Duration
     */

    @Then("schedule a session using promo code $code and duration $duration")
    @When("schedule a session using promo code $code and duration $duration")
    @Given("schedule a session using promo code $code and duration $duration")
    public void scheduleSession(@Named("code") String code,
                                @Named("duration") int duration) {

        System.out.println("-- Scheduling a session  --");

        String today=dateUtil.currentDateOnly();
        String finalSlot=today+"T"+slots.get(0)+":00"+"Z";
        System.out.println("Print final booking slot "+finalSlot);
        call.scheduleSession(finalSlot , code, duration);
        response.printResponse();
        this.checkAndWriteToReport(response.statusCode(), "SessionUtil with id--" + getMap().get("scheduled_session_id") + " created", parameter.isNegative());
        userDeviceId = jsonParser.getJsonData("results.user_device", ResponseDataType.STRING);

    }


    /*@
     Login with User, Param--> JSON
     */

    @When("login with $user")
    @Then("login with $user")
    @Alias("i login with $user")
    public void login(@Named("user") String user) {

        info("Login");

        System.out.println("-- Login  --");

        if (user.contains("{") && parameter.isExpert()) {

            expertChatApi.doLogIn(user, true);

            credentials.setExpertCredential(user);

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by expert " + jsonParser.getJsonData("results.email", ResponseDataType.STRING), parameter.isNegative());

        } else if (user.contains("{") && parameter.isExpert() == false) {

            expertChatApi.doLogIn(user, false);

            credentials.setuserCredential(user);

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by user--" + user, parameter.isNegative());

        } else if (user.contains("user")) {

            expertChatApi.doLogIn(TestUserMap.getUserCredentialsByKey(user), false);

            credentials.setuserCredential(TestUserMap.getUserCredentialsByKey(user));

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by user--" +
                    TestUserMap.getUserCredentialsByKey(user), parameter.isNegative());


        } else {
            expertChatApi.doLogIn(TestUserMap.getUserCredentialsByKey(user), true);

            credentials.setExpertCredential(TestUserMap.getUserCredentialsByKey(user));

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by expert--" +
                    TestUserMap.getUserCredentialsByKey(user), parameter.isNegative());
        }

    }

    /*@
     *Getting the expert profile.
     */

    @Then("get profile")
    @When("get profile")
    @Aliases(values = {"get the profile",
            "get the previously created expert profile",
            "get expert profile",
            "get profile of the logged in expert"})

    public void getProfile() {

        this.info("GET Expert Profile");

        System.out.println("-- Geting expert profile --");

        //  String expertProfileID = getMap ( ).get ( "expertProfileId" );

        if (parameter.isNegative()) {

            expertProfile.getProfileOfExpert("", parameter.isExpert());

        } else {

            expertProfile.getProfileOfExpert("", parameter.isExpert());
        }

        if (parameter.isExpert() == false) {

            this.checkAndWriteToReport(response.statusCode(),
                    "Expert profile loaded by a user--" + credentials.getUserCredential()[0], parameter.isNegative());
        } else {

            this.checkAndWriteToReport(response.statusCode(),
                    "Expert profile loaded by expert--" + credentials.getExpertCredential()[0], parameter.isNegative());
        }
    }
    /*@
     *Creating a calender
     */


    @Then("create a calender as $json")
    @Aliases(values = {"i am creating a calender as $json",
            "i create a calender" , "i create a calender for today"})

    public void calender() throws Exception{

        System.out.println("--Creating a calender  --");

        info("Creating a calender...");

        if (parameter.isNegative()) {

            calender.createCalender();
            //calender.appendExistingCalender();
        } else {
            calender.createCalender();
            //calender.appendExistingCalender();
        }
        this.checkAndWriteToReport(response.statusCode(), "Calender Created", parameter.isNegative());

    }

    /*@
    * Registering a device with the given JSON data from story
    *
     */

    @When("register a device as $json")
    @Then("register a device as $json")
    @Aliases(values = {"i register a device as $json",
            "i register a expert device as $json"})
    public void registerDevice(@Named("json") String json) {


        System.out.println("--Registering a Device --");


        if (parameter.isNegative()) {

            call.registerDevice(json, parameter.isExpert());

            this.checkAndWriteToReport(response.statusCode(), "Device not Registered--Negative Test case", true);

        } else {

            call.registerDevice(json, parameter.isExpert());

            System.out.println(json + parameter.isExpert());
        }

        this.checkAndWriteToReport(response.statusCode(), "Device Registered", false);
    }

    /**
     * @ Get SessionUtil ID
     */

    @Then("it should return session id")
    public void setSessionId() {

        info("SessionUtil id print");

        sessionId = getMap().get("scheduled_session_id");

        this.checkAndWriteToReport(response.statusCode(), sessionId + "-- Created", parameter.isNegative());

    }

    /**
     * @ Get SessionUtil Details
     */
    @When("I get the session details")
    @Alias("I pass on session id in session details API")
    public void getSessionDetails() {

        info("SessionUtil Details of " + sessionId);

        call.getSessionDetails(sessionId, parameter.isExpert());

        this.checkAndWriteToReport(response.statusCode(), "Details with id--" + sessionId + " extracted", parameter.isNegative());
    }

    /**
     * @ User revenue calculation validation
     */
    @When("user revenue should be $value since 100% promo is applied")
    @Then("user revenue should be $value since 100% promo is applied")

    public void validateUserRevenue(@Named("value") float value) {

        info("User revenue validation");

        boolean isValidate = false;

        if (getMap().get("user_revenue").equals(String.valueOf(value))) {

            isValidate = true;

            this.AssertAndWriteToReport(isValidate, "User revenue is " + value + " since 100% promo is applied");

        } else {

            this.AssertAndWriteToReport(isValidate, "User revenue calculation is wrong");
        }
    }

    /**
     * Validating Expert Estimated revenue for the schedule call
     */

    @Then("expert estimated revenue should be $value since payment type is experchat")
    @When("expert estimated revenue should be $value since payment type is experchat")
    public void validateExpertRevenue(@Named("value") float value) {

        info("Expert revenue validation");

        boolean isValidate = false;

        if (getMap().get("expert_revenue").equals(String.valueOf(value))) {

            isValidate = true;

            this.AssertAndWriteToReport(isValidate, "Expert estimated revenue is " + value + " after deducting 27.95% ExperChat commision");

        } else {

            this.AssertAndWriteToReport(isValidate, "Expert estimated revenue calculation is wrong");
        }
    }

    /*@
    * Validating the session status after scheduling a call
     */
    @Then("session status should be $status")
    public void validateStatus(@Named("status") String expected) {

        info("Verifying session final status");
        String actual = jsonParser.getJsonData("results.final_status", ResponseDataType.STRING);

        if (expected.equalsIgnoreCase(actual)) {

            this.AssertAndWriteToReport(true, "SessionUtil final status is " + actual);
        } else {

            this.AssertAndWriteToReport(false, "SessionUtil final status is " + actual);
        }
    }


    @When("i initiate the session")
    @Then("i initiate the session")
    public void validateSessionInitiation() throws  Exception{

        info("Intiating a Call/SessionUtil");

        System.out.println("initiating call");

        pcode.convertDateTime();
        call.intiate(sessionId, userDeviceId);
        response.printResponse();

        this.checkAndWriteToReport(response.statusCode(),"Successfully call initiated",parameter.isNegative());
    }


    @Then("validate that session cannot be initiated before scheduled time")
    public void validateCall() {
        String statusCode = jsonParser.getJsonData("results.status", ResponseDataType.INT);
        if (!statusCode.equals("2")) {
            this.AssertAndWriteToReport(true, "Since scheduled date is in future date, hence unable to initiate call");
        }
    }

    /**
     * Perform Action on accepted call
     * @return
     */

    /**
     *
     * @return
     */
    @When("I get a call")
    public boolean isCallArrive(){

        if(getMap().get("call_status").equals(CallStatus.INITIATED)){

            call.isCallArived();
            isCallArived=true;
        }

        this.AssertAndWriteToReport(isCallArived, "Call arrived at expert end");
        return isCallArived;
    }

    /**
     *
     */
    @Then("I will accept it")
    public void accept(){

       ISACTIONTAKEN =call.isAcceptCall();
    }

    /**
     *
     * @param status
     */
    @Then("Call should be in $status status")
    @Aliases(values = {"status should be $status"})
    public void verifyCallStatus(@Named("status")String status){

        boolean isValidate=false;

        String actual=jsonParser.getJsonData("results.status", ResponseDataType.STRING);

        if(status.equalsIgnoreCase(actual)){

            isValidate=true;
        }

        this.AssertAndWriteToReport(isValidate,"Call status is--"+status);

    }

    /**
     *
     * @param action
     */
    @Then("I will $action the call")
    @Aliases(values = {"I will $action the same call"})
    public void performActionOnRecivedCall(@Named("action")String action){

        switch (action.toLowerCase()){

            case "disconect" : ISACTIONTAKEN=call.isDissconnectCall();
                               break;
            case "reconnect" : ISACTIONTAKEN=call.reconnect(sessionId);
                               break;
            case "decline"   : ISACTIONTAKEN=call.isDecline();
                               break;
        }

        this.AssertAndWriteToReport(ISACTIONTAKEN,"Call action is-->"+action);
        ISACTIONTAKEN=false;

    }

    /**
     *
     */
    @Then("reconect should be successful")
    public void verifyReconnect(){

    }

    /**
     *
     */

    @Then("wait for session extenstion")
    @When("wait for session extenstion")
    public void continueSession() throws InterruptedException {

        int duration=Integer.parseInt(getMap().get("scheduled_duration")); //10 min

        SessionUtil session=new SessionUtil();
        long scheduleDateTime=session.getScheduleTimeInMillSecond();
        long durationToMilli=duration*60000;
        long scheduleEndTimeInMili=scheduleDateTime+durationToMilli;
        long extensionTimeBeforeEnd=5*60000;

        long extensibleAtInMili=scheduleEndTimeInMili-extensionTimeBeforeEnd;
        String currentTime=session.getCurrentTime();
        LocalDateTime serverjodatime = new DateTime(currentTime).toLocalDateTime();
        DateTimeFormatter serverdtfOut = DateTimeFormat.forPattern("MMM dd yyyy, hh:mm a");
        long currentTimeInMilli=session.getTimeInMillis(serverdtfOut.print(serverjodatime), "MMM dd yyyy, hh:mm a");

        long waitingTime= (extensibleAtInMili-currentTimeInMilli)/60000;

        System.out.println("Waiting "+waitingTime+" minute for extending call");
        info("Waiting "+waitingTime+" minute for extending call");

        while(!(currentTimeInMilli>=extensibleAtInMili) && !(currentTimeInMilli< scheduleEndTimeInMili)){

            /*Hit extension API*/

            isExtensible = call.checkExtension(sessionId);

        }


        this.AssertAndWriteToReport(isExtensible,"SessionUtil can be extended now");

    }

    /**
     *
     */
    @Then("verify if session extension is possible")
    public void verifySessionExtension(){

        /**
         * check for avaialble slot
         */

        if(isExtensible){
            call.extendSession("10");
        }
        this.checkAndWriteToReport(response.statusCode(),"Session exteded for 10 more minuite",parameter.isNegative());
    }


}
