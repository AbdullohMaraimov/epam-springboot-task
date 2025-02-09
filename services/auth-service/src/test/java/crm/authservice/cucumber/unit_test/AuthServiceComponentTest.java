package crm.authservice.cucumber.unit_test;

import crm.authservice.model.dto.LoginRequest;
import crm.authservice.model.dto.RegisterRequest;
import crm.authservice.model.dto.RegistrationResponse;
import crm.authservice.model.entity.AuthUser;
import crm.authservice.repository.UserRepository;
import crm.authservice.service.AuthService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@RequiredArgsConstructor
public class AuthServiceComponentTest {

    private final AuthService authService;

    private final UserRepository userRepository;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private ResponseEntity<?> response;

    @Before
    public void setup() {
        userRepository.deleteAll();
    }

    @Given("a new user with username {string} and password {string}")
    public void a_new_user_with_username_and_password(String username, String password) {
        registerRequest = new RegisterRequest(username, password);
    }

    @When("the user attempts to register")
    public void the_user_attempts_to_register() throws Exception {
        response = authService.register(registerRequest);
    }

    @Then("the registration should be successful")
    public void the_registration_should_be_successful() {
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        RegistrationResponse registrationResponse = (RegistrationResponse) response.getBody();
        Assertions.assertNotNull(registrationResponse);
        Assertions.assertEquals(registerRequest.username(), registrationResponse.username());
    }

    @Given("an existing user with username {string} and password {string}")
    public void an_existing_user_with_username_and_password(String username, String password) {
        AuthUser user = AuthUser.builder().username(username).password(password).build();
        userRepository.save(user);
    }

    @Given("a login request with username {string} and password {string}")
    public void a_login_request_with_username_and_password(String username, String password) {
        loginRequest = new LoginRequest(username, password);
    }

    @When("the user attempts to login")
    public void the_user_attempts_to_login() {
        response = authService.login(loginRequest);
    }

    @Then("the login should be successful")
    public void the_login_should_be_successful() {
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        String token = (String) response.getBody();
        Assertions.assertNotNull(token);
    }

    @Then("the registration should fail with status {int}")
    public void the_registration_should_fail_with_status(int statusCode) {
        Assertions.assertNotNull(response);
        Assertions.assertEquals(statusCode, response.getStatusCodeValue());
    }
}

