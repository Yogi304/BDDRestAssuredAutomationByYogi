package com.api.bookstore.requestBuilder;

import java.util.Map;

import com.api.bookstore.utils.EnvConfigResolver;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestBuilder {
	
    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
        		 .setBaseUri(EnvConfigResolver.getUrl())
                .build();
    }

    public static RequestSpecification withAuthToken(String token, Map<String, Object> queryParams, Map<String, Object> pathParams) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(com.api.bookstore.utils.EnvConfigResolver.getUrl())
                .addHeader("Authorization", "Bearer " + token);

        if (queryParams != null && !queryParams.isEmpty()) {
            builder.addQueryParams(queryParams);
        }
        
        if (pathParams != null && !pathParams.isEmpty()) {
            builder.addPathParams(pathParams);
        }

        return builder.build();
    }
    

    public static RequestSpecification withBodyAndNoAuthToken(Object body, Map<String, Object> queryParams, Map<String, Object> pathParams) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(EnvConfigResolver.getUrl())
                .setContentType(ContentType.JSON)
                .setBody(body);

        if (queryParams != null && !queryParams.isEmpty()) {
            builder.addQueryParams(queryParams);
        }
        
        if (pathParams != null && !pathParams.isEmpty()) {
            builder.addPathParams(pathParams);
        }

        return builder.build();
    }
    
    public static RequestSpecification withBodyAndAuthToken(Object body, String token, Map<String, Object> queryParams, Map<String, Object> pathParams) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(EnvConfigResolver.getUrl())
                .setContentType(ContentType.JSON)
                .setBody(body)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Accept", "application/json");

        if (queryParams != null && !queryParams.isEmpty()) {
            builder.addQueryParams(queryParams);
        }
        
        if (pathParams != null && !pathParams.isEmpty()) {
            builder.addPathParams(pathParams);
        }

        return builder.build();
    }

    
  

}
