package expertchat.params;

// Created by Kishor on 4/25/2017.

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Credentials {


    private Credentials(){

    }

    private static Credentials credentials=new Credentials ();

    public static Credentials getCredentials(){

        return credentials;
    }

    private String expertCredential;

    private String userCredential;


    public void setuserCredential ( String userCredential ) {

        this.userCredential = userCredential;

    }

    public String[] getExpertCredential ( ) {

        JsonObject jsonObject = ( JsonObject ) new JsonParser ( ).parse ( this.expertCredential );

        String credential[] = { jsonObject.get ( "email" ).getAsString ( ) , jsonObject.get ( "password" ).getAsString ( ) };

        return credential;
    }

    public void setExpertCredential ( String expertCredential ) {

        this.expertCredential = expertCredential;

    }

    public String[] getUserCredential ( ) {

        JsonObject jsonObject = ( JsonObject ) new JsonParser ( ).parse ( this.userCredential );

        String credential[] = { jsonObject.get ( "email" ).getAsString ( ) , jsonObject.get ( "password" ).getAsString ( ) };

        return credential;
    }
}
