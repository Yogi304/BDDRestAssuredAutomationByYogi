package com.api.bookstore.requestpojos;

import com.fasterxml.jackson.annotation.JsonInclude;

// This annotation tells the Jackson library to ignore any fields that are null
// when serializing this object to a JSON string.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookManagement {

	private int id;
	private String name;
	private String author;
	private int published_year;
	private String book_summary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPublished_year() {
		return published_year;
	}

	public void setPublished_year(int published_year) {
		this.published_year = published_year;
	}

	public String getBook_summary() {
		return book_summary;
	}

	public void setBook_summary(String book_summary) {
		this.book_summary = book_summary;
	}

}