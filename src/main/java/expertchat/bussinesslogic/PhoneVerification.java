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

/**
 * Created by root on 18/2/17.
 */

public class PhoneVerification extends AbstractApiFactory implements ExpertChatEndPoints {

    ApiResponse response = ApiResponse.getObject ( );

    ParseResponse jsonParser = new ParseResponse ( response );

    SessionManagement session = SessionManagement.session ( );

    private String code;

    public void phoneCodeSend ( String phone, boolean isExpert ) {

        if ( isExpert ) {
            response.setResponse (
                    this.post ( phone, PHONECODESEND, session.getExpertToken ( ) )
            );
        } else {
            response.setResponse (
                    this.post ( phone, U_PHONECODESEND, session.getExpertToken ( ) )
            );
        }
        response.printResponse ( );

        if ( response.statusCode ( ) == HTTPCode.HTTP_OK ) {
            setCode ( jsonParser.getJsonData ( "results.verification_code", ResponseDataType.STRING ) );
        }
    }

    public void phoneCodeResend ( String phone, boolean isExpert ) {

        if ( isExpert ) {

            response.setResponse (
                    this.post ( phone, PHONECODERESEND, session.getExpertToken ( ) )
            );

        } else {

            response.setResponse (
                    this.post ( phone, U_PHONECODERESEND, session.getExpertToken ( ) )
            );
        }
        response.printResponse ( );

        if ( response.statusCode ( ) == HTTPCode.HTTP_OK ) {

            setCode ( jsonParser.getJsonData ( "results.verification_code", ResponseDataType.STRING ) );

        }
    }

    public void verfiyPhone ( String code, boolean isExpert ) {
        String json = "{\"passcode\":\"" + code + "\"}";

        JsonObject phoneCodeJson = ( JsonObject ) new JsonParser ( ).parse ( json );

        if ( isExpert ) {

            response.setResponse (
                    this.post ( phoneCodeJson.toString ( ), PHONECODEVERIFY, session.getExpertToken ( ) )
            );

        } else {

            response.setResponse (
                    this.post ( phoneCodeJson.toString ( ), U_PHONECODEVERIFY, session.getExpertToken ( ) )
            );
        }

        response.printResponse ( );
    }

    public String getCode ( ) {
        return code;
    }

    private void setCode ( String code ) {
        this.code = code;
    }

    public boolean isCodeSent ( ) {
        return this.getCode ( ) != null;
    }
}
