package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;

import javax.xml.ws.Response;

public class CreatePromoCode extends AbstractApiFactory implements ExpertChatEndPoints {

    private ExpertChatApi expertChatApi= new ExpertChatApi();
    private ExpertProfile expertProfile = new ExpertProfile();
    ApiResponse response;

    public void createPromoCode(String json, String type, String pCode){

    }

    public void superLogin(String json, String userType){

        if (json.contains("{") && userType.equals("superUser")) {

            expertChatApi.doLogIn(json, "superUser");

            expertProfile.setExpertProfileID(json);

            System.out.println("Respons code of super admin "+response.statusCode());
            //this.checkAndWriteToReport(response.statusCode(), "Logged in to experChat by expert--" + user, isNegative);

        }
    }
}
