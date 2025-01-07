Feature: Trainee operations

  Scenario: Update trainee by username
    Given Trainee with username "Ali.Vali"
    When do update request to "/api/v1/trainee/Ali.Vali" to change first name to "Jim"
    Then request should return success message updated username should be "Jim"

  Scenario: Update user password
    Given Trainee with username "Ali.Vali"
    When do update password of user with username "Ali.Vali" to "12345678" request to "/api/v1/trainee/update-password"
    Then request should return success message

  Scenario: Deactivate user by username
    Given Trainee with username "Ali.Vali"
    When send request to "/api/v1/trainee/de-activate" to deactivate with param "Ali.Vali"
    Then request should return success message
    Then trainee should be deactivated with username "Ali.Vali"

  Scenario: Activate user by username
    Given Trainee with username "Ali.Vali"
    When send request to "/api/v1/trainee/activate" to activate with param "Ali.Vali"
    Then request should return success message
    Then trainee should be activated with username "Ali.Vali"

  Scenario: Find trainee with username
    Given Trainee with firstName "John", lastname "Doe"
    When send request to "/api/v1/trainee/John.Doe"
    Then request should return success message
    Then Check firstName and lastname

  Scenario: Delete trainee with username
    Given Trainee with username "Ali.Vali"
    When send request to "/api/v1/trainee/Ali.Vali" to delete trainee
    Then request should return success message of 200
