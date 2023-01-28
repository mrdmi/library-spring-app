package com.mrdmi.library.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.mrdmi.library.models.Book;

@Component
public class BookDAO {
private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public BookDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public List<Book> index() {
		return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
	}
	
	public List<Book> index(int person_id) {
		return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?",
				new BeanPropertyRowMapper<>(Book.class), person_id);
	}
	
	public void setBookOwner(int bookId, int personId) {
		jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", 
				personId, bookId);
	}
	
	public void removeBookOwner(int bookId) {
		jdbcTemplate.update("UPDATE Book SET person_id=null WHERE id=?", bookId);
	}
	
	public Optional<Integer> getPersonId(int id) {
		return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT person_id FROM Book WHERE id=?", Integer.class, id));
	}
	
	public Optional<Book> show(String title, String author) {
		return jdbcTemplate.query("SELECT * FROM Book WHERE title=? AND author=?",
				new BeanPropertyRowMapper<>(Book.class), title, author)
				.stream().findAny();
	}
	
	public Book show(int id) {
		return jdbcTemplate.query("SELECT * FROM Book WHERE id=?",
				new BeanPropertyRowMapper<>(Book.class), id)
				.stream().findAny().orElse(null);
	}
	
	public void save(Book book) {
		jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES(?, ?, ?)", book.getTitle(),
				book.getAuthor(), book.getYear());
	}
	
	public void update(int id, Book book) {
		jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE id=?", 
				book.getTitle(), book.getAuthor(), book.getYear(), id);
	}
	
	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
	}
}
