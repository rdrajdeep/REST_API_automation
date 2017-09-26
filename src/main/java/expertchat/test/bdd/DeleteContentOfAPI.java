package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.bussinesslogic.ExpertChatApi;
import expertchat.bussinesslogic.ExpertProfile;
import expertchat.bussinesslogic.SessionScheduleAndRevenueCheckWithPromo;
import expertchat.params.Credentials;
import expertchat.params.parameter;
import expertchat.usermap.TestUserMap;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static expertchat.usermap.TestUserMap.getMap;

public class DeleteContentOfAPI extends AbstractSteps{

    public DeleteContentOfAPI(ExtentReports reports, String casName) {
        super(reports, casName);
    }

    SessionScheduleAndRevenueCheckWithPromo objSlot= new SessionScheduleAndRevenueCheckWithPromo();
    List<String> slots= new ArrayList<String>();


    /*@Then("delete all available slots")
        public void getSlot(){

        System.out.println("--****- GETTING All SLOT NOW--***-");
        //objSlot.getallSlots();
        //slots=objSlot.getallSlots();

        Iterator i = slots.iterator();

        while (i.hasNext())
        {
            objSlot.deleteSlot("\""+i+"\"");
        }

        this.checkAndWriteToReport(response.statusCode(),"Slot extracted", parameter.isNegative());

    }
*/
}
