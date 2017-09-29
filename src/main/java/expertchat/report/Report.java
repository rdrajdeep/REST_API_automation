package expertchat.report;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.params.parameter;
import expertchat.util.ExpertChatUtility;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.steps.Steps;
import static expertchat.apioperation.apiresponse.HTTPCode.HTTP_BAD;
import static expertchat.apioperation.apiresponse.HTTPCode.HTTP_OK;


public class Report extends Steps {

    public static String rPath = ExpertChatUtility.getValue ( "report" ) +"/Report.html";
    protected ExtentReports reports;
    protected ExtentTest test;

    public Report ( ExtentReports reports, String casName ) {
        this.reports = reports;
        test = this.reports.startTest ( casName );

    }

    public void pass ( String description, String apiResponse ) {

        test.log ( LogStatus.PASS, description, apiResponse );
    }

    public void pass ( String description ) {

        test.log ( LogStatus.PASS, description );
        reports.flush ( );
    }

    public void fail ( String description ) {

        test.log ( LogStatus.FAIL, description );
        reports.flush ( );
    }


    public void warning ( String warning ) {

        test.log ( LogStatus.WARNING, warning );
        reports.flush ( );

    }

    public void info ( String information ) {

        test.log ( LogStatus.INFO, information );
        reports.flush ( );
    }

    public void fatal ( String information ) {

        test.log ( LogStatus.FATAL, information );
        reports.flush ( );
    }


    /**
     * @param statusCode
     * @param successMessage
     */
    public void checkAndWriteToReport ( int statusCode, String successMessage, boolean isNegative ) {

        if ( statusCode == HTTPCode.HTTP_OK || statusCode == HTTPCode.HTTP_ACCEPTED ) {

            pass ( successMessage );

        } else if ( isNegative && statusCode == HTTPCode.HTTP_BAD ) {

            pass ( "Negative Test Passed--" + ApiResponse.getObject ( ).getResponse ( ).prettyPrint ( ) );
            parameter.setIsNegative ( false );

        } else {
            fail ( "Something went wrong. Please check--" +
                    ApiResponse.getObject ( ).getResponse ( ).prettyPrint ( ) );
        }

    }

    public void checkErrorCode ( int serverCode, String expected ) {

        String s = ApiResponse.getObject ( ).getResponse ( ).jsonPath ( ).get ( "errors" ).toString ( );

        int i1 = s.indexOf ( "code" );
        int i2 = s.indexOf ( "message" );
        String actualCode = s.substring ( i1, i2 ).substring ( 0, s.substring ( i1, i2 ).length ( ) - 1 );

        if ( actualCode.contains ( expected ) && serverCode == HTTP_BAD ) {

            pass ( "Code matched.-->" + actualCode );
        } else {
            warning ( "Looks like you have not provided a proper code in test case" );
        }
    }

    public void checkSuccessCode ( int serverCode, String actual, String expected, boolean isNegative ) {

        if ( isNegative == false ) {
            if ( serverCode == HTTP_OK && expected.equals ( actual ) ) {

                pass ( "Code matched" + actual );

            } else {
                warning ( "Looks like you have not provided a proper code in test case" );
            }
        }
    }

    public void checkNonFiledError ( int serverCode, String actual, String expected ) {

        if ( serverCode == HTTP_BAD && expected.equals ( actual ) ) {

            pass ( "Code matched" + actual );

        } else {
            warning ( "Looks like you have not provided a proper code in test case" );
        }
    }

    public void AssertAndWriteToReport ( boolean bool, String... successMessage ) {

        if ( bool ) {

            pass ( successMessage[ 0 ] );
        } else {

            fail ( "Something went wrong. \t"+ApiResponse.getObject ().getResponse ().prettyPrint ());
        }
    }


    @AfterStory
    public void end ( ) {

        reports.flush ( );
        reports.endTest ( test );

    }

}
