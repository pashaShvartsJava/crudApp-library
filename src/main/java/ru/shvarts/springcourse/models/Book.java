package ru.shvarts.springcourse.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private Integer id_book;
    private Integer id_person;
    @NotEmpty(message = "Name should not be empty")
    @Size(min=1, max = 100, message = "Name should be between 1 and 100")
    private String name;
    @NotEmpty(message = "Name should not be empty")
    @Size(min=2, max = 30, message = "Name should be between 2 and 30")
    private String author;
    private Integer year;

    public Integer getId_person() {
        return id_person;
    }

    public void setId_person(Integer id_person) {
        this.id_person = id_person;
    }

    public Book(Integer id_book, Integer id_person, String name, String author, Integer year) {
        this.id_book = id_book;
        this.id_person = id_person;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }

    public Integer getId_book() {
        return id_book;
    }

    public void setId_book(Integer id_book) {
        this.id_book = id_book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
