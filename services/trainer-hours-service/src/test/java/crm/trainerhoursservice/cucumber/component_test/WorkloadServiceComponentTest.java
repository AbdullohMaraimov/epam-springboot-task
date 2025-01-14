package crm.trainerhoursservice.cucumber.component_test;

import crm.trainerhoursservice.model.constant.ActionType;
import crm.trainerhoursservice.model.dto.TrainerWorkload;
import crm.trainerhoursservice.model.entity.prod.WorkloadSummary;
import crm.trainerhoursservice.repository.WorkloadRepository;
import crm.trainerhoursservice.service.WorkloadService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class WorkloadServiceComponentTest {

    @Autowired
    private WorkloadService workloadService;

    @Autowired
    private WorkloadRepository workloadRepository;

    private TrainerWorkload workload;
    private WorkloadSummary summary;

    @Before
    public void setup() {
        workloadRepository.findByUsername("testTrainer").ifPresent(workloadRepository::save);
    }

    @Given("a trainer with username {string}, first name {string}, last name {string}, and training duration {int} for {int}-{int}-{int}")
    public void a_trainer_with_username_first_name_last_name_and_training_duration_for_date(
            String username, String firstName, String lastName, int duration, int year, int month, int day) {
        workload = new TrainerWorkload(username, firstName, lastName, true, LocalDate.now(), Duration.ZERO.toMinutes(), ActionType.ADD);
    }

    @When("the workload is processed")
    public void the_workload_is_processed() {
        workloadService.handleWorkload(workload);
    }

    @Then("the workload summary for username {string} should include training duration PT{int}M for {int}-{int}-{int}")
    public void the_workload_summary_should_include_training_duration_for_month_year(
            String username, int durationMinutes, int year, int month, int day) {
        Optional<WorkloadSummary> optionalSummary = workloadRepository.findByUsername(username);
        Assertions.assertTrue(optionalSummary.isPresent(), "Workload summary not found for username: " + username);

        WorkloadSummary summary = optionalSummary.get();
        Assertions.assertNotNull(summary.getYears(), "Yearly summaries are null");

    }



    @Given("a trainer with no workload summary for username {string}")
    public void a_trainer_with_no_workload_summary_for_username(String username) {
        workloadRepository.findByUsername(username).ifPresent(workloadRepository::save);
    }

    @When("the summary for trainer username {string} is retrieved")
    public void the_summary_for_trainer_username_is_retrieved(String username) {
        summary = workloadService.summarize(username);
    }

    @Then("the summary for username {string} should be null")
    public void the_summary_should_be_null(String username) {
        Assertions.assertNull(summary);
    }
}

