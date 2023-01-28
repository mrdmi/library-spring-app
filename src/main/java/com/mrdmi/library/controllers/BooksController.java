package com.mrdmi.library.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrdmi.library.dao.BookDAO;
import com.mrdmi.library.dao.PersonDAO;
import com.mrdmi.library.models.Book;
import com.mrdmi.library.models.Person;
import com.mrdmi.library.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {
	
	private final PersonDAO personDAO;
	private final BookDAO bookDAO;
	private final BookValidator bookValidator;
	
	public BooksController(PersonDAO personDAO, BookDAO bookDAO, BookValidator bookValidator) {
		super();
		this.personDAO = personDAO;
		this.bookDAO = bookDAO;
		this.bookValidator = bookValidator;
	}
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("books", bookDAO.index());
		return "books/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model, @ModelAttribute("transferPerson") Person transferPerson) {
		Optional<Book> bookOptional = Optional.ofNullable( bookDAO.show(id));
		if (bookOptional.isPresent()) {	
			Optional<Integer> optionalPersonId = bookDAO.getPersonId(id);
			if (optionalPersonId.isPresent()) {
				model.addAttribute("ownerPerson", personDAO.show(optionalPersonId.get()));
			} else {				
				model.addAttribute("people", personDAO.index());
			} 
			model.addAttribute("book", bookOptional.get());
			return "books/show";
		}
		
		
		return "books/not-found";
	}
	
	@GetMapping("/new")
	public String newBook(@ModelAttribute("book") Book book) {
		return "books/new";
	}
	
	@PostMapping
	public String create(@ModelAttribute("book") @Valid Book book, 
			BindingResult bindingResult) {
		
		String errMessage = bookValidator.validateBook(book);
		if (!errMessage.isEmpty()) {
			ObjectError error = new ObjectError("globalError", errMessage);
			bindingResult.addError(error);
		}
		
		if (bindingResult.hasErrors()) {
			return "books/new";
		}
		bookDAO.save(book);
		return "redirect:/books";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		Optional<Book> bookOptional = Optional.ofNullable( bookDAO.show(id));
		if (bookOptional.isPresent()) {
			model.addAttribute("book", bookOptional.get());
			return "books/edit";
		}
		
		return "books/not-found";
	}
	
	@PatchMapping("/{id}")
	public String update(@ModelAttribute("book") @Valid Book book,
			BindingResult bindingResult, @PathVariable("id") int id) {
		
		if (!bookDAO.show(id).getTitle().equals(book.getTitle()) || 
				!bookDAO.show(id).getAuthor().equals(book.getAuthor())) {
			String errMessage = bookValidator.validateBook(book);
			if (!errMessage.isEmpty()) {
				ObjectError error = new ObjectError("globalError", errMessage);
				bindingResult.addError(error);
			}			
		}
		
		
		if (bindingResult.hasErrors()) {
			return "books/edit";
		}
		
		bookDAO.update(id, book);
		return "redirect:/books";
	}
	
	@PatchMapping("/assign-owner/{id}")
	public String assignOwner(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
		Optional<Person> personOptional = Optional.ofNullable(person);
		if(personOptional.isPresent()) {
			bookDAO.setBookOwner(id, person.getId());
		}
		return "redirect:/books/" + id;
	}
	
	@PatchMapping("/remove-owner/{id}")
	public String removeOwner(@PathVariable("id") int id) {
		bookDAO.removeBookOwner(id);
		return "redirect:/books/" + id;

	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		bookDAO.delete(id);
		return "redirect:/books";
	}
}
