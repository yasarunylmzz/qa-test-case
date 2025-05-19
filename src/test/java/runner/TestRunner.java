package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/src/test/java/resource/features/Case4_DataExtraction.feature",   // .feature dosyalarının yolu
        glue = {"steps","hooks"},                             // Step definition klasörü
        plugin = {
                "pretty",
                "html:target/cucumber-report.html"      // Rapor çıktısı
        },
        monochrome = true
)
public class TestRunner {


}
