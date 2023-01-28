package com.mrdmi.library.models;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {
	
	private int id;

	@NotEmpty(message = "Поле \"Фамилия Имя\" должно быть заполнено")
	@Size(max = 100, message = "Количество символов ограничено 100")
	@Pattern(regexp = "[A-Z][a-z]* [A-Z][a-z]*",
		message = "Поле должно соответствовать шаблону: \"Фамилия Имя\" ")
	private String fullName;
	
	@Min(value = 1900, message = "неверный год рождения")
	private int yearOfBirth;
	
	public Person() {
	}

	public Person(String fullName, int yearOfBirth) {
		super();
		this.fullName = fullName;
		this.yearOfBirth = yearOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	@Override
	public String toString() {
		return "Person [name=" + fullName + ", age=" + yearOfBirth + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(yearOfBirth, fullName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return yearOfBirth == other.yearOfBirth && Objects.equals(fullName, other.fullName);
	}
	
	
}
