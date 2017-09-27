package expertchat.test.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.bussinesslogic.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class DeleteContentOfAPI extends AbstractSteps{

    public DeleteContentOfAPI(ExtentReports reports, String casName) {
        super(reports, casName);
    }

    SessionUtil objSlot= new SessionUtil();
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
