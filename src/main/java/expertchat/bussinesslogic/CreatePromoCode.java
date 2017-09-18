package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.session.SessionManagement;

import javax.xml.ws.Response;

public class CreatePromoCode extends AbstractApiFactory implements HTTPCode, ExpertChatEndPoints {

    private ExpertChatApi expertChatApi= new ExpertChatApi();
    private ExpertProfile expertProfile = new ExpertProfile();
    private ApiResponse response = ApiResponse.getObject();
    SessionManagement session;

    public void createPromoCode(String json){
         response.setResponse(this.post(json,ExpertChatEndPoints.PROMO_CODE, SessionManagement.session ( ).getUserToken(),true));
         response.printResponse();

        if(response.statusCode()==HTTP_OK|| response.statusCode()==HTTP_ACCEPTED)
            System.out.println("Promo code created Success fully");
        
        else
            System.out.println("Ahh Ohh! Something went wrong");


    }

}
