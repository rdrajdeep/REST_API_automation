package expertchat.util;
import static expertchat.util.ExpertChatUtility.getValue;

// Created by Kishor on 1/24/2017.

public class ExpertChatException extends RuntimeException {

    public static boolean EXIT = false;
    public static String message;

    public ExpertChatException ( String msg ) {

        super ( msg );
        message = msg;
        EXIT = true;
        sendEmailNotificationOnException ( message );
    }


    public static void sendEmailNotificationOnException ( String param ) {

        Email.sendEmail ( getValue ( "EmailBodyError" ), getValue ( "From" ),
                getValue ( "To" ), param, false );

    }
}
