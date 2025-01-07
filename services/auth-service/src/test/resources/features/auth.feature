Feature: Authentication Management

  Scenario: Register new user
    Given user submits registration request with username "iman.gadzhi" and password "12345678"
    Then user should be registered successfully
    And api should return username "iman.gadzhi"

  Scenario: Register again with existing user
    Given user "iman.gadzhi" exists with password "12345678"
    When user submits registration request with username "iman.gadzhi" and password "12345678"
    Then user should see registration failed

  Scenario: Login with existing user
    Given user "iman.gadzhi" exists with password "12345678"
    When user calls the "/auth/login" api with valid credentials
    Then api should return JWT token

  Scenario: Log in with invalid credentials
    Given user "iman.gadzhi" exists with password "12345678"
    When user calls "/auth/login" api with username "iman.gadzhi" and password "12345677"
    Then api should return unauthorized error