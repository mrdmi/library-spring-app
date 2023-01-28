package com.mrdmi.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrdmi.library.dao.BookDAO;
import com.mrdmi.library.models.Book;

@Component
public class BookValidator {
	
	private final BookDAO bookDAO;
	
	@Autowired
	public BookValidator(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	public String validateBook(Book book) {
		String message = "";
		if (bookDAO.show(book.getTitle(), book.getAuthor()).isPresent()) {
			message = "Книга с этим названием такого автора уже существует";
		}
		return message;
	}

}
