package com.gym.crm.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.crm.model.dto.request.TraineeRequest;
import com.gym.crm.model.dto.request.TrainerRequest;
import com.gym.crm.model.dto.request.TrainingRequest;
import com.gym.crm.model.dto.response.ApiResponse;
import com.gym.crm.model.dto.response.TraineeResponse;
import com.gym.crm.model.dto.response.TrainerResponse;
import com.gym.crm.model.entity.Trainee;
import com.gym.crm.model.entity.Trainer;
import com.gym.crm.repository.TraineeRepository;
import com.gym.crm.repository.TrainerRepository;
import com.gym.crm.service.TraineeService;
import com.gym.crm.service.TrainerService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Slf4j
@RequiredArgsConstructor
public class CrmStepDefinition {

    private final TraineeService traineeService;
    private final MockMvc mockMvc;
    private MockHttpServletResponse response;
    private final ObjectMapper objectMapper;
    private final TraineeRepository traineeRepository;
    private final TrainerService trainerService;
    private final TrainerRepository trainerRepository;

    @Given("Trainee with username {string}")
    public void givenTrainee(String username) throws JsonProcessingException {
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElse(null);

        if (trainee == null) {
            traineeService.create(new TraineeRequest(
                    "Ali",
                    "Vali",
                    LocalDate.of(2000, 1, 1),
                    "USA",
                    true
            ));
        }
    }

    @When("do update request to {string} to change first name to {string}")
    public void sendUpdateRequest(String endPoint, String username) {
        TraineeRequest updateRequest = new TraineeRequest(
                    "Jim",
                    "Vali",
                    LocalDate.of(2000, 1, 1),
                    "USA",
                    true);

        try {
            response = mockMvc.perform(patch(endPoint)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
                    .andReturn()
                    .getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("request should return success message updated username should be {string}")
    public void successMessage(String firstName) throws UnsupportedEncodingException, JsonProcessingException {
        ApiResponse<TraineeResponse> apiResponse = objectMapper.readValue(
                response.getContentAsString(),
                new TypeReference<ApiResponse<TraineeResponse>>() {}
        );

        TraineeResponse traineeResponse = apiResponse.data();
        assertEquals(firstName, traineeResponse.firstName());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(apiResponse.success());
    }


    @When("do update password of user with username {string} to {string} request to {string}")
    public void updateRequest(String username, String newPassword, String endPoint) throws Exception {
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username %s not found".formatted(username)));

        response = mockMvc.perform(MockMvcRequestBuilders.patch(endPoint)
                        .param("username", username)
                        .param("oldPassword", trainee.getPassword())
                        .param("newPassword", newPassword))
                .andReturn().getResponse();
    }

    @Then("request should return success message")
    public void assertSuccess() {
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @When("send request to {string} to deactivate with param {string}")
    public void deactivate(String api, String username) throws Exception {
        response = mockMvc.perform(MockMvcRequestBuilders.patch(api )
                        .param("username", username))
                .andReturn().getResponse();
    }

    @Then("trainee should be deactivated with username {string}")
    public void checkActive(String username) {
        TraineeResponse traineeResponse = traineeService.findByUsername(username);
        assertFalse(traineeResponse.isActive());
    }

    @When("send request to {string} to activate with param {string}")
    public void activate(String api, String username) throws Exception {
        response = mockMvc.perform(MockMvcRequestBuilders.patch(api)
                        .param("username", username))
                .andReturn().getResponse();
    }

    @Then("trainee should be activated with username {string}")
    public void checkActivated(String username) {
        TraineeResponse traineeResponse = traineeService.findByUsername(username);
        assertTrue(traineeResponse.isActive());
    }

    @Given("Trainee with firstName {string}, lastname {string}")
    public void givenTrainee(String firstName, String lastName) throws JsonProcessingException {
        TraineeRequest traineeRequest = new TraineeRequest(
                firstName,
                lastName,
                LocalDate.now(),
                "USA",
                true
        );
        traineeService.create(traineeRequest);
    }

    @When("send request to {string}")
    public void findTraineeByUsername(String api) throws Exception {
        response = mockMvc.perform(get(api)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    @Then("Check firstName and lastname")
    public void checkResult() throws UnsupportedEncodingException, JsonProcessingException {
        ApiResponse<TraineeResponse> apiResponse = objectMapper.readValue(
                response.getContentAsString(),
                new TypeReference<>() {}
        );
        assertEquals("John", apiResponse.data().firstName());
        assertEquals("Doe", apiResponse.data().lastName());
    }

    @When("send request to {string} to delete trainee")
    public void sendDeleteRequest(String url) throws Exception {
        response = mockMvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    @Then("request should return success message of 200")
    public void checkSuccess() throws UnsupportedEncodingException, JsonProcessingException {
        ApiResponse<Void> apiResponse = objectMapper.readValue(
                response.getContentAsString(),
                new TypeReference<ApiResponse<Void>>() {}
        );
        assertEquals(200, apiResponse.statusCode());
    }


    @Given("Trainer with firstName {string}, lastname {string}")
    public void givenTrainer(String firstName, String lastName) throws JsonProcessingException {
        TrainerRequest traineeRequest = new TrainerRequest(
                firstName,
                lastName,
                1L,
                true
        );
        trainerService.create(traineeRequest);
    }

    @Then("Check firstName and lastname of trainer")
    public void checkResultTrainer() throws UnsupportedEncodingException, JsonProcessingException {
        ApiResponse<TrainerResponse> apiResponse = objectMapper.readValue(
                response.getContentAsString(),
                new TypeReference<>() {}
        );
        assertEquals("Jill", apiResponse.data().firstName());
        assertEquals("Roy", apiResponse.data().lastName());
    }

    @When("do update password of trainer with username {string} to {string} request to {string}")
    public void updateTrainerPasswordRequest(String username, String newPassword, String endPoint) throws Exception {
        Trainer trainer = trainerRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainer with username %s not found".formatted(username)));

        response = mockMvc.perform(MockMvcRequestBuilders.patch(endPoint)
                        .param("username", username)
                        .param("oldPassword", trainer.getPassword())
                        .param("newPassword", newPassword))
                .andReturn().getResponse();
    }


    @When("send request to {string} to deactivate trainer with param {string}")
    public void deactivateTrainer(String api, String username) throws Exception {
        response = mockMvc.perform(MockMvcRequestBuilders.patch(api)
                        .param("username", username))
                .andReturn().getResponse();
    }

    @Then("trainer should be deactivated with username {string}")
    public void checkActiveTrainer(String username) {
        TrainerResponse trainerResponse = trainerService.findByUsername(username);
        assertFalse(trainerResponse.isActive());
    }


    @When("send request to {string} to activate trainer with param {string}")
    public void activateTrainer(String api, String username) throws Exception {
        response = mockMvc.perform(MockMvcRequestBuilders.patch(api)
                        .param("username", username))
                .andReturn().getResponse();
    }

    @Then("trainer should be activated with username {string}")
    public void checkActivatedTrainer(String username) {
        TrainerResponse trainerResponse = trainerService.findByUsername(username);
        assertTrue(trainerResponse.isActive());
    }

    @When("send request to {string} to create training with trainee {string}, trainer {string}")
    public void createTraining(String api, String traineeUsername, String trainerUsername) throws Exception {
        TraineeResponse traineeResponse = traineeService.findByUsername(traineeUsername);
        TrainerResponse trainerResponse = trainerService.findByUsername(trainerUsername);
        TrainingRequest trainingRequest = new TrainingRequest(
                traineeResponse.userId(),
                trainerResponse.id(),
                "GYM",
                1L,
                LocalDate.now(),
                Duration.ZERO
        );
        log.info("sending request");
        response = mockMvc.perform(post(api)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainingRequest))
                        .header("Authorization", "Bearer init"))
                .andReturn()
                .getResponse();

    }

}
