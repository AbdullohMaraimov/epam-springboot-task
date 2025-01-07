package com.gym.crm.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.gym.crm.cucumber",
        plugin = {"pretty", "json:target/cucumber-report.json"}
)
public class RunCucumberTest {
}
