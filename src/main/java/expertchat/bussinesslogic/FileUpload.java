package expertchat.bussinesslogic;

// Created by Kishor on 3/1/2017.

import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class FileUpload {

    public static boolean ERROR = false;
    private static String json;
    private WebDriver driver;

    public static String getJson ( ) {
        return json;
    }

    private static void waitSomeTime ( ) {

        try {

            TimeUnit.SECONDS.sleep ( 30 );

        } catch ( InterruptedException e ) {

            e.printStackTrace ( );
        }
    }

    public void uploadMedia ( String mediaPath, String ExpertEmail, String password, Boolean isExpert ) {

        try {
            System.setProperty ( "webdriver.chrome.driver", "Driver/chromedriver.exe" );

            driver = new ChromeDriver ( );

            driver.manage ( ).window ( ).maximize ( );

            if ( isExpert ) {

                driver.get ( "http://api.qa.experchat.com/api-auth/login/?next=/v1/expert/usermedia/" );
                driver.findElement ( By.name ( "username" ) ).sendKeys ( "expert_" + ExpertEmail );
            } else {

                driver.get ( "http://api.qa.experchat.com/api-auth/login/?next=/v1/user/usermedia/" );
                driver.findElement ( By.name ( "username" ) ).sendKeys ( "user_" + ExpertEmail );
            }

            driver.findElement ( By.name ( "password" ) ).sendKeys ( password );

            driver.findElement ( By.id ( "submit-id-submit" ) ).click ( );

            if ( isExpert && ! driver.getCurrentUrl ( ).equals ( "http://api.qa.experchat.com/v1/expert/usermedia/" ) ) {

                driver.quit ( );

            }/*else if(isExpert==false && !driver.getCurrentUrl().equals("http://api.qa.experchat.com/v1/user/usermedia/")){

            driver.quit();
        }*/

            waitSomeTime ( );

            driver.findElement ( By.name ( "media" ) ).sendKeys ( mediaPath );

            driver.findElement ( By.xpath ( "//*[@id=\"post-object-form\"]/form/fieldset/div[5]/button" ) ).click ( );

            String response = driver.findElement ( By.xpath ( "//*[@id=\"content\"]/div[1]/div[4]/pre" ) ).getText ( );

            String jsonData = response.substring ( response.indexOf ( "{" ) );

            System.out.println ( jsonData );

            driver.quit ( );

            json = new Gson ( ).toJson ( jsonData );

        } catch ( Exception e ) {

            driver.quit ( );
            json = e.getMessage ( );
            ERROR = true;
            System.out.println ( e.getMessage ( ) );

        }
    }

    /*public static void main(String [] args){

        new FileUpload().uploadMedia("C:\\Users\\Kishor\\Desktop\\fp.jpg","kishor+expert70@atlogys.com","qerty123");
    }*/
}
