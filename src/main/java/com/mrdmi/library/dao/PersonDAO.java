package com.mrdmi.library.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.mrdmi.library.models.Person;


@Component
public class PersonDAO {
private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public PersonDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public List<Person> index() {
		return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
	}
	
	public Optional<Person> show(String fullName) {
		return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?",
				new BeanPropertyRowMapper<>(Person.class), fullName)
				.stream().findAny();
	}
	
	public Person show(int id) {
		return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
				new BeanPropertyRowMapper<>(Person.class), id)
				.stream().findAny().orElse(null);
	}
	
	public void save(Person person) {
		jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birth) VALUES(?, ?)", person.getFullName(),
				person.getYearOfBirth());
	}
	
	public void update(int id, Person person) {
		jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birth=? WHERE id=?", 
				person.getFullName(), person.getYearOfBirth(), id);
	}
	
	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
	}
}
