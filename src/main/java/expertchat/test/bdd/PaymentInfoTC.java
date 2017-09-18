package expertchat.test.bdd;// Created by Kishor on 4/25/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.bussinesslogic.Account;
import expertchat.params.parameter;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class PaymentInfoTC  extends AbstractSteps{

    public PaymentInfoTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private Account account = new Account();

    /**
     * Payment account test cases
     */

    @When ("create a payment account as $json")
    @Then ("create a payment account as $json")
    public void createAccount(@Named ("json") String json) {

        info("Creating payment account");

        if (parameter.isNegative ()) {

            account.createAccount(json);

        } else {
            account.createAccount(json);
        }
        this.checkAndWriteToReport(response.statusCode(), "Account created ", parameter.isNegative ());

        responseLogger.writeResponseAsLog("Payment Account");
    }

    @Then("get the account")
    public void getAccount() {

        info("listing the payment account created");

        String accountId = account.getAccountId();

        account.getAccount(accountId);

        this.checkAndWriteToReport(response.statusCode(), "Account listed", parameter.isNegative ());

        responseLogger.writeResponseAsLog("get Payment Account");
    }

}
