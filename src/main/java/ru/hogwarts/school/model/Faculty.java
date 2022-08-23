package ru.hogwarts.school.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
public class Faculty {

    @Id
    @GeneratedValue
    private int id;

    @Pattern(regexp = "^[A-z]+$")
    private String name;

    @Pattern(regexp = "^[A-z]+$")
    private String colour;

    public Faculty(int id, String name, String colour) {
        this.id = id;
        this.name = name;
        this.colour = colour;
    }

    public Faculty() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getColour() {
        return colour;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id && name.equals(faculty.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}