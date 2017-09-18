package expertchat.apioperation.session;

// Created by Kishor on 1/23/2017.


public final class SessionManagement {

    private static SessionManagement sm = new SessionManagement ( );

    private static String user_token;
    private static String expert_token;

    private SessionManagement ( ) {

        /* preventing from creating more than one object of this class */
    }

    public static SessionManagement session ( ) {

        return sm;
    }

    public String getExpertToken ( ) {

        return expert_token;
    }

    public void setExpertToken ( String token ) {

        SessionManagement.expert_token= token;
    }

    public String getUserToken ( ) {

        return user_token;
    }

    public void setUserToken ( String token ) {

        SessionManagement.user_token = token;
    }

}
