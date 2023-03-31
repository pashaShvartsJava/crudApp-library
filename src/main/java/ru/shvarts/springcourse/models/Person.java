package ru.shvarts.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private Integer id_person;
    @NotEmpty(message = "Name should not be empty")
    @Size(min=1, max = 100, message = "Name should be between 1 and 100")
    private String name;
    @Min(value = 1920, message = "Date of birth should be more than 1920")
    private Integer yearBirthday;

    public Person() {
    }

    public Person(Integer id_person, String name, Integer yearBirthday ) {
        this.id_person=id_person;
        this.name = name;
        this.yearBirthday = yearBirthday;
    }

    public Integer getId_person() {
        return id_person;
    }

    public void setId_person(Integer id) {
        this.id_person = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearBirthday() {
        return yearBirthday;
    }

    public void setYearBirthday(Integer yearBirthday) {
        this.yearBirthday = yearBirthday;
    }
}
