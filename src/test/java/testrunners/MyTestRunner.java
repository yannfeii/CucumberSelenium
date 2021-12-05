package testrunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/AppFeatures"},
        glue = {"StepDefinitions","AppHooks"},
        plugin = {"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/HtmlReports/report.html"}
        //        plugin = {"pretty","junit:taget/JUnitReports/report.xml",
//                "json:taget/JUnitReports/report.json",
//                "html:target/HtmlReports"}
             )

public class MyTestRunner {

}
