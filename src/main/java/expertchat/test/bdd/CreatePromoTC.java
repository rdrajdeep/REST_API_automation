package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.AbstractApiFactory;
import expertchat.bussinesslogic.CreatePromoCode;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.eclipse.jetty.util.annotation.Name;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class CreatePromoTC extends AbstractSteps {

    public CreatePromoTC(ExtentReports reports, String casName) {
        super(reports, casName);
    }

    CreatePromoCode pcode = new CreatePromoCode();

    @When("login with super user $json")
    public void superUserlogin(@Named("json") String json){

        pcode.superLogin(json, "superUser");

    }

    /*
    * @param = promocode in Json
    * */
    @Then("create promocode $promoCode")
    public void createPromoCode(@Named("promoCode") String promCode){



    }
}
