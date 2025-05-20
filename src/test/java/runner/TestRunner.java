package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/src/test/java/resource/features/Case3_CriticalPath.feature",
        glue = {"steps","hooks"},                            
        plugin = {
                "pretty",
                "html:target/cucumber-report.html" 
        },
        monochrome = true
)
public class TestRunner {


}
