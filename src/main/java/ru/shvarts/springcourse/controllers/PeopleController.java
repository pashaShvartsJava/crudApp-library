package ru.shvarts.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shvarts.springcourse.dao.PersonDAO;
import ru.shvarts.springcourse.models.Person;
import ru.shvarts.springcourse.dao.BookDAO;
import ru.shvarts.springcourse.util.PersonValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    private final PersonValidator personValidator;

    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String showPeople(Model model){
        model.addAttribute("people", personDAO.showAllPeople());
        return "people/allpeople";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.showId(id));
        model.addAttribute("checkAvailabilityOfBooks", personDAO.checkAvailabilityOfBooks(id));

        model.addAttribute("booksAccordingIdPerson", bookDAO.showBooksAccordingIdPerson(id));
        return "people/showPerson";
    }

    @GetMapping("/newPerson")
    public String showFormOfNewPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/addNew";
    }

    @PostMapping()
    public String createNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/addNew";
        personDAO.createNewPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String updatePage(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.showId(id));
        return "people/updatedPerson";
    }

    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/updatedPerson";
        personDAO.editPerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/people";
    }
}
