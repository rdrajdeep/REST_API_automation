package expertchat.test;

// Created by Kishor on 1/24/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.report.Report;
import expertchat.test.bdd.*;
import expertchat.util.ExpertChatException;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.StoryTimeouts;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.BeforeClass;
import java.util.List;

/* Story configurator*/

public class TestStoryConfig extends JUnitStories {

    private  static ExtentReports extent = null;


    @BeforeClass
    public static void setReport ( ) {

        extent = new ExtentReports ( Report.rPath, true );

        if ( extent == null ) {
            throw new ExpertChatException ( "Something serious has just happened!!!!!" );
        }
    }

    public ExtentReports getReport ( ) {

        if ( extent != null ) {

            return extent;
        }
        return null;
    }

    @Override
    public Embedder configuredEmbedder ( ) {

        final EmbedderControls embedderControls = new EmbedderControls ( );
        embedderControls.ignoreFailureInStories ( );
        embedderControls.doVerboseFailures ( false );
        embedderControls.ignoreFailureInView ( );
        embedderControls.useThreads ( 1 );
        embedderControls.failOnStoryTimeout ( );

        final Embedder embedder = new Embedder ( );
        final Configuration configuration = configuration ( );

        StoryTimeouts.TimeoutParser t = new StoryTimeouts.TimeoutParser ( ) {
            @Override
            public boolean isValid ( String timeout ) {
                return true;
            }

            @Override
            public long asSeconds ( String timeout ) {
                return 500;
            }
        };

        embedder.useTimeoutParsers ( t );
        embedder.useConfiguration ( configuration );
        embedder.useStepsFactory ( stepsFactory ( ) );

        return embedder;
    }

    @Override
    public Configuration configuration ( ) {

        return new MostUsefulConfiguration ( )
                .useStoryLoader ( new LoadFromClasspath ( this.getClass ( ) ) );
    }

    @Override
    public List < String > storyPaths ( ) {

        List < String > s = new StoryFinder ( ).
                findPaths ( CodeLocations.codeLocationFromClass ( this.getClass ( ) ),
                        "**/*.story", null );
        return s;
    }

    @Override
    public InjectableStepsFactory stepsFactory ( ) {

        return new InstanceStepsFactory ( configuration ( ),

                /*new BasicFlowTC ( getReport (), "Log-in and registration flow"),
                new BasicProfileTC (getReport (), "Basic profile flow"),
                new ExpertProfileTC (getReport (), "Expert profile flow"),
                /*new SocialLinkTC ( getReport (), "Social Link  flow" ),
                new CallingTC ( getReport (), "Calling flow" ),
                new PhoneVerificationTC ( getReport (), "Phone number verification flow"),
                new PaymentInfoTC ( getReport (), "Payment information flow"),
                new ProfileStatusTC ( getReport (), "profile status check flow" ),
                new SearchTC (getReport (), "SOLR Search flow"),
                new MyStatesTC (getReport (), "Expert Analytics flow"),
                new SuperAdminTC ( getReport (), "Super Admin flow" ),
                new GetStreamTC (getReport (), "Get Stream flow"),*/
               new SessionTC(getReport(),"Promocode test")
        );
    }
}
