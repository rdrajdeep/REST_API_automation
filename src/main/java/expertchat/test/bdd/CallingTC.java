package expertchat.test.bdd;// Created by Kishor on 4/25/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.bussinesslogic.Calling;
import expertchat.params.parameter;
import org.jbehave.core.annotations.*;

import static expertchat.usermap.TestUserMap.getMap;

public class CallingTC extends AbstractSteps{

    public CallingTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private Calling call = new Calling();

    /**
     * register a device
     */
    @Then ("test")
    @When ("test")
    public void test(){

            System.out.println ( "dsdasdasd" );
    }

    /**
     * Test cases to drive the Calling API
     */
    /*Calling API Test cases*/
    @When("register a device as $json")
    @Then("register a device as $json")
    public void registerDevice(@Named ("json") String json) {

        if (parameter.isNegative ()) {

            call.registerDevice(json, parameter.isExpert ());

            checkAndWriteToReport(response.statusCode(), "Device not Registered--Negative Test case", true);
        } else {

            call.registerDevice(json, parameter.isExpert ());

            responseLogger.writeResponseAsLog("Register Device");
        }

        checkAndWriteToReport(response.statusCode(), "Device Registered", false);
    }


    @Then("initiate a call of scheduled_duration $time")
    public void initiateCall(@Named("time") String time) {

        info("Initiating a call to expert...");

        if (parameter.isNegative ()) {

            call.doCall(time);

            responseLogger.writeResponseAsLog("Call initiated by user-Negative");

        } else {

            call.doCall(time);

            responseLogger.writeResponseAsLog("Call initiated by user-Negative");

        }

        checkAndWriteToReport(response.statusCode(), "Call initiated", parameter.isNegative ());
    }

    /**
     *
     */
     @Then("initiate the call")
     public void intiate(){

         if(parameter.isNegative ()){

             call.intiate ("1");
         }else {

             call.intiate ("1");
         }

         this.checkAndWriteToReport ( response.statusCode (), "Call Initiated" , parameter.isNegative ());
     }

    /**
     *
     */
    @Then("accept the call")
    public void acceptCall() {

        this.info("accepting Call...");

        call.isAcceptCall();

        this.checkAndWriteToReport(response.statusCode(),
                "Call Accepted", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Accept Call");
    }

    /**
     *
     */
    @Then("decline the call")
    public void declineCall() {

        this.info("Declining a Call");

        call.isDecline();

        this.checkAndWriteToReport(response.statusCode(),
                "Call Declined", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Decline Call");
    }

    @Then("delay the call")
    public void delayCall() {

        this.info("Delaying the call");

        call.isDelay();

        this.checkAndWriteToReport(response.statusCode(),
                "Call delayed", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Delay Call");
    }

    @Then("disconnect the call")
    public void disconnectCall() {

        this.info("Disconnecting a call...");

        call.isDissconnectCall();

        this.checkAndWriteToReport(response.statusCode(),

                "Call Disconnected", parameter.isNegative ());

    }

    @Then("provide review as $review")
    public void review(@Named ( "review" )String review){

        if(parameter.isNegative ()){

            call.addReview ( review );
        }else {

            call.addReview ( review );
        }

        this.checkAndWriteToReport ( response.statusCode (), "Review added" , parameter.isNegative ());
    }

    @Then("schedule a session")
    public void scheduleSession1(){

        if(parameter.isNegative ()){

            call.scheduleSession ( );
        }else {

            call.scheduleSession ();
        }

        this.checkAndWriteToReport ( response.statusCode (), "Session scheduled", parameter.isNegative ());
    }

    @Then("schedule a session again")
    public void scheduleSession2(){

        if(parameter.isNegative ()){

            call.scheduleSession2 ( );
        }else {

            call.scheduleSession2 ();
        }

        this.checkAndWriteToReport ( response.statusCode (), "Session scheduled", parameter.isNegative ());
    }

    @Then("cancel the session")
    public void cancelSession(){

        boolean isCancel=call.isCancelSession ();

        if(isCancel){

            AssertAndWriteToReport ( isCancel, "Session canceled");

        }else if(parameter.isNegative () && isCancel==false) {

            AssertAndWriteToReport ( isCancel, "Negative Test--Session could not be canceled");

        }else {
            AssertAndWriteToReport ( false, "" );
        }
    }

    @Then("extend an ongoing session by scheduled_duration of $time")
    public void extendSession(@Named ( "time" )String time){

        if(parameter.isNegative ()){

            call.extendSession ( time );
        }else {

            call.extendSession ( time );
        }

        this.checkAndWriteToReport (response.statusCode (), "Session extended by\t\t"+time, parameter.isNegative ());
    }

    @Then ( "get all the past session and verify" )
    public void getPastSession() {

        String dateTime = null;

        if ( parameter.isNegative ( ) ) {

            call.getPastSession ( );
        } else {

            call.getPastSession ( );

            dateTime = jsonParser.getJsonData ( "results.scheduled_datetime", ResponseDataType.STRING );
        }

        if ( getMap ( ).get ( "scheduled_datetime" ).equals ( dateTime ) ) {

            this.checkAndWriteToReport ( response.statusCode (), "Pst session loaded", parameter.isNegative ());

        }else {

            this.fail ("Verification failed");
        }
    }

    @Then ( "get all the future session and verify" )
    public void getFutureSession(){

        String dateTime=null;

        if(parameter.isNegative ()){

            call.getFutureSession ();

        }else {

            call.getFutureSession ();

            dateTime = jsonParser.getJsonData ( "results.scheduled_datetime", ResponseDataType.STRING );
        }
        if ( getMap ().get ("scheduled_datetime").equals(dateTime)) {

            this.checkAndWriteToReport (response.statusCode (), "Future session loaded", parameter.isNegative());
        }else {
            this.fail ("Verification failed");
        }
    }

    /**
     *
     * @param time
     * @param money
     */
    @Then("create a session for $time mint and the revenue should be $money $")
    public void checkRevenue(@Named ( "time" )String time,
                            @Named ( "money" )String money){

        info("Checking revenue");

    }

    /**
     *
     * @param
     */

    @Then("check the revenue")
    public void revenue(){

        info("checking revenue");
        String r=call.checkRevenue();
        this.checkAndWriteToReport (response.statusCode (), "Revenue is->"+r, parameter.isNegative());
    }

    @Then("extend the session by $time mint and check the revenue")
    public void revenueAfterExtend(@Named ( "time" )String time){


    }

    @Then("check the session status")
    public void sessionStatus(){

        info("checking session status");

        String status=call.sessionStatus();

        this.checkAndWriteToReport (response.statusCode (), "Session status is->"+status, parameter.isNegative());
    }

    @Then("check the notification")
    public void checkNotification(){

        info("Getting all notifications");

        String notifications=call.getAllNotifications();

        this.checkAndWriteToReport (response.statusCode (), "Notification get-->"+notifications, parameter.isNegative());
    }

    @Then("create a card with $nonce")
    public void cardCreation(@Named("nonce")String nonce){

        info ( "Creating a card" );

        call.createCard (nonce);

        this.checkAndWriteToReport ( response.statusCode (), "Card created ", false );
    }

}
