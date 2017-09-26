package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.bussinesslogic.*;
import expertchat.params.Credentials;
import expertchat.params.parameter;
import expertchat.usermap.TestUserMap;
import expertchat.util.DatetimeUtility;
import org.apache.commons.collections.FastArrayList;
import org.jbehave.core.annotations.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static expertchat.usermap.TestUserMap.getMap;

/*@
 * This serve the purpose of creating PROMO CODE,
 * Scheduling a session with that PROMO CODE
 * Validate the esitimated Revenue calculation
 * ** Author: Rajdeep**
 */

public class SessionScheduleAndRevenueCheckWithPromoTC extends AbstractSteps {

    public SessionScheduleAndRevenueCheckWithPromoTC(ExtentReports reports, String casName) {
        super(reports, casName);
    }

    expertchat.bussinesslogic.SessionScheduleAndRevenueCheckWithPromo pcode = new expertchat.bussinesslogic.SessionScheduleAndRevenueCheckWithPromo();

    ExpertChatApi expertChatApi = new ExpertChatApi();

    Credentials credentials = Credentials.getCredentials();

    private ApiResponse response = ApiResponse.getObject();

    private Calling call = new Calling();

    private ExpertProfile expertProfile = new ExpertProfile();

    private Calender calender = new Calender();

    private List<Long> slots = new ArrayList<>();

    private String sessionId = null;

    private String userDeviceId = null;

    private String scheduleDate = null;

    DatetimeUtility dateUtil=new DatetimeUtility();


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
        slots=pcode.getaSlot();

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

        List<Long> unixDateTimeList=new FastArrayList();

        long currentTime=dateUtil.convertToUnixTimestamp(dateUtil.ISTtoUTC(dateUtil.currentDate()));
        long slotInUnix=0;

       /* for(long datetime:slots){

            unixDateTimeList.add(datetime);

        }*/
       List<Long> sortedList= new ArrayList<Long>();

     /* Collections.sort(slots);
        System.out.println("**After sorting--"+slots);
*/
        for (long slot: slots){
            if(slot>currentTime){
                slotInUnix=slot;
                System.out.println("Slot time in utcUnix date from API "+ slot);
                break;
            }else continue;
        }

        System.out.println("Current time in UTC unix "+ currentTime);
        System.out.println("Selected Slot in UTC Unix "+ slotInUnix);

        String slotInUTC = dateUtil.convertUnixToOriginDate(slotInUnix);

        System.out.println("UTC date after convert from unix "+slotInUTC);

      /*  call.scheduleSession(slotInUTC , code, duration);

        response.printResponse();

        this.checkAndWriteToReport(response.statusCode(), "Session with id--" + getMap().get("scheduled_session_id") + " created", parameter.isNegative());

        userDeviceId = jsonParser.getJsonData("results.user_device", ResponseDataType.STRING);

        getMap().put(jsonParser.getJsonData("results.id", ResponseDataType.STRING), "slot+\"Z\"");
*/

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
    @Alias("i am creating a calender as $json")
    public void calender(@Named("json") String json) {

        System.out.println("--Creating a calender  --");

        info("Creating a calender...");

        if (parameter.isNegative()) {
            calender.createCalender(json);
        } else {
            calender.createCalender(json);
        }
        this.checkAndWriteToReport(response.statusCode(), "Calender Created", parameter.isNegative());

    }

    /*@
    * Registering a device with the given JSON data from story
    *
     */

    @When("register a device as $json")
    @Then("register a device as $json")
    @Alias("i register a device as $json")
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
     * @ Get Session ID
     */

    @Then("it should return session id")
    public void setSessionId() {

        info("Session id print");

        sessionId = getMap().get("scheduled_session_id");

        this.checkAndWriteToReport(response.statusCode(), sessionId + "-- Created", parameter.isNegative());

    }

    /**
     * @ Get Session Details
     */
    @When("I get the session details")
    @Alias("I pass on session id in session details API")
    public void getSessionDetails() {

        info("Session Details of " + sessionId);

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

            this.AssertAndWriteToReport(true, "Session final status is " + actual);
        } else {

            this.AssertAndWriteToReport(false, "Session final status is " + actual);
        }
    }


    @When("i initiate the session")
    public void validateSessionInitiation() {

        info("Intiating a Call/Session");

        System.out.println("initiating call");

       String SlotinUTC= getMap().get("scheduled_datetime");

        System.out.println("schedule time in utc: "+SlotinUTC);

        long slotinUnix=dateUtil.convertToUnixTimestamp(SlotinUTC);
        long currentTimeUnix=dateUtil.convertToUnixTimestamp(dateUtil.ISTtoUTC(dateUtil.currentDate()));
        long diff=slotinUnix-currentTimeUnix;
        System.out.println("Looping for "+diff+" times");
        String statusCode = null;
        for (long i=0;i<=diff;i++){

            call.intiate(sessionId, userDeviceId);

            statusCode=jsonParser.getJsonData("results.status", ResponseDataType.INT);

            if (!statusCode.equals("2")){

                currentTimeUnix=dateUtil.convertToUnixTimestamp(dateUtil.ISTtoUTC(dateUtil.currentDate()));
                diff=slotinUnix-currentTimeUnix;
                System.out.println("Loop "+i+" ->> Schedule time is still in future date");

                continue;

            }else{

                this.checkAndWriteToReport(response.statusCode(), "Call is successfully initiated", parameter.isNegative());
                System.out.println("Call is successfully initiated");
                break;
            }
        }

    }


    @Then("validate that session cannot be initiated before scheduled time")
    public void validateCall() {
        String statusCode = jsonParser.getJsonData("results.status", ResponseDataType.INT);
        if (!statusCode.equals("2")) {
            this.AssertAndWriteToReport(true, "Since scheduled date is in future date, hence unable to initiate call");
        }
    }

    @When("I schedule a session 10 min prior to available slot for duration 20 min")
    public void schedule(){

    }
}
