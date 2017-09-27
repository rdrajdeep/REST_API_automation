/*@
 // Author of the code Rajdeep D
 */

package expertchat.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DatetimeUtility {

    private static Date utcDate;
    private static DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static DateFormat formatDateOnly = new SimpleDateFormat("yyyy-MM-dd");






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
    public String currentDate() {

        String dateTime=null;

        formatDate.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));

        formatDate.format(new Date());

        return formatDate.format(new Date());
    }

    /*@
    * Get current date in "yyyy-MM-dd" format
     */
    public String currentDateOnly() {

        String dateTime=null;

        formatDateOnly.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));

        formatDateOnly.format(new Date());

        return formatDateOnly.format(new Date());
    }


    /*@
     * Convert date from  "yyyy-MM-dd'T'HH:mm:ss'Z'" to Unix TimeStamp
     */

    public long convertToUnixTimestamp(String date) {

        long unixTime=0;
        try {
            Date newdate = formatDate.parse(date);

            unixTime = (long) newdate.getTime() / 1000;

            //System.out.println(unixTime);
            return unixTime;

        }catch (ParseException e){

            e.printStackTrace();
        }
        return unixTime;
    }

    public String convertUnixToOriginDate(long unixTime){

        Date date = new Date(unixTime*1000L);

        String utcDate=formatDate.format(date);

        return utcDate;

    }

    public static  void main(String[] args){

        DatetimeUtility dt=new DatetimeUtility();
        long currentUnix=dt.convertToUnixTimestamp(dt.ISTtoUTC(dt.currentDate()));//unix of below ist time
       // String istDt="2017-09-23T03:30:00Z";
        String utcDt="2017-09-27T07:00:00Z";
        String cdate="2017-09-27T06:21:47Z";

        String  cDateUTC=dt.ISTtoUTC(cdate);

        long scheduletime=dt.convertToUnixTimestamp(utcDt);

        System.out.println("UTC "+utcDt+" Converted to unixtime  ---  "+scheduletime);
        System.out.println("Current time in unix "+currentUnix);
        System.out.println("Now is "+cdate +" Time diff in Sec "+(scheduletime-currentUnix));
        System.out.println("Current time in UTC "+cDateUTC);
      //  System.out.println("IST "+istDt+" Converted to UTC  ---  "+dt.ISTtoUTC(istDt));
        //System.out.println("UTC "+utcDt+" Converted to UNIX ---  "+ dt.convertToUnixTimestamp(utcDt));
        //System.out.println("unix to original-- "+dt.convertUnixToOriginDate(unix));
        //System.out.println("IST to UTC "+dt.ISTtoUTC(dt.convertUnixToOriginDate(unix)));
        //System.o ut.println("UNIX "+dt.convertToUnixTimestamp(istDt)+" Converted to UTC ---  "+
            //    dt.convertUnixToOriginDate(dt.convertToUnixTimestamp(istDt)));
        //System.out.println(dt.UTCtoIST(dt.convertUnixToOriginDate(dt.convertToUnixTimestamp(istDt))));

        //System.out.println("utc++"+dt.ISTtoUTC(dt.convertUnixToOriginDate(dt.convertToUnixTimestamp(istDt))));


    }

}

