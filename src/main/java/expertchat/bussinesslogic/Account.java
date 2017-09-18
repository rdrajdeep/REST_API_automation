package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;

public class Account extends AbstractApiFactory implements ExpertChatEndPoints {

    private static String accountId;
    ApiResponse response = ApiResponse.getObject ( );
    ParseResponse pr = new ParseResponse ( response );
    SessionManagement session = SessionManagement.session ( );

    public String getAccountId ( ) {

        return accountId;
    }

    public void setAccountId ( String accountId ) {

        Account.accountId = accountId;
    }

    public void createAccount ( String json ) {

        response.setResponse (

                this.post ( json, EXPERT_ACCOUNT, session.getExpertToken ( ) )
        );

        if ( response.getResponse ( ).statusCode ( ) == HTTPCode.HTTP_OK ||
                response.getResponse ( ).statusCode ( ) == HTTPCode.HTTP_ACCEPTED ) {

            this.setAccountId ( pr.getJsonData ( "results.id", ResponseDataType.INT ) );
        }

        response.printResponse ( );
    }

    public void getAccount ( String id ) {

        response.setResponse (
                this.get ( EXPERT_ACCOUNT + id + "/", session.getExpertToken ( ) )
        );
        response.printResponse ( );
    }
}
