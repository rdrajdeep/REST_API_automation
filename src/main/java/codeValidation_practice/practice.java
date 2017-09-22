package codeValidation_practice;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/*@
@param UTC time
Convert UTC time to IST time zone
 */
public class practice  {

    public static Date utcDate;
    public static DateFormat expireFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static void main(String[] args){

        String date= "2017-09-22T14:00:00Z"; // UTC api

       /* try {
           *//* expireFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            utcDate = expireFormat.parse(date);
            System.out.println(TimeZone.getDefault().getID());

            expireFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
            System.out.println(expireFormat.format(utcDate));*/


            expireFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
            expireFormat.format(new Date());
            //String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            //Timestamp unixtime=  Timestamp.valueOf(currentTime);
            System.out.println(expireFormat.format(new Date()));

        /*} catch (ParseException e) {
            e.printStackTrace();
        }*/

        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date newdate = expireFormat.parse(date);// .parse(date);
            long unixTime = (long) newdate.getTime() / 1000;
            System.out.println(unixTime);//<- prints 1352504418
        }catch (ParseException e){
            e.printStackTrace();
        }


    }
}
