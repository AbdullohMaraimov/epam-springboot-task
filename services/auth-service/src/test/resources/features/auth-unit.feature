Feature: AuthService Component Tests

  Scenario: Register a new user successfully
    Given a new user with username "testUser" and password "password123"
    When the user attempts to register
    Then the registration should be successful

  Scenario: Fail to register an existing user
    Given an existing user with username "testUser" and password "password123"
    Given a new user with username "testUser" and password "password123"
    When the user attempts to register
    Then the registration should fail with status 400

  Scenario: Login with valid credentials
    Given an existing user with username "testUser" and password "password123"
    Given a login request with username "testUser" and password "password123"
    When the user attempts to login
    Then the login should be successful
