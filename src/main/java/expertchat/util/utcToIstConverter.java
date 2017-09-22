package expertchat.util;

import org.jbehave.core.annotations.Alias;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class utcToIstConverter {

    private static Date utcDate;
    private static DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");


    /*@
        * Convert date IST to UTC in "yyyy-MM-dd'T'HH:mm:ss'Z'" format
     */

    public String UTCtoIST(String date){

        try {
            formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));

            utcDate = formatDate.parse(date);
            //System.out.println(TimeZone.getDefault().getID());

            formatDate.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
           // System.out.println(formatDate.format(utcDate));


        } catch (ParseException e) {

            e.printStackTrace();
        }

        return formatDate.format(utcDate);
    }


    /*@
        * Convert date IST to UTC in "yyyy-MM-dd'T'HH:mm:ss'Z'" format
     */

    public String ISTtoUTC(String date){

        Date istDate=null;

        try {
            formatDate.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));

            istDate = formatDate.parse(date);
            //System.out.println(TimeZone.getDefault().getID());

            formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
            // System.out.println(formatDate.format(istDate));


        } catch (ParseException e) {

            e.printStackTrace();
        }

        return formatDate.format(istDate);
    }


    /*@
    * Get current date in "yyyy-MM-dd'T'HH:mm:ss'Z'" format
     */
    public String currentDate() throws ParseException {

        String dateTime=null;

        formatDate.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));

        formatDate.format(new Date());

        return formatDate.format(new Date());
    }

    /*@
     * Convert date from  "yyyy-MM-dd'T'HH:mm:ss'Z'" to Unix TimeStamp
     */

    public long convertToUnixTimestamp(String date) {

        long unixTime=0;
        try {
            Date newdate = formatDate.parse(date);

            unixTime = (long) newdate.getTime() / 1000;

            System.out.println(unixTime);
            return unixTime;

        }catch (ParseException e){

            e.printStackTrace();
        }
        return unixTime;
    }

}

