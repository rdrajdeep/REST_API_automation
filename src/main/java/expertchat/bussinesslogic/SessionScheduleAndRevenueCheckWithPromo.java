package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import static expertchat.usermap.TestUserMap.getMap;


public class SessionScheduleAndRevenueCheckWithPromo extends AbstractApiFactory
        implements HTTPCode, ExpertChatEndPoints {

    private ApiResponse response = ApiResponse.getObject();

    private ParseResponse jsonParser = new ParseResponse ( response );

    SessionManagement session=SessionManagement.session();

    String expertSlots ;

    public void createPromoCode(String json){
         response.setResponse(this.post(json,ExpertChatEndPoints.PROMO_CODE, SessionManagement.session ( ).getUserToken(),true));
         response.printResponse();

        if(response.statusCode()==HTTP_OK|| response.statusCode()==HTTP_ACCEPTED)
            System.out.println("Promo code created Success fully");
        
        else
            System.out.println("Ahh Ohh! Something went wrong");

    }


    public String getaSlot(){

        String url= SEARCH_BY_EXPERT_ID+ getMap().get("expertProfileId");
        String slot=null;

        response.setResponse(this.get(url, session.getUserToken())); //,false, "Search Api"));

        response.printResponse();

        System.out.println("Status code=== "+response.statusCode());

        if (response.statusCode()==HTTP_ACCEPTED || response.statusCode()==HTTP_OK){

            slot=jsonParser.getJsonData("results.calendars[0]", ResponseDataType.STRING);

        }

     return slot;

    }


}
