package expertchat.bussinesslogic;// Created by Kishor on 2/9/2017.

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.usermap.TestUserMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;


public class BasicProfile extends AbstractApiFactory implements ExpertChatEndPoints, HTTPCode {

    private ApiResponse response = ApiResponse.getObject ( );

    private ParseResponse parseResponse = new ParseResponse ( response );


    public void loadBasicProfile ( boolean isExpert ) {

        if ( isExpert ) {

            response.setResponse (
                    this.get ( BASIC_PROFILE, SessionManagement.session ( ).getExpertToken ( ) )
            );
        } else {
            response.setResponse (
                    this.get ( U_BASIC_PROFILE, SessionManagement.session ( ).getExpertToken ( ) )
            );

        }

        response.printResponse ( );
    }

    public boolean verifyProfileEmail ( String user ) {

        String actualEmail = parseResponse.getJsonData ( "results.email", ResponseDataType.STRING );

        String expectedEmail = TestUserMap.getEmailOf ( user );
        System.out.println ( "same opopopop" + TestUserMap.getEmailOf ( user ) );
        return actualEmail.equals ( expectedEmail );
    }


    public void addName ( String name, boolean isExpert ) {

        if ( isExpert ) {
            response.setResponse (
                    this.put (

                            name, BASIC_PROFILE, SessionManagement.session ( ).getExpertToken ( ) ) );
        } else {

            response.setResponse (
                    this.put (

                            name, U_BASIC_PROFILE, SessionManagement.session ( ).getExpertToken ( ) ) );
        }

        response.printResponse ( );
    }

    public void addProfilePhoto ( String image, boolean isExpert ) throws IOException {

        byte[] imageInByte;
        BufferedImage originalImage = ImageIO.read ( new File ( image ) );

        // convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream ( );
        ImageIO.write ( originalImage, "jpg", baos );
        baos.flush ( );
        imageInByte = baos.toByteArray ( );
        baos.close ( );
        String b64String = "\"" + "data:image/png;base64," + Base64.getEncoder ( ).encodeToString ( imageInByte ) + "\"";
        String json = "{\"image\":" + b64String + "}";

        if ( isExpert ) {

            response.setResponse (
                    this.post (
                            json, ME_PHOTO, SessionManagement.session ( ).getExpertToken ( ) ) );
        } else {

            response.setResponse (
                    this.post (
                            json, U_ME_PHOTO, SessionManagement.session ( ).getExpertToken ( ) ) );
        }

        response.printResponse ( );

    }
}
