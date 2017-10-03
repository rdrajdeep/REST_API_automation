package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;

/**
 * Created by joker on 12/4/17.
 */
public class Calender extends AbstractApiFactory implements HTTPCode, ExpertChatEndPoints {

    private String calenderId;
    private ApiResponse response=ApiResponse.getObject();
    private SessionManagement  session=SessionManagement.session();
    private ParseResponse pr=new ParseResponse(response);

    /****
     *
     * @param
     */
    public void createCalender(int duration) throws  Exception{

        SessionUtil obj= new SessionUtil();

        String json="{\n" +
                "    \"title\": \"test3\",\n" +
                "    \"start_time\": \""+obj.getStrtTimeForCalender()+"\",\n" +
                "    \"end_time\": \""+obj.getEndTimeForCalender(duration)+"\",\n" +
                "    \"timezone\": \"Asia/Kolkata\",\n" +
                "    \"week_days\": [\n" +
                "        "+obj.today()+" \n" +
                "    ]\n" +
                "}";


        System.out.println("----->"+json);

        response.setResponse(
                this.post(json, SLOTS, session.getExpertToken ())
        );

        response.printResponse();

        System.out.println(response.statusCode());
        if(response.statusCode()==HTTP_OK || response.statusCode()==HTTP_ACCEPTED ){

            calenderId=pr.getJsonData("results.id", ResponseDataType.INT);
        }
    }

    /**
     * Testing purpose only
     * */
    public void appendExistingCalender() throws  Exception{

        SessionUtil obj= new SessionUtil();

        String json="{\n" +
                "    \"results\": {\n" +
                "        \"id\": 350,\n" +
                "        \"title\": \"test3\",\n" +
                "        \"start_time\":\""+obj.getStrtTimeForCalender()+"\",\n" +
                "        \"end_time\": \""+obj.getEndTimeForCalender(20)+"\",\n" +
                "        \"timezone\": \"Asia/Kolkata\",\n" +
                "        \"week_days\": [\n" +
                "            "+obj.today()+"\n" +
                "        ]\n" +
                "    }\n" +
                "}";


        System.out.println("-----> "+json);

        response.setResponse(
                this.post(json, SLOTS+"/350/", session.getExpertToken ())
        );

        response.printResponse();

        System.out.println(response.statusCode());
        if(response.statusCode()==HTTP_OK || response.statusCode()==HTTP_ACCEPTED ){

            calenderId=pr.getJsonData("results.id", ResponseDataType.INT);
        }
    }


    public void getCalender(String id){

        response.setResponse(
                this.get(SLOTS+id+"/",session.getExpertToken ())
        );
    }

    public void updateCalender(String json, String id){

        response.setResponse(
                this.put( json, SLOTS+id+"/", session.getExpertToken ())
        );
    }

    public String getCalenderId(){

        return calenderId;
    }

    public void getAvilableSlot(String epId) {

        response.setResponse(
                this.get(AVILABLE_SLOTS+epId+"/",session.getExpertToken ())
        );

        String reponse=response.getResponse ().asString ().toString ();


    }

}