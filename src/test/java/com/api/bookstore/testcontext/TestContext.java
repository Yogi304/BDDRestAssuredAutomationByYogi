package com.api.bookstore.testcontext;

import com.api.bookstore.requestpojos.BookManagement;
import com.api.bookstore.requestpojos.UserManagement;

import io.restassured.response.Response;

public class TestContext {
	  public static Response response;
	    public static UserManagement userSignupRequest;
	    public static UserManagement usersLoginRequest;
	    public static String authToken;
	    
	    public static BookManagement newBookRequest; 
	    public static int newBookId;  
	    
	    
	    public static Response getResponse() {
	        return response;
	    }
	    public static void setResponse(Response response) {
	        TestContext.response = response;
	    }
	    
	   
	    
	    public static int getNewBookId()
	    {
	    	return newBookId;
	    }
	    
	    public static void setNewBookId(int bookId)
	    {
	    	newBookId=bookId;
	    }
	    
	    public static String getAuthToken() {
	        return authToken;
	    }

	    public static void setAuthToken(String token) {
	        authToken = token;
	    }

	    public static UserManagement getUserSignupRequest() {
	        return userSignupRequest;
	    }

	    public static void setUserSignupRequest(UserManagement userRequest) {
	        userSignupRequest = userRequest;
	    }
	    
	    public static BookManagement getNewBookRequest() {
	        return newBookRequest;
	    }
	    
	    public static void setNewBookRequest(BookManagement bookRequest) {
	        TestContext.newBookRequest = bookRequest;
	    }
	    public static UserManagement getUsersLoginRequest() {
	        return usersLoginRequest;
	    }
	    public static void setUsersLoginRequest(UserManagement request) {
	        TestContext.usersLoginRequest = request;
	    }
	    
}
