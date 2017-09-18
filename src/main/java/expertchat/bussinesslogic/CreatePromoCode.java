package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.session.SessionManagement;

import javax.xml.ws.Response;

public class CreatePromoCode extends AbstractApiFactory implements ExpertChatEndPoints {

    private ExpertChatApi expertChatApi= new ExpertChatApi();
    private ExpertProfile expertProfile = new ExpertProfile();
    ApiResponse response;
    SessionManagement session;

    public void createPromoCode(String json){

        response.setResponse(this.post(json,ExpertChatEndPoints.PROMO_CODE));
        if(response.statusCode()==204|| response.statusCode()==200){
            System.out.println("Promocode created");
        }


    }

}
