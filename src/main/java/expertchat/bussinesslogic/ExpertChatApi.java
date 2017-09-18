package expertchat.bussinesslogic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.usermap.TestUserMap;
import expertchat.util.ExpertChatUtility;

import static expertchat.usermap.TestUserMap.getMap;


public class ExpertChatApi extends AbstractApiFactory implements ExpertChatEndPoints {

    public static boolean REGISTRATION_ERROR = false;
    public static boolean LOGIN_ERROR = false;
    private ApiResponse response = ApiResponse.getObject ( );
    private ParseResponse jsonParser = new ParseResponse ( response );
    private String baseID;


    /**
     * @param json
     */
    public void doRegistration ( String json, boolean isExpert ) {

        System.out.println ( "REGISTRATION" );

        if ( isExpert ) {
            response.setResponse (
                    this.post ( json, ExpertChatEndPoints.REGISTER )
            );
        } else {

            response.setResponse (
                    this.post ( json, ExpertChatEndPoints.REGISTER_USER )
            );

        }

        if ( response.statusCode ( ) == HTTPCode.HTTP_BAD ) {

            REGISTRATION_ERROR = true;
            return;
        }

        EmailVerification.setVerificationEndPoint ( jsonParser.getVerificationCode ( ) );

        response.printResponse ( );
    }

    /**
     * @param json
     */

    public void doLogIn ( String json, boolean isExpert ) {

        System.out.println ( "LOGIN" );

        if ( isExpert ) {

            response.setResponse ( this.post ( json, ExpertChatEndPoints.LOGIN ) );

            SessionManagement.session ( ).setExpertToken (
                    jsonParser.getJsonData ( "results.token", ResponseDataType.STRING )
            );

            baseID = jsonParser.getJsonData ( "results.id", ResponseDataType.INT );
            getMap ( ).put ( "expert_baseId", baseID);

        } else {

            System.out.println("SUPER USER");
            response.setResponse ( this.post ( json, ExpertChatEndPoints.LOGIN_USER ) );

            SessionManagement.session ( ).setUserToken (
                    jsonParser.getJsonData ( "results.token", ResponseDataType.STRING )
            );

            baseID = jsonParser.getJsonData ( "results.id", ResponseDataType.INT );
            getMap ( ).put ( "user_baseId", baseID);


        }

        if ( response.statusCode ( ) == HTTPCode.HTTP_BAD ) {

            LOGIN_ERROR = true;
            return;
        }
        response.printResponse ( );
        return;
    }
/*
* Overloaded method for superuser /expert /user login
* * */

    public void doLogIn ( String json, String userType ){

        if ( userType.equals("superUser") ) {

            response.setResponse ( this.post ( json, ExpertChatEndPoints.SUPER_ADMIN_LOGIN ) );//Send super user endpoint

            SessionManagement.session ( ).setExpertToken (
                    jsonParser.getJsonData ( "results.token", ResponseDataType.STRING )
            );

            baseID = jsonParser.getJsonData ( "results.id", ResponseDataType.INT );
            getMap ( ).put ( "expert_baseId", baseID);

        }else{
            System.out.println("Unable to login with the user");
        }

        if ( response.statusCode ( ) == HTTPCode.HTTP_BAD ) {

            LOGIN_ERROR = true;
            return;
        }
        response.printResponse ( );
    }

    public String getBaseID ( ) {

        return baseID;
    }

    public void verifyUser ( ) {

        System.out.println ( "VERIFY USER" );
        response.setResponse (
                this.get ( EmailVerification.getVerificationEndPoint ( ) )
        );

        response.printResponse ( );
    }

    /**
     * @param password
     * @param user
     */
    public void changePassword ( String password, String user, boolean isExpert ) {

        System.out.println ( "CHANGE PASSWORD" );
        String json = "{\"current_password\":\"jyoti1032\",  \"new_password\":\"1032kishor\"}";

        JsonObject jsonObject = ( JsonObject ) new JsonParser ( ).parse ( json );
        jsonObject.remove ( "current_password" );
        jsonObject.addProperty ( "current_password", TestUserMap.getPasswordOf ( user ) );
        jsonObject.remove ( "new_password" );
        jsonObject.addProperty ( "new_password", password );

        if ( isExpert ) {
            response.setResponse (
                    this.post ( jsonObject.toString ( ), CHANGE_PASSWORD, SessionManagement.session ( ).getExpertToken ( ) )
            );
        } else {
            response.setResponse (
                    this.post ( jsonObject.toString ( ), U_CHANGE_PASSWORD, SessionManagement.session ( ).getExpertToken ( ) )
            );

        }

        response.printResponse ( );

        if ( response.statusCode ( ) == HTTPCode.HTTP_OK ||
                response.statusCode ( ) == HTTPCode.HTTP_ACCEPTED ) {

            String updateTestData = TestUserMap.getUserCredentialsByKey ( user );
            JsonObject userData = ( JsonObject ) new JsonParser ( ).parse ( updateTestData );
            jsonObject.remove ( "password" );
            jsonObject.addProperty ( "password", password );
            TestUserMap.setTestData ( user, userData.toString ( ) );
        }

    }

    /**
     * @param password
     */
    public void resetPassword ( String password, String user, boolean isExpert ) {

        System.out.println ( "RESTING PASSWORD.." );
        String json = "{\"email\": \"" + TestUserMap.getEmailOf ( user ) + "\"}";
        JsonObject jsonObject = ( JsonObject ) new JsonParser ( ).parse ( json );

        if ( isExpert ) {
            response.setResponse (
                    this.post ( jsonObject.toString ( ), ExpertChatUtility.getValue ( "qa" ) + "expert/password/reset/" )
            );

        } else {

            response.setResponse (
                    this.post ( jsonObject.toString ( ), ExpertChatUtility.getValue ( "qa" ) + "user/password/reset/" )
            );
        }
        String token = jsonParser.getJsonData ( "results.token", ResponseDataType.STRING );

        String uid = jsonParser.getJsonData ( "results.uid", ResponseDataType.STRING );

        String resetUrl = ExpertChatUtility.getValue ( "qa" ) + "password/reset/confirm/" + uid + "/" + token + "/";

        String newJSon = "{\"password\":\"" + password + "\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";

        JsonObject newJson = ( JsonObject ) new JsonParser ( ).parse ( newJSon );

        response.setResponse (
                this.post ( newJson.toString ( ), resetUrl )
        );
        response.printResponse ( );

        if ( response.statusCode ( ) == HTTPCode.HTTP_OK ||
                response.statusCode ( ) == HTTPCode.HTTP_ACCEPTED ) {

            String updateTestData = TestUserMap.getUserCredentialsByKey ( user );

            System.out.println (updateTestData);

            JsonObject userData = ( JsonObject ) new JsonParser ( ).parse ( updateTestData );

            userData.remove ( "password" );

            userData.addProperty ( "password", password );

            TestUserMap.setTestData ( user, userData.toString ( ) );

            System.out.println ( "Updated data after reset" + userData.toString ( ) );

            System.out.println ( "Updated data after reset" + TestUserMap.getUserCredentialsByKey ( user ) );
        }
    }

    public void resendEmailVerification ( String json, boolean isExpert ) {

        System.out.println ( "RESEND EMAIL VERIFICATION" );

        if ( isExpert ) {

            response.setResponse (
                    this.post ( json, RESEND_EMAIL_VERIFICATION )
            );

        } else {

            response.setResponse (
                    this.post ( json, U_RESEND_EMAIL_VERIFICATION )
            );
        }

        response.printResponse ( );

    }

}
