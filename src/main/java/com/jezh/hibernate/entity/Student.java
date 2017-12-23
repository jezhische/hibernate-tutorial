package com.jezh.hibernate.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY = let MySql handle the generation AUTO_INCREMENT,
    // this is most common generation strategy for MySql
    @Column(name="id")
    private int id;

    @Column(name="first_Name")
    private String firstName;

        @Column(name="last_Name")
    private String lastName;

        @Column(name="email")
    private String email;

    public Student() {
    }

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Student student = (Student) o;
//        return Objects.equals(firstName, student.firstName) &&
//                Objects.equals(lastName, student.lastName) &&
//                Objects.equals(email, student.email);
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student)o;
        if (firstName != null ? !firstName.equals(student.firstName) : student.firstName != null) return false;
        if (lastName != null ? !lastName.equals(student.lastName) : student.lastName != null) return false;
        return email != null ? email.equals(student.email) : student.email == null;
    }

    @Override
    public int hashCode() {

//        return Objects.hash(firstName, lastName, email);
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return 31 * result + (email != null ? email.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
