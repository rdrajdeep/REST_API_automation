package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;

import javax.xml.ws.Response;

public class CreatePromoCode extends AbstractApiFactory implements HTTPCode, ExpertChatEndPoints {

    private ExpertChatApi expertChatApi= new ExpertChatApi();
    private ExpertProfile expertProfile = new ExpertProfile();
    private ApiResponse response = ApiResponse.getObject();
    private ParseResponse jsonParser = new ParseResponse ( response );
    SessionManagement session;
    String expertSlots;

    public void createPromoCode(String json){
         response.setResponse(this.post(json,ExpertChatEndPoints.PROMO_CODE, SessionManagement.session ( ).getUserToken(),true));
         response.printResponse();

        if(response.statusCode()==HTTP_OK|| response.statusCode()==HTTP_ACCEPTED)
            System.out.println("Promo code created Success fully");
        
        else
            System.out.println("Ahh Ohh! Something went wrong");

    }

    public void searchExperts(String expertId, String datetime){

        String url= "experts/"+expertId+"/";

        response.setResponse(this.get(url, SessionManagement.session().getUserToken(),false, "Search Api"));
        System.out.println("Printing response**");
        //response.printResponse();
        System.out.println("Status code=== "+response.statusCode());

        if (response.statusCode()==HTTP_ACCEPTED || response.statusCode()==HTTP_OK){

            System.out.println("Printing search result");

            expertSlots=jsonParser.getJsonData("results", ResponseDataType.STRING);
            System.out.println(expertSlots);

        }
       // response.printResponse();

    }
}
