package com.api.bookstore.requestBuilder;

import com.api.bookstore.assertions.WrappedAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    
    private static Response sendRequest( String method, String endpoint, RequestSpecification spec) {
        Response response = null;

        switch (method.toUpperCase()) {
        
            case "GET":
                response = RestAssured.given().spec(spec)
                        .when().log().all().get(endpoint)
                        .then().log().all()
                        .extract().response();
               
				 
				 break;

            case "POST":
                response = RestAssured.given().spec(spec)
                        .when().log().all().post(endpoint)
                        .then().log().all()
                        .extract().response();
			 
                break;

            case "PUT":
                response = RestAssured.given().spec(spec)
                        .when().log().all().put(endpoint)
                        .then().log().all()
                        .extract().response();
                
			 
                break;

            case "DELETE":
                response = RestAssured.given().spec(spec)
                        .when().log().all().delete(endpoint)
                        .then().log().all()
                        .extract().response();
			 
                break;

            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        return response;
    }


    public static Response get(RequestSpecification spec, String endpoint) {
        return sendRequest( "GET",endpoint, spec);
    }

    public static Response post(RequestSpecification spec, String endpoint) {
        return sendRequest( "POST",endpoint,spec);
    }

    public static Response put(RequestSpecification spec, String endpoint) {
        return sendRequest("PUT", endpoint,spec);
    }

    public static Response delete(RequestSpecification spec, String endpoint) {
        return sendRequest( "DELETE", endpoint, spec);
    }


    public static void validateResponseAgainstSchema(Response response,String endpoint, String schemaFileName) {
		   WrappedAssert.assertJsonSchema(response, "schemas/"+schemaFileName,
		            "Validating JSON schema for  " + endpoint);
		
	}
}
