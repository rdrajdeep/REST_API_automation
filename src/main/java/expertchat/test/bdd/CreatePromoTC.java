package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.session.SessionManagement;
import expertchat.bussinesslogic.Calling;
import expertchat.bussinesslogic.CreatePromoCode;
import expertchat.bussinesslogic.ExpertChatApi;
import expertchat.params.Credentials;
import expertchat.params.parameter;
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
    Credentials credentials = Credentials.getCredentials();
    SessionManagement session;
    private ApiResponse response = ApiResponse.getObject ();
    private Calling call= new Calling();

    @When("I am rajdeep")
    public void check(){
        System.out.println("My name is rajdeep");
    }

    @When("login as super user $json")
    @Then("login as user $json")
        public void superUserlogin(@Named("json") String json){

        expertChatApi.doLogIn(json,false);
        credentials.setuserCredential(json);
        this.checkAndWriteToReport(response.statusCode(), "Logged in as Super User" + json, parameter.isNegative ());

    }

    /*
    * @param = All details of promocode as Json
    * */
    @Then("create promocode $promoCode")
    public void PromoCode(@Named("promoCode") String promoCode){

        //pcode.createPromoCode(promoCode);
        //this.checkAndWriteToReport(response.statusCode(), "New Promo code " + promoCode +"Created", parameter.isNegative ());
        return;
    }

    @Then ("Search an expert with id $expertId and its slots $datetime")
    public void searchExpert(@Named("expertId") String expertId,
                             @Named("datetime") String datetime){

        System.out.println("Search expert initiated");
        pcode.searchExperts(expertId,datetime);

    }
    /*
    * @Param promo code
    * */
    @Then("schedule a call with expert $expertId using promocode $promoCode")
    public void scheduleCall(@Named("promoCode") String promoCode,
                             @Named("expertId") int expertId){
           // call.scheduleSession();

    }
}
