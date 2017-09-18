package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.bussinesslogic.PhoneVerification;
import expertchat.params.parameter;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class PhoneVerificationTC  extends AbstractSteps{

    public PhoneVerificationTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private PhoneVerification phone = new PhoneVerification();

    /* Phone code verification test cases*/

    @When ("we provide phone number as $phone")
    @Then ("we provide phone number as $phone")
    public void sendCode(@Named ("phone") String phoneNo) {

        this.info("Sending phone code--" + phoneNo);

        if (parameter.isNegative ()) {

            phone.phoneCodeSend(phoneNo, parameter.isExpert ());

            responseLogger.writeResponseAsLog("Phone code sent");

        } else {
            phone.phoneCodeSend(phoneNo, parameter.isExpert ());

            this.checkAndWriteToReport(response.statusCode(),
                    "Phone code sent", false);

            responseLogger.writeResponseAsLog("Phone code sent");

            /*Phone code resent*/

            this.info("Resending phone code--" + phoneNo);

            phone.phoneCodeResend(phoneNo, parameter.isExpert ());

            this.checkAndWriteToReport(response.statusCode(),
                    "Phone code resent", parameter.isNegative ());

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

        phone.verfiyPhone(phone.getCode(), parameter.isExpert ());

        this.checkAndWriteToReport(response.statusCode(),
                "Mobile no verified", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Mobile no verify");
    }

    @Then("phone should not get Verified")
    public void negative(){

        if(parameter.isNegative ()){

            this.checkAndWriteToReport ( response.statusCode () , "Not verified", true);
        }
    }
}
