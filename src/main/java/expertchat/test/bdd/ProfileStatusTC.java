package expertchat.test.bdd;// Created by Kishor on 4/25/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.bussinesslogic.ProfileComplete;
import expertchat.params.parameter;
import org.jbehave.core.annotations.Then;

public class ProfileStatusTC extends AbstractSteps{

    public ProfileStatusTC ( ExtentReports reports, String casName) {

        super(reports, casName);
    }

    private ProfileComplete profileComplete = new ProfileComplete();

    @Then ("check profile completness")
    public void checkProfileComplete() {

        info("Checking profile completeness");

        profileComplete.checkProfileCompletemness();

        boolean isComplete = profileComplete.isProfileComplete();

        if (isComplete) {

            this.AssertAndWriteToReport(isComplete, "Profile completed");

        } else if (!isComplete && parameter.isNegative ( )) {

            negativeCases();

        } else {

            this.AssertAndWriteToReport(false, "Profile Not Completed");
        }
    }
}
