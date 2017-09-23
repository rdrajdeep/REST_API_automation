package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.util.DatetimeUtility;

import java.util.ArrayList;
import java.util.List;

import static expertchat.usermap.TestUserMap.getMap;

public class SessionScheduleAndRevenueCheckWithPromo extends AbstractApiFactory
        implements HTTPCode, ExpertChatEndPoints {

    private ApiResponse response = ApiResponse.getObject();

    DatetimeUtility dateUtil=new DatetimeUtility();

    private ParseResponse jsonParser = new ParseResponse ( response );

    SessionManagement session=SessionManagement.session();

    String expertSlots ;

    public void createPromoCode(String json){

         response.setResponse(this.post(json,ExpertChatEndPoints.PROMO_CODE, SessionManagement.session ( ).getUserToken(),true));

         //response.printResponse();

        if(response.statusCode()==HTTP_OK|| response.statusCode()==HTTP_ACCEPTED)

            System.out.println("Promo code created Success fully");
        
        else
            System.out.println("Ahh Ohh! Something went wrong");

    }


    public List<Long> getaSlot(){

        String url= SEARCH_BY_EXPERT_ID+ getMap().get("expertProfileId");
        String temp=null;
        String calenderDate=null;
        List<String> calender=new ArrayList<>();
        List<Long> calenderInUnix=new ArrayList<>();
        response.setResponse(this.get(url, session.getUserToken()));

        //response.printResponse();
        System.out.println("***This are available slots in UTC time****"+response.getResponse().jsonPath().getList("results.calendars"));


        if (response.statusCode()==HTTP_ACCEPTED || response.statusCode()==HTTP_OK){

            calender=response.getResponse().jsonPath().getList("results.calendars");

        }
        for (String item:calender){
            temp=item;
            calenderDate=temp+"Z";
            calenderInUnix.add(dateUtil.convertToUnixTimestamp(calenderDate));
        }

     return calenderInUnix; // Returning list of long date time

    }


}
