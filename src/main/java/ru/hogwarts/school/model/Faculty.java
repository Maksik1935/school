package ru.hogwarts.school.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.Set;

@Entity
public class Faculty {

    @Id
    @GeneratedValue
    private int id;

    @Pattern(regexp = "^[A-z]+$")
    private String name;

    @Pattern(regexp = "^[A-z]+$")
    private String colour;

    @OneToMany(mappedBy = "faculty")
    private Set<Student> students;

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

    public Set<Student> getStudents() {
        return students;
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