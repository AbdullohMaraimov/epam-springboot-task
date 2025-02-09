Feature: CRM operations

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

  Scenario: Deactivate trainer by username
    Given Trainer with firstName "Jill", lastname "Roy"
    When send request to "/api/v1/trainer/de-activate" to deactivate trainer with param "Jill.Roy"
    Then request should return success message
    Then trainer should be deactivated with username "Jill.Roy"

  Scenario: Activate trainer by username
    Given Trainer with firstName "Jill", lastname "Roy"
    When send request to "/api/v1/trainer/activate" to activate trainer with param "Jill.Roy"
    Then request should return success message
    Then trainer should be activated with username "Jill.Roy"

  Scenario: Update trainer password
    Given Trainer with firstName "Jill", lastname "Roy"
    When do update password of trainer with username "Jill.Roy" to "12345678" request to "/api/v1/trainer/update-password"
    Then request should return success message