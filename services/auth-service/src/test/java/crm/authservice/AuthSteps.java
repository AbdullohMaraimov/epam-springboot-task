package crm.authservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crm.authservice.model.dto.LoginRequest;
import crm.authservice.model.dto.RegisterRequest;
import crm.authservice.model.dto.RegistrationResponse;
import crm.authservice.model.entity.AuthUser;
import crm.authservice.repository.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RequiredArgsConstructor
public class AuthSteps {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private MockHttpServletResponse response;
    private final ObjectMapper objectMapper;


    @Given("user submits registration request with username {string} and password {string}")
    public void submitRegistrationRequest(String username, String password) {
        RegisterRequest request = new RegisterRequest(username, password);

        try {
            response = mockMvc.perform(post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andReturn()
                    .getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("user should be registered successfully")
    public void userRegisteredSuccessfully() {
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Then("api should return username {string}")
    public void apiReturnsUsername(String username) throws UnsupportedEncodingException, JsonProcessingException {
        RegistrationResponse registrationResponse = objectMapper.readValue(response.getContentAsString(), RegistrationResponse.class);
        assertEquals(username, registrationResponse.username());
    }


    @Then("user should see registration failed")
    public void registrationFails() {
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Given("user {string} exists with password {string}")
    public void userExists(String username, String password) {
        if (userRepository.findByUsername(username).isEmpty()) {
            AuthUser user = AuthUser.builder()
                    .username(username)
                    .password(password)
                    .build();
            userRepository.save(user);
        }
    }

    @When("user calls the {string} api with valid credentials")
    public void callLoginApi(String api) {
        LoginRequest loginRequest = new LoginRequest("iman.gadzhi", "12345678");
        try {
            response = mockMvc.perform(post(api)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andReturn()
                    .getResponse();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("api should return JWT token")
    public void apiReturnsJwt() {
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        try {
            assertNotNull(response.getContentAsString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    @When("user calls {string} api with username {string} and password {string}")
    public void callLoginApiWithInvalidCredentials(String apiEndpoint, String username, String password) {
        LoginRequest request = new LoginRequest(username, password);
        try {
            response = mockMvc.perform(post(apiEndpoint)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andReturn()
                    .getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("api should return unauthorized error")
    public void apiReturnsUnauthorizedError() {
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

}
