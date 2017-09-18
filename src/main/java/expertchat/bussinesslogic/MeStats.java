package expertchat.bussinesslogic;// Created by Kishor on 4/13/2017.

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;

/* Get the states of expert*/

public class MeStats extends AbstractApiFactory implements HTTPCode, ExpertChatEndPoints {

    private ApiResponse response=ApiResponse.getObject ();
    private ParseResponse pr=new ParseResponse ( response );
    private SessionManagement session=SessionManagement.session ();
    private String numProfileVisit;
    private String numSession;

    public void  getAllcounts() {

        response.setResponse (
                this.get ( SEARCH_BASE + "me-stats/", session.getExpertToken ( ) ) );

        response.printResponse ( );

    }


    public void getAllStats ( String to, String fromDate ) {

        String url="me-stats/?from_date="+fromDate+"&to_date="+to+"&stats=profile_visits&stats=sessions_count";

        response.setResponse ( this.get ( SEARCH_BASE+url, session.getExpertToken ( ) ) );

        response.printResponse ();

       if ( response.statusCode ()!=HTTP_BAD ){

           numProfileVisit=pr.getJsonData ( "results.profile_visits.total", ResponseDataType.INT);
           numSession=pr.getJsonData ( "results.sessions_count.total", ResponseDataType.INT);
        }
    }

    public String getNumProfileVisit(){
        return numProfileVisit;
    }

    public String getNumSession(){

        return numSession;
    }

}
