package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.session.SessionManagement;
import expertchat.util.DatetimeUtility;
import expertchat.util.ResponseParser;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import static expertchat.usermap.TestUserMap.getMap;

public class SessionUtil extends AbstractApiFactory implements HTTPCode, ExpertChatEndPoints {

    private ApiResponse response = ApiResponse.getObject();
    private List<String> slots=new ArrayList<String>();
    private DatetimeUtility dateUtil=new DatetimeUtility();
    private ParseResponse jsonParser = new ParseResponse ( response );
    private SessionManagement session=SessionManagement.session();
    private LocalTime localTime = LocalTime.now();
    private int startHour;
    private int startMint;
    private int endHour;
    private int endMint;
    private long inMiliisSchedule;

    public long getScheduleTimeInMillSecond(){

        return inMiliisSchedule;

    }


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



    public String getCurrentTime(){

        response.setResponse(
                this.get("http://connect.qa.experchat.com/now/")
        );

        return response.getResponse().jsonPath().getString("results.datetime");

    }

  /*  public static void main(String [] args){

        SessionUtil slot=new SessionUtil();

        String u_token="eyJpcF9hZGRyZXNzIjoiNjEuMjQ2LjQ3LjkzIiwidXNlcl9pZCI6MywidGltZXN0YW1wIjoxNTA2NDI2MTE1LjE3NzkwNn0:1dwoFP:F8za8I4NyB-rQh8b_3ChVPhVHn4";
              //  List<String> aSlot=null;
        slot.getAllSlots(u_token,"10");


    }*/


   public void convertDateTime() throws Exception{

       this.getCurrentTime();

       String scheduleTime= getMap().get("scheduled_datetime");
      // String scheduleTime= "2017-09-27T12:00:00Z";

       LocalDateTime serverjodatime = new DateTime(this.getCurrentTime()).toLocalDateTime();

       DateTimeFormatter serverdtfOut = DateTimeFormat.forPattern("MMM dd yyyy, hh:mm a");

       LocalDateTime scheduleTimeJoda = new DateTime(scheduleTime).toLocalDateTime();

       DateTimeFormatter schedule = DateTimeFormat.forPattern("MMM dd yyyy, hh:mm a");

       long inmillisLocal=getTimeInMillis(serverdtfOut.print(serverjodatime),"MMM dd yyyy, hh:mm a");

       inMiliisSchedule = getTimeInMillis(schedule.print(scheduleTimeJoda),"MMM dd yyyy, hh:mm a");

       if(inmillisLocal <=inMiliisSchedule){

          long diff=inMiliisSchedule-inmillisLocal;

           System.out.println(diff/60000+"-Mint wait to call");

           Thread.sleep(diff);
       }


   }


   /**/
    public long getTimeInMillis(String dateTime, String inputFormat){

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(inputFormat, Locale.getDefault());

            Date date = sdf.parse(dateTime);

            return date.getTime();

        } catch (ParseException e) {

            e.printStackTrace();
            return 0;
        }
    }


    /**
     *
     * @param args
     * @throws Exception
     */

   public static void main(String[] args) throws Exception{

       int duration=Integer.parseInt("10"); //10 min

       SessionUtil session=new SessionUtil();

       session.convertDateTime();
       long scheduleDateTime=session.getScheduleTimeInMillSecond();
       System.out.println(scheduleDateTime+"  Schedule date in mili");
       long durationToMilli=duration*60000;

       long endTime=(scheduleDateTime+durationToMilli);
       System.out.println(scheduleDateTime+"---"+endTime+"  End time after+ 10");
       long extensionTimeBeforeEnd=5*60000;
       long extensionTime=endTime-extensionTimeBeforeEnd;

       String currentTime=session.getCurrentTime();

       LocalDateTime serverjodatime = new DateTime(currentTime).toLocalDateTime();
       DateTimeFormatter serverdtfOut = DateTimeFormat.forPattern("MMM dd yyyy, hh:mm a");

       long currentTimeInMilli=session.getTimeInMillis(serverdtfOut.print(serverjodatime), "MMM dd yyyy, hh:mm a");

       System.out.println(currentTimeInMilli+"Current time "+extensionTime+" extension time");
       if(currentTimeInMilli>=extensionTime && currentTimeInMilli< endTime){
           System.out.println("TEST sucess");
           System.out.println(currentTimeInMilli-extensionTime);
       }
       else{
           System.out.println("False");

       }



   }


   public String getStrtTimeForCalender() throws Exception{

      startHour =localTime.getHour();

      startMint =localTime.getMinute();

      if(startMint==60){

          startHour++;
          startMint=0;
      }

      while (startMint % 5 !=0){

          startMint=startMint+1;
      }


    return new String(String.valueOf(startHour)+":"+String.valueOf(startMint)+":00");

   }


    public String getEndTimeForCalender(){

       if((startMint +20)>60){

           endHour = startHour +1;
           endMint =(startMint +20)- startMint;

       }else if((startMint +20)==60) {

           endHour = startHour +1;
           endMint =0;

       } else {

               endHour=startHour;
               endMint=startMint +20;

           }

        return new String(String.valueOf(endHour)+":"+String.valueOf(endMint)+":00");
    }

    public int today(){

        Calendar calendar=Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if(day==7){

            return 1;

        }else {

          return  day-1;
        }
    }


}

