package expertchat.test.bdd;// Created by Kishor on 4/25/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.bussinesslogic.MeStats;
import expertchat.params.parameter;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyStatesTC  extends AbstractSteps{

    public MyStatesTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private MeStats stats=new MeStats ();

    @Then ("check the profile visits and count should be $count")
    public void profileVisits(@Named ("count")String count){

        info("Checking Number of profile visits");

        String date="";

        stats.getAllcounts ();

        int size=response.getResponse ().jsonPath ().getList ( "results.profile_visits.summary" ).size ();

        if(size>0) {

            String ActualCount=jsonParser.getJsonData ( "results.profile_visits.summary[0].count", ResponseDataType.INT);

            date=jsonParser.getJsonData ( "results.profile_visits.summary[0].date", ResponseDataType.STRING );

            if(String.valueOf (count).equals (ActualCount)){

                AssertAndWriteToReport ( true, "Number of profile visits for\t"+date+"\tis\t"+ActualCount);
            }else {

                AssertAndWriteToReport ( false, "Number of profile visits for\t"+date+"\tis\t"+ActualCount);
            }
        }else {

            AssertAndWriteToReport ( true, "Number of profile visits for\t"+date+"\tis\t"+size);
        }

    }

    @Then("check the session count and count should be $count")
    public void sessionCounr(@Named ( "count" )String count){
        info("Checking Number of session");

        String date="";

        stats.getAllcounts ();

        try{

            TimeUnit.SECONDS.sleep ( 5 );

        }catch ( Exception e ){


        }

        int size=response.getResponse ().jsonPath ().getList ( "results.sessions_count.summary" ).size ();

        if(size>0) {

            String ActualCount=jsonParser.getJsonData ( "results.sessions_count.summary[0].count", ResponseDataType.INT);

            date=jsonParser.getJsonData ( "results.sessions_count.summary[0].date", ResponseDataType.STRING );

            if(count.equals (ActualCount)){

                AssertAndWriteToReport ( true, "Number of Session for\t"+date+"\tis\t"+ActualCount);
            }else {

                AssertAndWriteToReport ( false, "Number of Session for\t"+date+"\tis\t"+ActualCount);
            }
        }else {

            AssertAndWriteToReport ( true, "Number of Session for\t"+date+"\tis\t"+size);
        }
    }

    @Then("check the total statistics between $from and $to")
    public void getStats(@Named ( "to" )String to,
                         @Named ( "from" )String from){


        info("Getting all expert statistics..");
        String toDate;

        if(to.toLowerCase ().equals ( "today" )){

            toDate=new SimpleDateFormat ( "yyyy-mm-dd" ).format (new Date ());
        }else {
            toDate=to;
        }

        stats.getAllStats(toDate, from);
        checkAndWriteToReport (response.statusCode (),

                "Total profile visit bewtween\t"+toDate+"\t"+from+"\tis"+stats.getNumProfileVisit ()+"\n"+
                        "Total Number of session bewtween\t"+toDate+"\t"+from+"\tis"+stats.getNumSession (), parameter.isNegative ( ));
    }
}
