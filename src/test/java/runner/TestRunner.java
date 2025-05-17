package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import steps.Case1Steps;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/src/test/java/resource/features/Case2_PriceSort.feature",   // .feature dosyalarının yolu
        glue = "steps",                             // Step definition klasörü
        plugin = {
                "pretty",
                "html:target/cucumber-report.html"      // Rapor çıktısı
        },
        monochrome = true
)
public class TestRunner {


}
