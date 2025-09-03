# This feature file defines scenarios for the user signup process,
# including successful creation and handling of duplicate users.
@UserCreation
Feature: UserManagement Feature
  
  As a client of the API,
  I want to register new users and ensure duplicate registrations are handled,
  So that the user management system is robust.

  @P1
  Scenario: Successful user registration (Positive Scenario)
    Given a set of new user details are generated
    When a POST request is sent to the signup endpoint with the new user's details
    Then the response status code should be 200
    And the response body should contain the success message "User created successfully"
    And the response should match the user-success-schema.json schema

  @P1
  Scenario: Attempting to register with an existing email (Negative Scenario)
    Given a set of new user details are generated
    Given the user is already registered in the system
    When another POST request is sent to the signup endpoint with the same user details
    Then the response status code should be 400
    And the response body should contain the error detail "Email already registered"
    And the response should match the userCreationFailure.json schema

  @P1
  Scenario: Attempting to create a user with a malformed JSON payload
    Given a malformed JSON payload with a trailing comma is prepared
    When a POST request is sent to the signup endpoint with the malformed payload
    Then the response status code should be 422
      And the response should match the IncorrectJsonFomat.json schema

  @P2
  Scenario: Successful login with valid credentials
    Given a set of new user details are generated
    Given the user is successfully registered in the system
    When a POST request is sent to the login endpoint with their valid credentials
    Then the response status code should be 200
    And the response body should contain a valid bearer token
   And the response should match the LoginForAccessToken.json schema

  @P2
  Scenario Outline: Attempting to login with invalid credentials
    When a POST request is sent to the login endpoint with email "<email>" and password "<password>"
    Then the response status code should be 400
    And the response body should contain the error detail "Incorrect email or password"
     And the response should match the userLoginFailure.json schema

    Examples: 
      | email          | password          | description               |
      | yogi@gmail.com | 00000000000000000 | Correct email, wrong pass |
      | @@email.com    | abdld1@1324       | Wrong email, correct pass |
      | yogi@gmail.com |                   | Correct email, blank pass |
      |                | abdld1@1324       | Blank email, correct pass |
