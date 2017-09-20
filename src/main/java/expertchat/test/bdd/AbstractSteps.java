package expertchat.test.bdd;

// Created by Kishor on 1/25/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.session.SessionManagement;
import expertchat.report.Report;
import expertchat.util.ResponseLogger;
import org.jbehave.core.annotations.*;
import expertchat.params.parameter;


public abstract class AbstractSteps extends Report {

    protected ApiResponse response = ApiResponse.getObject();
    protected ParseResponse jsonParser = new ParseResponse(response);
    protected ResponseLogger responseLogger = new ResponseLogger(jsonParser);



    protected AbstractSteps ( ExtentReports reports, String casName ) {

        super ( reports, casName );
    }
    @Given ("complete $flow")
    public void flow(@Named ("flow") String flow) {

        info("...." + flow + "....");
    }

    @Given("an user")
    @Then ("an user")
    @When ("an user")
    public void user() {

        System.out.println ( "I am user now" );
        parameter.setExpert ( false );
    }
    /*@
    * Working as an Exper
     */

    @Given("an expert")
    @Then("an expert")
    @When("an expert")
    public void expert() {
        System.out.println ( "I am expert now" );
        parameter.setExpert ( true );
    }

    @Given("negative scenario")
    public void negative() {

        parameter.setIsNegative ( true );
    }

    @Then("should not $allowed")
    public void negativeCases() {

        this.checkAndWriteToReport(response.statusCode(), jsonParser.printError(), true);
    }


    @Then("check error code $code")
    public void check_error_code(@Named("code") String code) {

        this.checkErrorCode(jsonParser.serverStatusCode(), code);
    }

    @Then("check non-field error code $code")
    public void check_non_filed_error_code(@Named("code") String code) {

        this.checkNonFiledError(response.statusCode(),
                jsonParser.getNonFieldErrorCode(), code);
    }


    @Then("check success code $code")
    public void check_success_code(@Named("code") String code) {

        this.checkSuccessCode(response.statusCode(), jsonParser.getSuccessCode(), code, false);
    }

    @Then("logout")
    @When("logout")
    @Aliases ( values = {"logout the expert","logout the user"})
    public void logout() {

        SessionManagement.session().setExpertToken (null);

        if (SessionManagement.session().getExpertToken () == null) {

            info("Logout from the system");
        }
    }

}
