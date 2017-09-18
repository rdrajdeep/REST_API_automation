package expertchat.bussinesslogic;// Created by Kishor on 3/22/2017.

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;

public class ProfileComplete extends AbstractApiFactory implements ExpertChatEndPoints, HTTPCode {


    private boolean isProfileComplete;
    private String isExpertProfile;
    private String is_myinfo_complete;
    private String is_socialink_exist;
    private String has_availibility_slot;
    private ApiResponse response = ApiResponse.getObject ( );
    private SessionManagement session = SessionManagement.session ( );
    private ParseResponse parse = new ParseResponse ( response );

    public boolean isProfileComplete ( ) {
        return isProfileComplete;
    }
  public void sethas_availibility_slot(String has_availibility_slot){

        this.has_availibility_slot=has_availibility_slot;

  }
    public void setProfileComplete ( boolean profileComplete ) {
        isProfileComplete = profileComplete;
    }

    public String isExpertProfile ( ) {
        return isExpertProfile;
    }

    public void setExpertProfile ( String expertProfile ) {
        isExpertProfile = expertProfile;
    }

    public String isIs_myinfo_complete ( ) {
        return is_myinfo_complete;
    }

    public void setIs_myinfo_complete ( String is_myinfo_complete ) {
        this.is_myinfo_complete = is_myinfo_complete;
    }

    public String isIs_socialink_exist ( ) {
        return is_socialink_exist;
    }

    public void setIs_socialink_exist ( String is_socialink_exist ) {
        this.is_socialink_exist = is_socialink_exist;
    }


    public void checkProfileCompletemness ( ) {

        response.setResponse (

                this.get ( PROFILE_STATUS, session.getExpertToken ( ) )
        );

        response.printResponse ( );

        setExpertProfile ( parse.getJsonData ( "results.is_expertprofiles_exist", ResponseDataType.BOOLEAN ) );

        setIs_myinfo_complete ( parse.getJsonData ( "results.is_myinfo_complete", ResponseDataType.BOOLEAN ) );

        setIs_socialink_exist ( parse.getJsonData ( "results.is_socialink_exist", ResponseDataType.BOOLEAN ) );

        sethas_availibility_slot(parse.getJsonData ( "results.has_availibility_slot", ResponseDataType.BOOLEAN));

        if ( is_myinfo_complete.equals ( "false" ) || is_socialink_exist.equals ( "false" ) || isExpertProfile.equals ( "false" ) || has_availibility_slot.equals ( "false" )) {

            setProfileComplete ( false );
            return;
        } else if ( is_myinfo_complete.equals ( "true" ) && is_socialink_exist.equals ( "true" ) && isExpertProfile.equals ( "true" ) && has_availibility_slot.equals ( "true" ) ) {

            setProfileComplete ( true );
            return;
        }
    }

}
