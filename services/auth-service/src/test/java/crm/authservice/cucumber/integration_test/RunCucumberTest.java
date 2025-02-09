package crm.authservice.cucumber.integration_test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "crm.authservice.cucumber.integration_test",
        plugin = {"pretty", "json:target/cucumber-report.json"}
)
public class RunCucumberTest {
}
