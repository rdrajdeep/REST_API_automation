package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.bussinesslogic.*;
import expertchat.params.Credentials;
import expertchat.params.parameter;
import expertchat.usermap.TestUserMap;
import expertchat.util.DatetimeUtility;
import expertchat.util.ExpertChatException;
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
    //private String sessionId = null;
    private String userDeviceId = null;
    private DatetimeUtility dateUtil=new DatetimeUtility();
    private boolean isCallArived=false;
    private boolean ISACTIONTAKEN =false;
    private boolean isExtensible=false;
    boolean isWaitOver=false;

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
    @When("get a slot")
    @Aliases(values = {"I get a slot"})
    public void getSlot() {

        System.out.println("--- GETTING A SLOT NOW---");
        slots=pcode.getAllSlots("10");
        System.out.println("Extracted slot is: "+slots.get(0));

        if (!slots.isEmpty()) {
            this.checkAndWriteToReport(response.statusCode(), "Most recent available slot of today is extracted for scheduling a session. Your slot is "+slots.get(0)+":00 UTC", parameter.isNegative());

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
        this.checkAndWriteToReport(response.statusCode(), "A session is scheduled today at  "+slots.get(0)+":00", parameter.isNegative());
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

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by user " + jsonParser.getJsonData("results.email", ResponseDataType.STRING), parameter.isNegative());

        } else if (user.contains("user")) {

            expertChatApi.doLogIn(TestUserMap.getUserCredentialsByKey(user), false);

            credentials.setuserCredential(TestUserMap.getUserCredentialsByKey(user));

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by user " +
                    TestUserMap.getUserCredentialsByKey(user), parameter.isNegative());


        } else {
            expertChatApi.doLogIn(TestUserMap.getUserCredentialsByKey(user), true);

            credentials.setExpertCredential(TestUserMap.getUserCredentialsByKey(user));

            this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by expert " +
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

        this.info("Get Expert Profile");

        System.out.println("-- Geting expert profile --");

        //  String expertProfileID = getMap ( ).get ( "expertProfileId" );

        if (parameter.isNegative()) {

            expertProfile.getProfileOfExpert("", parameter.isExpert());

        } else {

            expertProfile.getProfileOfExpert("", parameter.isExpert());
        }

        if (parameter.isExpert() == false) {

            this.checkAndWriteToReport(response.statusCode(),
                    "Expert profile loaded for user:- " + credentials.getUserCredential()[0], parameter.isNegative());
        } else {

            this.checkAndWriteToReport(response.statusCode(),
                    "Expert profile loaded for expert:- " + credentials.getExpertCredential()[0], parameter.isNegative());
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
        this.checkAndWriteToReport(response.statusCode(), "A calender  is created successfully ", parameter.isNegative());

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
        String deviceId=null;
        if (parameter.isNegative()) {

            call.registerDevice(json, parameter.isExpert());

            this.checkAndWriteToReport(response.statusCode(), "Device not Registered-- Negative Test case", true);

        } else {

            call.registerDevice(json, parameter.isExpert());

        }

        if (parameter.isExpert()){
             deviceId=getMap().get("ExpertDevice");
        }else {
            deviceId = getMap().get("UserDevice");
        }
        this.checkAndWriteToReport(response.statusCode(), "A Device with id "+deviceId+" is Registered.", false);
    }

    /**
     * @ Get SessionUtil ID
     */

    @Then("it should return session id")
    public void setSessionId() {
        info("Getting session Id from scheduled session");

        String sessionId = getMap().get("scheduled_session_id");

        this.checkAndWriteToReport(response.statusCode(), "Session Id "+sessionId + " is created for this session.", parameter.isNegative());

    }

    /**
     * @ Get SessionUtil Details
     */
    @When("I get the session details")
    @Aliases(values = {"I pass on session id in session details API",
    "I pass on session id in session details API show me estimated revenue of user and expert"})
    public void getSessionDetails() {

        info("Session Details of " + getMap().get("scheduled_session_id"));
        //String sessionID= getMap().get("scheduled_session_id");
        call.getSessionDetails(getMap().get("scheduled_session_id"), parameter.isExpert());

        this.checkAndWriteToReport(response.statusCode(), "Details with id-- " + getMap().get("scheduled_session_id") + " extracted", parameter.isNegative());
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


    @When("I initiate the session")
    @Then("I initiate the session")
       public void validateSessionInitiation() throws  Exception{

        info("Intiating a Session with expert");

        System.out.println("initiating call");

        pcode.convertDateTime();
        call.intiate(getMap().get("scheduled_session_id"), userDeviceId);
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
    @Aliases(values = {"I get a call from user"})
    public boolean isCallArrive(){

        info("Checking if call arrived");
        if(getMap().get("call_status").equals(CallStatus.INITIATED)){

            call.isCallArived();
            isCallArived=true;

        }

        this.AssertAndWriteToReport(isCallArived, "Call arrived at expert end");
        return isCallArived;
    }
/**
 * Cancel the call after initiating--
 */
/*@Then("Cancel the session")
public void cancel(){
    info("Cancelling the session");
    ISACTIONTAKEN=call.isCancelSession();
    this.AssertAndWriteToReport(ISACTIONTAKEN,"Session is cancelled successfully");
}*/

    /**
     *
     */
    @Then("I will accept it")
    @Aliases(values = {"I will accept the call"})
    public void accept(){

        info("Expert accept the call");
       ISACTIONTAKEN =call.isAcceptCall();
       this.AssertAndWriteToReport(ISACTIONTAKEN,"Expert accepted the call");

    }

    /**
     *
     * @param status
     */
    @Then("Call should be in $status status")
    @Aliases(values = {"status should be $status"})
    public void verifyCallStatus(@Named("status")String status){

        info("Verify if call status is  "+status);
        String actual=jsonParser.getJsonData("results.status", ResponseDataType.INT);
        String matcher=null;
        boolean isValidate=false;

        switch (status){
            case("accepted"):   matcher=null;   matcher="2"; //CallStatus.ACCEPTED;
                    break;
            case("disconnected"):  matcher=null;     matcher="4";  //CallStatus.COMPLETED;
                break;
            case("declined"):     matcher=null;          matcher="3"; //CallStatus.DECLINED;
                break;
            case("reconnect"): matcher=null;            matcher="6"; //CallStatus.RECONNECT;
                break;
            case("extended"): matcher=null;             matcher=CallStatus.EXTEND;
                break;
            case ("cancelled"): matcher=null;           matcher=CallStatus.CANCELLED;
                break;

            default: new ExpertChatException("Please provide proper action, action is not in the list- reconnect,declined,disconnected, accepted");
               break;
        }
        if (matcher.equals(actual)){
            isValidate=true;
        }else{
            isValidate=false;
        }

        System.out.println("actual status "+actual+" checking status "+status);
        this.AssertAndWriteToReport(isValidate,"Call is "+"\""+status+"\" successfully");

    }

    /**
     *
     * @param action
     */
    @Then("I will $action the call")
    @Aliases(values = {"I will $action the same call"})
    public void performActionOnRecivedCall(@Named("action")String action){

        info(action+" the call");
        switch (action.toLowerCase()){

            case "disconnect" : ISACTIONTAKEN=call.isDissconnectCall();
                               break;
            case "reconnect" : ISACTIONTAKEN=call.reconnect(getMap().get("scheduled_session_id"));
                               break;
            case "decline"   : ISACTIONTAKEN=call.isDecline();
                               break;
            case "cancel"   : ISACTIONTAKEN=call.isCancelSession();
                               break;
        }

        this.AssertAndWriteToReport(ISACTIONTAKEN,"Call action is-->"+action);
        ISACTIONTAKEN=false;

    }

    /**
     *Reconnect call once user reconnected the call
     */
    @Then("reconnect should be successful")
    @Aliases(values = {"Call should be reconnected sucessfully"})
    public void verifyReconnect(){

        info("Verify reconnect");

        if(getMap().get("call_status").equals(CallStatus.RECONNECT)){
            this.AssertAndWriteToReport(true,"Call is reconnected successfully");
        }else{
            this.AssertAndWriteToReport(false,"Reconnection failed");
        }
    }

    /**
     * Waiting till extension time is reached, Extensible at 5 min prior to scheduled end time.
     */

    @Then("wait for session extenstion")
    @When("wait for session extenstion")
    @Aliases(values = {"I wait for another session extenstion"})
    public void continueSession() throws InterruptedException {

        info("Call is in-progress and waiting for Session extension");
        int duration=Integer.parseInt(getMap().get("scheduled_duration")); //10 min

        SessionUtil session=new SessionUtil();
        String scheduleTime= getMap().get("scheduled_datetime");
        LocalDateTime scheduleTimeJoda = new DateTime(scheduleTime).toLocalDateTime();
        DateTimeFormatter schedule = DateTimeFormat.forPattern("MMM dd yyyy, hh:mm a");
        long scheduleDateTime=session.getTimeInMillis(schedule.print(scheduleTimeJoda),"MMM dd yyyy, hh:mm a");

        long durationToMilli=duration*60000;
        long scheduleEndTimeInMili=(scheduleDateTime+durationToMilli);
        long extensionTimeBeforeEnd=5*60000;
        long extensibleAtInMili=(scheduleEndTimeInMili-extensionTimeBeforeEnd);

        String currentTime=session.getCurrentTimefromServer();
        LocalDateTime serverjodatime = new DateTime(currentTime).toLocalDateTime();
        DateTimeFormatter serverdtfOut = DateTimeFormat.forPattern("MMM dd yyyy, hh:mm a");

        long currentTimeInMilli=session.getTimeInMillis(serverdtfOut.print(serverjodatime), "MMM dd yyyy, hh:mm a");
        long waitingTime= (extensibleAtInMili-currentTimeInMilli);

        info("Waiting time is "+(waitingTime/60000)+" minute for extending call");
        System.out.println("Waiting time is "+(waitingTime/60000)+" minute for extending call");

        String callStatus=getMap().get("call_status");

        if( (currentTimeInMilli<extensibleAtInMili) && (currentTimeInMilli< scheduleEndTimeInMili)&&(callStatus.equals("2"))) {
            /*Wait for extension is reached*/
            Thread.sleep(waitingTime);
        }else{
            this.AssertAndWriteToReport(true,"Session cannot be extended as call is not in accepted state");
        }
       /* isExtensible = call.checkExtension(sessionId);
        this.AssertAndWriteToReport(isExtensible,"Session  can be extended now");*/
       if ((currentTimeInMilli>=extensibleAtInMili) ){
           isWaitOver=true;
           this.AssertAndWriteToReport(isWaitOver,"Extension wait time is over, now can be check for extension");
       }

    }

    /**
     *
     */
    @Then("Retrieve session extension details")
    @When("Retrieve session extension details")
    public void extenssionDetail(){

        if(isWaitOver){
            isExtensible = call.checkExtension(getMap().get("scheduled_session_id"));
        }else{
             System.out.println("Error in waiting time, it comes out without waiting wait time");
            this.AssertAndWriteToReport(isWaitOver);
            return;
        }

        if (isExtensible){
            String availableExtnTime=getMap().get("available_extension_duration");
            String extnPrice=getMap().get("extension_price");
            System.out.println(availableExtnTime+" min is available for $"+extnPrice);
        }
        this.AssertAndWriteToReport(isExtensible,"Session  can be extended now");

    }

    @When("No slot is available for extension")
    @Then("No slot is available for extension")
    public void checkExtensionSlot(){

        if (!isExtensible) {
            this.AssertAndWriteToReport(parameter.isNegative(), getMap().get("extn_error_message"));
        }else{
            this.AssertAndWriteToReport(false,"");
        }

    }

    @Then("User should not allowed to extend the call")
    public void isExtensionAllowed(){

    }


    /**
     * Checking if extension is possible
     */
    @Then("verify if session extension is possible")
    public void verifySessionExtension(){

        isExtensible = call.checkExtension(getMap().get("scheduled_session_id"));
        this.AssertAndWriteToReport(isExtensible,"Session  can be extended now");

    }

    /**
     * If extension is possible than extend the session for desired duration. By default 10.
     * @param duration
     */
    @Then("If possible, Extend the call for $duration min")
    public void extendCall(@Named("duration") String duration){

        info("Extending session--");
        if(isExtensible) {
            call.extendSession(duration);
            // this.AssertAndWriteToReport(true,getMap().get("error_message"));
            String extnPrice = getMap().get("extension_price");
            String extnDuration = getMap().get("extension_time");
            System.out.println("Extn Duration " + extnDuration + " and price " + extnPrice);

        }
        this.checkAndWriteToReport(response.statusCode(),"Session exteded for 10 more minute",parameter.isNegative());

    }

    public static void main(String[] args){



    }


}
