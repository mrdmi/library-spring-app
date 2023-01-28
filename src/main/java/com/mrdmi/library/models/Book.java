package com.mrdmi.library.models;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
	private int id; 
	
	@NotEmpty(message = "Поле \"Название\" должно быть заполнено")
	@Size(max = 100, message = "Количество символов ограничено 100")
	private String title;
	
	@NotEmpty(message = "Поле \"Автор\" должно быть заполнено")
	@Size(max = 100, message = "Количество символов ограничено 100")
	private String author;
	
	@NotEmpty(message = "Поле \"Год\" должно быть заполнено")
	private String year;

	public Book() {
	}
	
	public Book(String title, String author, String year) {
		super();
		this.title = title;
		this.author = author;
		this.year = year;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, title, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && Objects.equals(title, other.title)
				&& Objects.equals(year, other.year);
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", year=" + year + "]";
	}
	
	
}
