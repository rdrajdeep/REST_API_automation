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
    List<String> slots=new ArrayList<String>();

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

    public List getAllSlots( String min){

        System.out.println("---Getting all slots---");

        String expert_id=getMap().get("expertId");

        System.out.println("Exprt base Id "+expert_id);
        //String api=USER_AVILABLE_SLOTS+expert_id+"/";
        String api=USER_AVILABLE_SLOTS+"546"+"/";

        response.setResponse(

                //this.get(api, token)
                this.get(api, session.getUserToken())
        );

        if(response.statusCode()!=HTTP_BAD || response.statusCode()!=HTTP_UNAVAILABLE) {

            slots= new ResponseParser().onSuccess(response.getResponse().asString());

        }

        System.out.println("*****I am in getAllSlots>>>>***"+slots);
        //Getting first first time slot

        return slots;
    }




  /*  public static void main(String [] args){

        SessionScheduleAndRevenueCheckWithPromo slot=new SessionScheduleAndRevenueCheckWithPromo();

        String u_token="eyJpcF9hZGRyZXNzIjoiNjEuMjQ2LjQ3LjkzIiwidXNlcl9pZCI6MywidGltZXN0YW1wIjoxNTA2NDI2MTE1LjE3NzkwNn0:1dwoFP:F8za8I4NyB-rQh8b_3ChVPhVHn4";
              //  List<String> aSlot=null;
        slot.getAllSlots(u_token,"10");


    }*/


}
