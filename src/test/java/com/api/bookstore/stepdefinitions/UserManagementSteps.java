package com.api.bookstore.stepdefinitions;

import com.api.bookstore.assertions.WrappedAssert;
import com.api.bookstore.endpoints.EndPoints;
import com.api.bookstore.logs.WrappedReportLogger;
import com.api.bookstore.requestBuilder.ApiClient;
import com.api.bookstore.requestBuilder.RequestBuilder;
import com.api.bookstore.requestpojos.UserManagement;
import com.api.bookstore.responsepojos.Detail;
import com.api.bookstore.responsepojos.LoginForAccessTokenResponse;
import com.api.bookstore.responsepojos.Message;
import com.api.bookstore.testcontext.TestContext;
import com.api.bookstore.utils.DataGenerator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;


public class UserManagementSteps {
	
	  public String malformedJsonPayload;
	  public static Response response;
	  

	    @Given("a set of new user details are generated")
	    public void a_set_of_new_user_details_are_generated() {
	        WrappedReportLogger.trace("Generating random user details for the scenario...");
	        String newEmail = DataGenerator.randomEmail();
	        int newId = DataGenerator.randomID();
	        String newPwd = DataGenerator.randomPwd();

	        UserManagement  userSignupRequest = new UserManagement();
	        TestContext.setUserSignupRequest(userSignupRequest);
	        TestContext.getUserSignupRequest().setId(newId);
	        TestContext.getUserSignupRequest().setEmail(newEmail);
	        TestContext.getUserSignupRequest().setPassword(newPwd);
	        WrappedReportLogger.trace("Generated user details with email: " + newEmail + " and ID: " + newId);
	    }

	    @And("the user is already registered in the system")
	    public void the_user_is_already_registered_in_the_system() {
	        WrappedReportLogger.trace("Pre-registering user to test duplicate creation...");
	        ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(TestContext.getUserSignupRequest(), null, null), EndPoints.SIGNUP);
	        WrappedReportLogger.trace("User with email " + TestContext.getUserSignupRequest().getEmail() + " is now registered.");
	    }

	    @When("a POST request is sent to the signup endpoint with the new user's details")
	    public void a_post_request_is_sent_to_the_signup_endpoint_with_the_new_user_s_details() {
	        WrappedReportLogger.trace("Sending POST request to create a new user...");
	        response = ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(TestContext.getUserSignupRequest(), null, null), EndPoints.SIGNUP);
	        TestContext.setResponse(response);
	    }
	    
	    @When("another POST request is sent to the signup endpoint with the same user details")
	    public void another_post_request_is_sent_to_the_signup_endpoint_with_the_same_user_details() {
	        WrappedReportLogger.trace("Sending another POST request with the same user details...");
	        response = ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(TestContext.getUserSignupRequest(), null, null), EndPoints.SIGNUP);
	        TestContext.setResponse(response);
	    }

	    @Then("the response status code should be {int}")
	    public void the_response_status_code_should_be(int expectedStatusCode) {
	        WrappedReportLogger.trace("Asserting that the response status code is " + expectedStatusCode);
	        WrappedAssert.assertEquals(TestContext.getResponse().getStatusCode(), expectedStatusCode, "Validating response status code.");
	    }

	    @And("the response body should contain the success message {string}")
	    public void the_response_body_should_contain_the_success_message(String successMessage) {
	        WrappedReportLogger.trace("Asserting the success message in the response body...");
	        Message messageResponse =TestContext.getResponse().as(Message.class);
	        WrappedAssert.assertEquals(messageResponse.getMessage(), successMessage, "Validating success message value");
	        WrappedReportLogger.trace("Verified new user was created successfully.");
	    }
	    
	    @And("the response body should contain the error detail {string}")
	    public void the_response_body_should_contain_the_error_detail(String errorDetail) {
	        WrappedReportLogger.trace("Asserting the error detail in the response body...");
	        Detail detailResponse = TestContext.getResponse().as(Detail.class);
	        WrappedAssert.assertEquals(detailResponse.getDetail(), errorDetail, "Validating detail value for existing user");
	        WrappedReportLogger.trace("Verified that an existing user cannot be created again.");
	    }


	    @Given("the user is successfully registered in the system")
	    public void the_user_is_successfully_registered_in_the_system() {
	        WrappedReportLogger.trace("Ensuring user is registered before running login tests...");
	        // This API call creates the user generated in the Background step.
	        ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(TestContext.getUserSignupRequest(), null, null), EndPoints.SIGNUP);
	        WrappedReportLogger.trace("User with email " + TestContext.getUserSignupRequest() + " is now registered.");
	    }

	    @When("a POST request is sent to the login endpoint with their valid credentials")
	    public void a_post_request_is_sent_to_the_login_endpoint_with_their_valid_credentials() {
	        UserManagement userloginRequest = new UserManagement();
	        userloginRequest.setId(TestContext.getUserSignupRequest().getId());
	        userloginRequest.setEmail(TestContext.getUserSignupRequest().getEmail());
	        userloginRequest.setPassword(TestContext.getUserSignupRequest().getPassword());

	        WrappedReportLogger.trace("Sending login request for user: " + userloginRequest.getEmail());
	        response = ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(userloginRequest, null, null), EndPoints.LOGIN);
	        TestContext.setResponse(response);
	    }

	    @Then("the response body should contain a valid bearer token")
	    public void the_response_body_should_contain_a_valid_bearer_token() {
	        WrappedReportLogger.trace("Validating the login response token...");
	        LoginForAccessTokenResponse loginResponse = TestContext.getResponse().as(LoginForAccessTokenResponse.class);

	        WrappedAssert.assertEquals(loginResponse.getToken_type(), "bearer", "Validating token type value");
	        WrappedAssert.assertNotNull(loginResponse.getAccess_token(), "Validating if access token is not null");

	        // Store the access token in the context for use in subsequent API calls
	        String authToken = loginResponse.getAccess_token();
	        TestContext.setAuthToken(authToken);
	        WrappedReportLogger.trace("Login successful and token stored in context.");
	    }

	    @When("a POST request is sent to the login endpoint with email {string} and password {string}")
	    public void a_post_request_is_sent_to_the_login_endpoint_with_email_and_password(String email, String password) {
	    	UserManagement loginRequest = new UserManagement();
	    	int id=DataGenerator.randomID();
	        loginRequest.setId(id); 
	        loginRequest.setEmail(email);
	        loginRequest.setPassword(password);

	        WrappedReportLogger.trace("Sending login request with invalid credentials (Email: " + email + ")");
	        response = ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(loginRequest, null, null), EndPoints.LOGIN);
	        TestContext.setResponse(response);
	    }
	    
	    @Given("a malformed JSON payload with a trailing comma is prepared")
	    public void a_malformed_json_payload_with_a_trailing_comma_is_prepared() {
	        WrappedReportLogger.trace("Preparing an incorrectly formatted JSON payload...");
	        this.malformedJsonPayload = "{\n" +
	                "  \"id\": 12345,\n" +
	                "  \"email\": \"siva@gmail.com\",\n" +
	                "  \"password\": \"siva123\",\n" +  
	                "}";
	        WrappedReportLogger.trace("Payload prepared.");
	    }

	    @When("a POST request is sent to the signup endpoint with the malformed payload")
	    public void a_post_request_is_sent_to_the_signup_endpoint_with_the_malformed_payload() {
	        WrappedReportLogger.trace("Sending malformed JSON to the /signup endpoint...");
	        response = ApiClient.post(
	                RequestBuilder.withBodyAndNoAuthToken(this.malformedJsonPayload, null, null),
	                EndPoints.SIGNUP);
	        TestContext.setResponse(response);
	    }

	    @And("the response should match the user-success-schema.json schema")
	    public void the_response_should_match_the_user_Success_schema() {
	        WrappedReportLogger.trace("Validating user registration response against schema: " + "Message.json");
	        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(),EndPoints.SIGNUP,  "Message.json");
	    }
	    @And("the response should match the userCreationFailure.json schema")
	    public void the_response_should_match_the_userCreationFailure_schema() {
	        WrappedReportLogger.trace("Validating user registration response against schema: " + "Detail.json");
	        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(),EndPoints.SIGNUP,  "Detail.json");
	    }
	    @And("the response should match the IncorrectJsonFomat.json schema")
	    public void the_response_should_match_the_IncorrectJsonFomat_schema() {
	        WrappedReportLogger.trace("Validating user registration response against schema: " + "IncorrectJsonFomat.json");
	        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(),EndPoints.SIGNUP,  "IncorrectJsonFomat.json");
	    }
	    @And("the response should match the LoginForAccessToken.json schema")
	    public void the_response_should_match_the_LoginForAccessToken_schema() {
	        WrappedReportLogger.trace("Validating user registration response against schema: " + "LoginForAccessToken.json");
	        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(),EndPoints.LOGIN,  "LoginForAccessToken.json");
	    }
	    @And("the response should match the userLoginFailure.json schema")
	    public void the_response_should_match_the_userLoginFailure_schema() {
	        WrappedReportLogger.trace("Validating user registration response against schema: " + "Detail.json");
	        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(),EndPoints.LOGIN,  "Detail.json");
	    }
	  
}
