package com.api.bookstore.requestpojos;

import com.fasterxml.jackson.annotation.JsonInclude;

// This annotation tells the Jackson library (used by REST Assured) to ignore any fields
// that are null when converting this object into a JSON string. This is useful for
// creating request bodies where you might not need to send all fields.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserManagement {

	private int id;
	private String email;
	private String password;
	private String token;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

