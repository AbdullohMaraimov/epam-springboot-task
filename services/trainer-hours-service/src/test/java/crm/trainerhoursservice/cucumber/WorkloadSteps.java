package crm.trainerhoursservice.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crm.trainerhoursservice.model.constant.ActionType;
import crm.trainerhoursservice.model.dto.TrainerWorkload;
import crm.trainerhoursservice.model.entity.dev.TrainingHour;
import crm.trainerhoursservice.model.entity.prod.WorkloadSummary;
import crm.trainerhoursservice.repository.WorkloadRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RequiredArgsConstructor
public class WorkloadSteps {

    private final MockMvc mockMvc;
    private MockHttpServletResponse response;
    private final ObjectMapper objectMapper;
    private TrainerWorkload trainerWorkload;

    @Given("workload with username {string}, firstname {string}, lastname {string}, status {string}, date {string}, duration {double}")
    public void processWorkloadSuccessfully(String username, String firstName, String lastName, String status, String trainingDate, double duration) {
        trainerWorkload = new TrainerWorkload(
                username,
                firstName,
                lastName,
                Boolean.parseBoolean(status),
                LocalDate.parse(trainingDate),
                duration,
                ActionType.ADD
        );
    }

    @When("send post request to {string} to process")
    public void sendRequestToApi(String api) {
        try {
            response = mockMvc.perform(post(api)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(trainerWorkload)))
                    .andReturn()
                    .getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("receive status code 200")
    public void processSuccessful() {
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }



    @Given("username of trainer {string} send get request to {string} to get summary")
    public void usernameOfTrainer(String username, String endPoint) {
        try {
            response = mockMvc.perform(get(endPoint)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("get response code of 200 which is successful")
    public void summarySuccessful() {
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Then("check username {string} matches the request, and make sure getting result")
    public void checkResult(String username) throws UnsupportedEncodingException, JsonProcessingException {
        WorkloadSummary workloadSummary = objectMapper.readValue(response.getContentAsString(), WorkloadSummary.class);
        assertEquals(username, workloadSummary.getUsername());
    }

}
