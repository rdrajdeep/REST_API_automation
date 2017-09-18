package expertchat.apioperation.apiresponse;

// Created by Kishor on 1/23/2017.

import io.restassured.response.Response;

public final class ApiResponse {

    private final static ApiResponse responseObj = new ApiResponse ( );
    public static String VERIFICATION_CODE;
    private Response response;

    private ApiResponse ( ) {
    }

    public static ApiResponse getObject ( ) {

        return responseObj;
    }

    public Response getResponse ( ) {

        return response;
    }

    public void setResponse ( Response response ) {

        this.response = response;
    }

    public void printResponse ( ) {

        getResponse ( ).prettyPrint ( );
    }

    public int statusCode ( ) {

        return getResponse ( ).statusCode ( );
    }
}
