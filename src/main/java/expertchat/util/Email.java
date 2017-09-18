package expertchat.util;

import expertchat.report.Report;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


public class Email {

    private static Session getEmailSession ( ) {

        final String username = "kishor@atlogys.com";
        final String password = "kishor@dbu";

        Properties props = new Properties ( );
        props.put ( "mail.smtp.auth", "true" );
        props.put ( "mail.smtp.starttls.enable", "true" );
        props.put ( "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory" );
        props.put ( "mail.smtp.host", "smtp.gmail.com" );
        props.put ( "mail.smtp.port", "465" );

        Session session = Session.getInstance ( props,
                new javax.mail.Authenticator ( ) {

                    protected PasswordAuthentication getPasswordAuthentication ( ) {

                        return new PasswordAuthentication ( username, password );
                    }
                } );

        return session;
    }


    public static void sendEmail ( String body,
                                   String from,
                                   String to,
                                   String subject,
                                   boolean isAttachment ) {

        try {

            Message message = new MimeMessage ( getEmailSession ( ) );

            message.setFrom ( new InternetAddress ( from ) );

            String address = to;

            InternetAddress[] iAdressArray = InternetAddress.parse ( address );

            message.setRecipients ( Message.RecipientType.TO, iAdressArray );

            message.setSubject ( subject );

            if ( ! isAttachment ) {

                message.setText ( body );

                Transport.send ( message );

                return;
            }

            BodyPart messageBodyPart = new MimeBodyPart ( );

            messageBodyPart.setText ( body );

            Multipart multipart = new MimeMultipart ( );

            multipart.addBodyPart ( messageBodyPart );

            // Part two is attachment
            messageBodyPart = new MimeBodyPart ( );

            String rName = Report.rPath;
            String lName = ResponseLogger.logPath;

            DataSource rSource = new FileDataSource ( rName );
            DataSource lSource = new FileDataSource ( lName );

            messageBodyPart.setDataHandler ( new DataHandler ( rSource ) );
            messageBodyPart.setFileName ( "ExperChat_Automation.html" );

            multipart.addBodyPart ( messageBodyPart );

            message.setContent ( multipart );

            Transport.send ( message );

        } catch ( MessagingException e ) {

            System.out.println ( "Error in sending email. " + e.getMessage ( ) );
            System.exit ( 1 );
        }
    }
}