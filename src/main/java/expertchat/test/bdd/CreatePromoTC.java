package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.bussinesslogic.CreatePromoCode;
import expertchat.bussinesslogic.ExpertChatApi;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.eclipse.jetty.util.annotation.Name;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import javax.xml.ws.Response;

public class CreatePromoTC extends AbstractSteps {

    public CreatePromoTC(ExtentReports reports, String casName) {
        super(reports, casName);
    }

    CreatePromoCode pcode = new CreatePromoCode();
    ExpertChatApi expertChatApi = new ExpertChatApi();
    ApiResponse  response;

    @When("login with super user $json")
    public void superUserlogin(@Named("json") String json){
    expertChatApi.doLogIn(json,false);
        System.out.println("Api response code "+response.statusCode());

    }

    /*
    * @param = promocode in Json
    * */
    @Then("create promocode $promoCode")
    public void PromoCode(@Named("promoCode") String promCode){

    pcode.createPromoCode(promCode);

    }
}
