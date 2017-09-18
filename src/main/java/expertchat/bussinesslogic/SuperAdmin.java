package expertchat.bussinesslogic;// Created by Kishor on 3/20/2017.

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static expertchat.usermap.TestUserMap.getMap;


public class SuperAdmin extends AbstractApiFactory implements ExpertChatEndPoints, HTTPCode {


    private String contentId;
    private int countOfContent;
    private ApiResponse response = ApiResponse.getObject ( );
    private SessionManagement session = SessionManagement.session ( );
    private ParseResponse pr = new ParseResponse ( response );
    private LinkedHashMap < String, ArrayList < String > > tagMap = new LinkedHashMap <> ( );
    private String stringOfTags = "";

    public String getStringOfTags ( ) {

        return stringOfTags;
    }

    public ArrayList getTags ( String key ) {

        return tagMap.get ( key );
    }

    public String getContentId ( ) {
        return contentId;
    }

    public void setContentId ( String contentId ) {
        this.contentId = contentId;
    }

    public int getCountOfContent ( ) {
        return countOfContent;
    }

    public void setCountOfContent ( int countOfContent ) {

        this.countOfContent = countOfContent;
    }

    public void createContent ( String json ) {

        response.setResponse (

                this.post ( json, SUPER_ADMIN_CONTENTS, session.getExpertToken ( ) )
        );

        if ( response.statusCode ( ) == HTTPCode.HTTP_OK ||
                response.statusCode ( ) == HTTPCode.HTTP_ACCEPTED ) {

            this.setContentId ( pr.getJsonData ( "results.id", ResponseDataType.INT ) );
            getMap ( ).put ( "superAdminContentId", getContentId ( ) );
            response.printResponse ( );
        }
    }

    public void getContent ( String contentId ) {

        String url = null;
        stringOfTags = null;
        stringOfTags = "";

        if ( contentId.equals ( "" ) ) {
            url = SUPER_ADMIN_CONTENTS + contentId.trim ( );
        } else if ( ! contentId.equals ( "" ) ) {
            url = SUPER_ADMIN_CONTENTS + contentId.trim ( ) + "/";
        }

        response.setResponse (

                this.get ( url, session.getExpertToken ( ) )
        );

        if ( contentId.equals ( "" ) && response.statusCode ( ) != HTTP_BAD ) {

            setCountOfContent ( Integer.parseInt ( pr.getJsonData ( "metadata.count", ResponseDataType.INT ) ) );

        } else if ( ! contentId.equals ( "" ) && response.statusCode ( ) != HTTP_BAD ) {

            List tags = response.getResponse ( ).jsonPath ( ).getList ( "results.tags" );

            int size = tags.size ( );

            for ( int i = 0 ; i < size ; i++ ) {

                stringOfTags = stringOfTags + tags.get ( i ).toString ( ).trim ( );
            }
            getMap ( ).put ( "su-stringOfTags", stringOfTags );

            System.out.println ( "I am from super admin:::" + stringOfTags );
        }

        response.printResponse ( );
    }

    public boolean deleteContent ( String contentId ) {

        return this.isDelete ( SUPER_ADMIN_CONTENTS + contentId + "/", session.getExpertToken ( ) );
    }

    public void updateContent ( String json, String contentId ) {

        response.setResponse (
                this.put ( json, SUPER_ADMIN_CONTENTS + contentId + "/", session.getExpertToken ( ) )
        );

        response.printResponse ( );
    }

    public boolean verifyUpdate ( String json ) {

        String title = pr.getJsonData ( "results.title", ResponseDataType.STRING );

        System.out.println ( title );

        String des = pr.getJsonData ( "results.description", ResponseDataType.STRING );

        System.out.println ( des );

        String content_id = pr.getJsonData ( "results.content_id", ResponseDataType.STRING );

        System.out.println ( content_id );

        return json.contains ( title ) && json.contains ( des ) && json.contains ( content_id );
    }

    public boolean unhide ( String contentId ) {

        response.setResponse (

                this.get ( SUPER_ADMIN_CONTENTS + contentId + "/" + "unhide/", session.getExpertToken ( ) )
        );

        response.printResponse ( );

        return pr.getJsonData ( "results.status", ResponseDataType.STRING ).equals ( "success" );
    }
}
