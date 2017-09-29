package expertchat.apioperation;

// Created by Kishor on 1/24/2017.

import expertchat.apioperation.httpspecs.Specification;
import expertchat.util.ExpertChatException;
import io.restassured.http.Header;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AbstractApiFactory implements ApiFactories {


    @Override
    public Response post ( String json, String url ) {

        Response r;
        r = given ( )
                .request ( )
                .spec ( Specification.setupRequestSpecBuilder ( ) )
                .body ( json )
                .post ( url );

        return r;
    }


    @Override
    public Response post ( String json, String url, String token ) {
        Response r;
        Header header = new Header ( "authorization", "token " + token );
        r = given ( )
                .request ( )
                .header ( header )
                .spec ( Specification.setupRequestSpecBuilder ( ) )
                .body ( json )
                .post ( url );

        return r;
    }

    @Override
    public Response post ( String json, String url, String token, boolean port ) {
        Response r;
        Header header = new Header ( "authorization", "token " + token );
        if ( port ) {
            r = given ( )
                    .request ( )
                    .header ( header )
                    .spec ( Specification.setupRequestSpecBuilderWithPort ( ) )
                    .body ( json )
                    .post ( url );
        } else {
            throw new ExpertChatException ( "Please Use proper URL" );
        }
        return r;
    }

    @Override
    public Response get ( String url ) {

        Response r;
        r = given ( )
                .request ( )
                .spec ( Specification.setupRequestSpecBuilder ( ) )
                .get ( url );

        return r;
    }

    @Override
    public Response get ( String url, String token ) {

        Response r;
        Header header = new Header ( "authorization", "token " + token );
        r = given ( )
                .request ( )
                .header ( header )
                .spec ( Specification.setupRequestSpecBuilder ( ) )
                .get ( url );

        return r;
    }

    @Override
    public Response get ( String url, String token, boolean port ) {
        Response r;
        if ( port ) {

            Header header = new Header ( "authorization", "token " + token );
            r = given ( )
                    .request ( )
                    .header ( header )
                    .spec ( Specification.setupRequestSpecBuilderWithPort ( ) )
                    .get ( url );
        } else {
            throw new ExpertChatException ( "Please Use proper URL" );
        }
        return r;
    }

    /*@
     * For getting the Search api url
     * Added by Rajdeep
     */

    @Override
    public Response get ( String url, String token, boolean port, String isSearch ) {
        Response r;
        if ( !port && isSearch.equals("Search Api")) {

            Header header = new Header ( "authorization", "token " + token );
            r = given ( )
                    .request ( )
                    .header ( header )
                    .spec ( Specification.setupRequestSpecBuilderSearchBase ( ) )
                    .get ( url );
        } else {
            throw new ExpertChatException ( "Please Use proper URL" );
        }
        return r;
    }

    @Override
    public Response put ( String json, String url, String token ) {

        Response r;
        Header header = new Header ( "authorization", "token " + token );
        r = given ( )
                .request ( )
                .header ( header )
                .spec ( Specification.setupRequestSpecBuilder ( ) )
                .body ( json )
                .put ( url );

        return r;
    }

    @Override
    public Response put ( String json, String url, String token, boolean port ) {
        Response r;
        if ( port ) {

            Header header = new Header ( "authorization", "token " + token );
            r = given ( )
                    .request ( )
                    .header ( header )
                    .spec ( Specification.setupRequestSpecBuilderWithPort ( ) )
                    .body ( json )
                    .put ( url );
        } else {
            throw new ExpertChatException ( "Please Use proper URL" );
        }
        return r;
    }

    @Override
    public Response delete ( String json, String url, String token ) {
        Response r;
        Header header = new Header ( "authorization", "token " + token );
        r = given ( )
                .request ( )
                .header ( header )
                .spec ( Specification.setupRequestSpecBuilder ( ) )
                .body ( json )
                .delete ( url );

        return r;
    }

    @Override
    public Response delete ( String json, String url, String token, boolean port ) {
        Response r;
        if ( port ) {
            Header header = new Header ( "authorization", "token " + token );
            r = given ( )
                    .request ( )
                    .header ( header )
                    .spec ( Specification.setupRequestSpecBuilderWithPort ( ) )
                    .body ( json )
                    .delete ( url );
        } else {
            throw new ExpertChatException ( "Please Use proper URL" );
        }
        return r;
    }


    @Override
    public boolean isDelete ( String url, String token ) {

        Response r;
        Header header = new Header ( "authorization", "token " + token );
        r = given ( )
                .request ( )
                .header ( header )
                .spec ( Specification.setupRequestSpecBuilder ( ) )
                .delete ( url );

        if ( r.statusCode ( ) == 204 ) {
            System.out.println ( "The server successfully processed the request " +
                    "but not returning any content" );
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Response patch ( String json, String url, String token ) {
        Response r;
        Header header = new Header ( "authorization", "token " + token );
        r = given ( )
                .request ( )
                .header ( header )
                .spec ( Specification.setupRequestSpecBuilder ( ) )
                .body ( json )
                .patch ( url );

        return r;
    }
}
