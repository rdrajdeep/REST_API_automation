package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.util.ExpertChatException;

import java.util.List;

import static expertchat.usermap.TestUserMap.getMap;


public class GetStreamFeeds extends AbstractApiFactory implements HTTPCode, ExpertChatEndPoints {

    private String getStreamId;
    private String stringOfTags = "";
    private ApiResponse response = ApiResponse.getObject ( );
    private ParseResponse pr = new ParseResponse ( response );
    private SessionManagement session = SessionManagement.session ( );
    private String tag;

    public String getTag ( ) {
        return tag;
    }

    public String getGetStreamId ( ) {
        return getStreamId;
    }

    public String getStringOfTags ( ) {
        return stringOfTags;
    }

    private void getFeedsByExpertProfileId ( String id ) {

        response.setResponse ( this.get ( BY_EXPERT_PROFILE + id + "/feeds/", session.getExpertToken ( ) ) );
        response.printResponse ( );
    }

    private void getFeedsByExpertId ( String id ) {

        response.setResponse ( this.get ( BY_EXPERT + id + "/feeds/", session.getExpertToken ( ) ) );
        response.printResponse ( );
    }

    private void getFeedsByTagId ( String id ) {

        tag = id;
        stringOfTags = getStreamId = null;
        stringOfTags = "";
        getStreamId = "";
        response.setResponse ( this.get ( BY_TAGS + id + "/feeds/", session.getExpertToken ( ) ) );

        if ( response.statusCode ( ) != HTTP_BAD ) {

            getStreamId = pr.getJsonData ( "results[0].id", ResponseDataType.INT );

            List tags = response.getResponse ( ).jsonPath ( ).getList ( "results[0].tags" );

            int size = tags.size ( );
            for ( int i = 0 ; i < size ; i++ ) {

                stringOfTags = stringOfTags + tags.get ( i ).toString ( ).trim ( );
            }

            getMap ( ).put ( "getStreamId", getStreamId );
            getMap ( ).put ( "gs-stringOfTags", stringOfTags );
            System.out.println ( "I am from getStream:::" + stringOfTags );
        }
        response.printResponse ( );
    }

    public void getFeeds ( String id, By by ) {

        switch ( by.ordinal ( ) ) {

            case 0:
                getFeedsByExpertProfileId ( id );
                break;

            case 1:
                getFeedsByExpertId ( id );
                break;

            case 2:
                getFeedsByTagId ( id );
                break;

            default:
                throw new ExpertChatException ( "Exception has occurred" );
        }
    }


    public enum By {

        BY_EXPERTPROFILE,
        BY_EXPERTID,
        BY_TAGID

    }
}
