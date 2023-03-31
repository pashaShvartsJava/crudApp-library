package ru.shvarts.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shvarts.springcourse.models.Book;
import ru.shvarts.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showAllBooks(){
        return jdbcTemplate.query("select Book.id_book, Book.name, Book.author, Book.year from Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> show(String name){
        return jdbcTemplate.query("select * from book where name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Book showBook(Integer id){
        return jdbcTemplate.query("select * from Book where id_book=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).
                stream().findAny().orElse(null);
    }

    public void createNewBook(Book book){
        jdbcTemplate.update("insert into book(name, author, year) values (?, ?, ?)",
               book.getName(), book.getAuthor(), book.getYear());
    }

    public void editBook(Integer id, Book book){
        jdbcTemplate.update("update Book set name=?, author=?, year=? where id_book=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void deleteBook(Integer id){
        jdbcTemplate.update("delete from book where id_book=?", id);
    }

    public boolean checkAvailability(Integer bookId){
        Book book = jdbcTemplate.query("select id_person from book where id_book=? ", new Object[]{bookId}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
        return book != null && book.getId_person() != null;
    }

    public void ridBook(Integer id){
        jdbcTemplate.update("update book set id_person=? where id_book=?", null, id);
    }

    public void setPerson(Integer id, Person person){
        jdbcTemplate.update("update book set id_person=? where id_book=?", person.getId_person(), id);
    }

    public List<Book> showBooksAccordingIdPerson(Integer id){
        return jdbcTemplate.query("select * from book where id_person=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

}
