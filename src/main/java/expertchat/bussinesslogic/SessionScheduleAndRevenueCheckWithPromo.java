package expertchat.bussinesslogic;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.params.Credentials;
import expertchat.test.bdd.SessionScheduleAndRevenueCheckWithPromoTC;
import expertchat.util.DatetimeUtility;
import expertchat.util.ResponseParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static expertchat.usermap.TestUserMap.getMap;

public class SessionScheduleAndRevenueCheckWithPromo extends AbstractApiFactory

        implements HTTPCode, ExpertChatEndPoints {

    private ApiResponse response = ApiResponse.getObject();

    DatetimeUtility dateUtil=new DatetimeUtility();

    private ParseResponse jsonParser = new ParseResponse ( response );

    SessionManagement session=SessionManagement.session();

    String expertSlots ;
    String allSlots;

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

     return calenderInUnix; // Returning list of long unix datetime

    }

    public String getAllSlots(String token){

        List<String> slots=new ArrayList<String>();

      //  String expert_id=getMap().get("Expert_Id");

        String api=USER_AVILABLE_SLOTS+"546"+"/";

        response.setResponse(

                this.get(api, token)
        );

        if(response.statusCode()!=HTTP_BAD || response.statusCode()!=HTTP_UNAVAILABLE) {

            new ResponseParser().onSuccess(response.getResponse().asString());

        }


        return allSlots;
    }




    public static void main(String [] args){


        SessionScheduleAndRevenueCheckWithPromo slot=new SessionScheduleAndRevenueCheckWithPromo();

        String u_token="eyJpcF9hZGRyZXNzIjoiNjEuMjQ2LjQ3LjkzIiwidXNlcl9pZCI6NDc1LCJ0aW1lc3RhbXAiOjE1MDY0MTE4OTIuNDQxNDQ4fQ:1dwkY0:Mp9URW5OZ1RuVDrViaaa3FaVjho";

       slot.getAllSlots(u_token);
    }


}
