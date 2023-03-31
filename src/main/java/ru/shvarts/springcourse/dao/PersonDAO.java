package ru.shvarts.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shvarts.springcourse.models.Book;
import ru.shvarts.springcourse.models.Person;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class PersonDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showAllPeople(){
        return jdbcTemplate.query("select * from Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String name){
        return jdbcTemplate.query("select * from person where name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Person showId(Integer id){
        return jdbcTemplate.query("select * from Person where id_person=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny().orElse(null);
    }

    public void createNewPerson(Person person){
        jdbcTemplate.update("insert into Person(name, yearBirthday) values(?, ?)", person.getName(), person.getYearBirthday());
    }

    public void editPerson(Integer id, Person updatedPerson){
        jdbcTemplate.update("update person set name=?, yearBirthday=? where id_person=?",
                updatedPerson.getName(), updatedPerson.getYearBirthday(), id);
    }

    public void deletePerson(Integer id){
        jdbcTemplate.update("delete from person where id_person=?", id);
    }

    public boolean checkAvailabilityOfBooks(Integer id){
        Person person = jdbcTemplate.query("select id_person from book where id_person=? ", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
        return person != null && person.getId_person() != null;
    }
}
