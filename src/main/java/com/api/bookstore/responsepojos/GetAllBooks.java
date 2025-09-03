package com.api.bookstore.responsepojos;

import java.util.List;

public class GetAllBooks {
	private List<GetBook> books;

	public List<GetBook> getBooks() {
		return books;
	}

	public void setBooks(List<GetBook> books) {
		this.books = books;
	}


}
