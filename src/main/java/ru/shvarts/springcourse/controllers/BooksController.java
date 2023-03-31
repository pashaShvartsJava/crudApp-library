package ru.shvarts.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shvarts.springcourse.dao.BookDAO;
import ru.shvarts.springcourse.dao.PersonDAO;
import ru.shvarts.springcourse.models.Book;
import ru.shvarts.springcourse.models.Person;
import ru.shvarts.springcourse.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;

    private final PersonDAO personDAO;

    private final BookValidator bookValidator;

    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String showBooks(Model model){
        model.addAttribute("books", bookDAO.showAllBooks());
        return "books/allbooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("human") Person person){
        model.addAttribute("book", bookDAO.showBook(id));
        model.addAttribute("person", personDAO.showId(bookDAO.showBook(id).getId_person()));
        model.addAttribute("people", personDAO.showAllPeople());
        model.addAttribute("checkAvailability", bookDAO.checkAvailability(id));
        return "books/showBook";
    }

    @GetMapping("/newBook")
    public String showFormOfCreatingNewBook(Model model){
        model.addAttribute("book", new Book());
        return "books/addNewBook";
    }

    @PostMapping
    public String createNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()) return "books/addNewBook";
        bookDAO.createNewBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showFormOfBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.showBook(id));
        return "books/updatedBook";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id, @ModelAttribute("book") Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/updatedBook";
        bookDAO.editBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/setPerson/{id}")
    public String setPerson(@PathVariable("id") int id, @ModelAttribute("human") Person person){
        bookDAO.setPerson(id, person);
        return "redirect:/books";
    }

    @PatchMapping("/ridBook/{id}")
    public String ridBook(@PathVariable("id") int id){
        bookDAO.ridBook(id);
        return "redirect:/books";
    }

}
