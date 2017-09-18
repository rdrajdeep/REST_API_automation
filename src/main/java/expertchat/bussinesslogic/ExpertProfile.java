package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.params.Credentials;

import static expertchat.apioperation.ExpertChatEndPoints.EXPERT_PROFILE;
import static expertchat.apioperation.ExpertChatEndPoints.EXPERT_PROFILE_BY_USER;
import static expertchat.usermap.TestUserMap.getMap;

public class ExpertProfile extends AbstractApiFactory implements HTTPCode {

    private ApiResponse response = ApiResponse.getObject ( );

    private ParseResponse parseResponse = new ParseResponse ( response );

    private SessionManagement session = SessionManagement.session ( );

    private FileUpload fileUpload = new FileUpload ( );

    private String expertProfileID;

    private String expertID;


    public String getExpertProfileID ( ) {
        return expertProfileID;
    }

    public void setExpertProfileID ( String expertProfileID ) {
        this.expertProfileID = expertProfileID;
    }

    public String getExpertID ( ) {

        return expertID;
    }

    public void setExpertID ( String expertID ) {

        this.expertID = expertID;
    }

    /**
     * @param profile
     */
    public void addExpertyProfile ( String profile ) {

        response.setResponse (
                this.post ( profile, EXPERT_PROFILE, session.getExpertToken ( ) )
        );

        if ( response.statusCode ( ) == HTTP_ACCEPTED ||
                response.statusCode ( ) == HTTP_OK ) {

            setExpertProfileID (
                    parseResponse.getJsonData ( "results.id", ResponseDataType.INT ) );


            setExpertID (
                    parseResponse.getJsonData ( "results.expert.id", ResponseDataType.INT ) );

            getMap ( ).put ( "expertProfileId", getExpertProfileID ( ) );
            getMap ( ).put ( "expertId", getExpertID ( ) );
        }

        response.printResponse ( );
    }

    /**
     * @param byExpert
     */
    public void getProfileOfExpert ( String id, boolean byExpert ) {

        if ( byExpert ) {

            if ( ! id.isEmpty ( ) ) {

                response.setResponse (
                        this.get ( EXPERT_PROFILE + id + "/", session.getExpertToken ( ) )
                );
                response.printResponse ( );
            } else {

                response.setResponse (
                        this.get ( EXPERT_PROFILE, session.getExpertToken ( ) )
                );
                response.printResponse ( );
            }
        } else {

            if ( ! id.isEmpty ( ) ) {

                response.setResponse (
                        this.get ( EXPERT_PROFILE_BY_USER + id + "/", session.getExpertToken ( ) )
                );
                response.printResponse ( );
            } else {

                response.setResponse (
                        this.get ( EXPERT_PROFILE_BY_USER, session.getExpertToken ( ) )
                );
                response.printResponse ( );
            }
        }

        if ( response.statusCode ( ) == HTTP_OK ) {

            setExpertProfileID (
                    parseResponse.getJsonData ( "results[0].id", ResponseDataType.INT ) );


            setExpertID (
                    parseResponse.getJsonData ( "results[0].expert.id", ResponseDataType.INT ) );

            getMap ( ).put ( "expertProfileId", getExpertProfileID ( ) );
            getMap ( ).put ( "expertId", getExpertID ( ) );
        }

        response.printResponse ( );
    }

    public void getAllProfileOfExpert ( ) {

        response.setResponse (
                this.get ( EXPERT_PROFILE, session.getExpertToken ( ) )
        );

        response.printResponse ( );
    }

    public String getProfileCount ( ) {

        return parseResponse.getJsonData ( "metadata.count", ResponseDataType.INT );
    }

    public void getNextPage ( ) {

        String page = parseResponse.getJsonData ( "metadata.next", ResponseDataType.STRING );
        response.setResponse (
                this.get ( page, session.getExpertToken ( ) )
        );

        response.printResponse ( );
    }

    public void updateExpertProfile ( String profile, String id ) {

        response.setResponse (
                this.patch ( profile, EXPERT_PROFILE + id + "/", session.getExpertToken ( ) )
        );

        response.printResponse ( );
    }

    public boolean deleteProfile ( String id ) {

        return this.isDelete ( EXPERT_PROFILE + id + "/", session.getExpertToken ( ) );

    }



    public void uploadMedia ( String mediaPath, boolean isExpert ) {

        if ( isExpert ) {

            fileUpload.uploadMedia ( mediaPath, Credentials.getCredentials ().getExpertCredential ( )[ 0 ], Credentials.getCredentials ().getExpertCredential ( )[ 1 ], true );

        } else {

            fileUpload.uploadMedia ( mediaPath, Credentials.getCredentials ().getUserCredential ( )[ 0 ], Credentials.getCredentials ().getUserCredential ( )[ 1 ], false );
        }
    }

    public String getResponseOfMediaUpload ( ) {

        return FileUpload.getJson ( );
    }
}
