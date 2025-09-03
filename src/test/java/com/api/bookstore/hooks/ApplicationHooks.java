package com.api.bookstore.hooks;

import com.api.bookstore.endpoints.EndPoints;
import com.api.bookstore.requestBuilder.ApiClient;
import com.api.bookstore.requestBuilder.RequestBuilder;
import com.api.bookstore.requestpojos.UserManagement;
import com.api.bookstore.responsepojos.LoginForAccessTokenResponse;
import com.api.bookstore.testcontext.TestContext;
import com.api.bookstore.utils.DataGenerator;

import io.cucumber.java.BeforeAll;
import io.restassured.response.Response;

public class ApplicationHooks {
	
	

	@BeforeAll
	public static void registerAndLoginUserForAccessToken()
	{
	        String newEmail = DataGenerator.randomEmail();
	        int newId = DataGenerator.randomID();
	        String newPwd = DataGenerator.randomPwd();
	        	UserManagement userSignupRequest=new UserManagement();
	        	userSignupRequest.setId(newId);
	        	userSignupRequest.setEmail(newEmail);
	        	userSignupRequest.setPassword(newPwd);
	        	TestContext.setUserSignupRequest(userSignupRequest);
		        ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(TestContext.getUserSignupRequest(), null, null), EndPoints.SIGNUP);
		        UserManagement loginRequest = new UserManagement();
		       
		        loginRequest.setId(newId);
		        loginRequest.setEmail(newEmail);
		        loginRequest.setPassword(newPwd);
		        TestContext.setUsersLoginRequest(loginRequest);
		        Response loginResponse = ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(TestContext.getUsersLoginRequest(), null, null), EndPoints.LOGIN);

		        LoginForAccessTokenResponse tokenResponse = loginResponse.as(LoginForAccessTokenResponse.class);
		        TestContext.setAuthToken(tokenResponse.getAccess_token());     
	
	}
	
	

}
