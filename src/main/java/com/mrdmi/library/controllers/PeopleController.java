package com.mrdmi.library.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrdmi.library.dao.BookDAO;
import com.mrdmi.library.dao.PersonDAO;
import com.mrdmi.library.models.Person;
import com.mrdmi.library.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {
	
	private final PersonDAO personDAO;
	private final BookDAO bookDAO;
	private final PersonValidator personValidator;
	
	@Autowired
	public PeopleController(PersonDAO personDAO, PersonValidator personValidator, BookDAO bookDAO) {
		super();
		this.personDAO = personDAO;
		this.bookDAO = bookDAO;
		this.personValidator = personValidator;
	}
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("people", personDAO.index());
		return "people/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model) {
		Optional<Person> personOptional = Optional.ofNullable( personDAO.show(id));
		if (personOptional.isPresent()) {			
			model.addAttribute("person", personOptional.get());
			model.addAttribute("books", bookDAO.index(id));
			return "people/show";
		}
		
		
		return "people/not-found";
	}
	
	@GetMapping("/new")
	public String newPerson(@ModelAttribute("person") Person person) {
		return "people/new";
	}
	
	@PostMapping
	public String create(@ModelAttribute("person") @Valid Person person, 
			BindingResult bindingResult) {
		
		personValidator.validate(person, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "people/new";
		}
		personDAO.save(person);
		return "redirect:/people";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		Optional<Person> personOptional = Optional.ofNullable( personDAO.show(id));
		if (personOptional.isPresent()) {
			model.addAttribute("person", personOptional.get());
			return "people/edit";
		}
		
		return "people/not-found";
	}
	
	@PatchMapping("/{id}")
	public String update(@ModelAttribute("person") @Valid Person person,
			BindingResult bindingResult, @PathVariable("id") int id) {

		if (!personDAO.show(id).getFullName().equals(person.getFullName())) {			
			personValidator.validate(person, bindingResult);
		}
		
		if (bindingResult.hasErrors()) {
			return "people/edit";
		}
		
		personDAO.update(id, person);
		return "redirect:/people";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		personDAO.delete(id);
		return "redirect:/people";
	}
}
