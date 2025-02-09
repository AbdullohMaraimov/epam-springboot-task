Feature: Trainee operations

  Scenario: Update trainee by username
    Given Trainee with username "Ali.Vali"
    When do update request to "/api/v1/trainee/Ali.Vali" to change first name to "Jim"
    Then request should return success message updated username should be "Jim"

  Scenario: Find trainee with username
    Given Trainee with firstName "John", lastname "Doe"
    When send request to "/api/v1/trainee/John.Doe"
    Then request should return success message
    Then Check firstName and lastname

  Scenario: Delete trainee with username
    Given Trainee with username "Ali.Vali"
    When send request to "/api/v1/trainee/Ali.Vali" to delete trainee
    Then request should return success message of 200

  Scenario: Find trainer with username
    Given Trainer with firstName "Jill", lastname "Roy"
    When send request to "/api/v1/trainer/Jill.Roy"
    Then request should return success message
    Then Check firstName and lastname of trainer

  Scenario: Create training
    Given Trainer with firstName "Jill", lastname "Roy"
    Given Trainee with username "Ali.Vali"
    When send request to "/api/v1/training" to create training with trainee "Ali.Vali", trainer "Jill.Roy"
    Then request should return success message