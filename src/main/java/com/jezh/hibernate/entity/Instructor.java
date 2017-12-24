package com.jezh.hibernate.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    @OneToOne(cascade =
            {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.REMOVE})
    @JoinColumn(name = "instructor_detail_id")
    private InstructorDetail instructorDetail;

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, String email, InstructorDetail instructorDetail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.instructorDetail = instructorDetail;
    }

    public Instructor(String firstName, String lastName, String email) {
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

    public InstructorDetail getInstructorDetail() {
        return instructorDetail;
    }

    public void setInstructorDetail(InstructorDetail instructorDetail) {
        this.instructorDetail = instructorDetail;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", instructorDetail=" + instructorDetail +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instructor that = (Instructor) o;
        return
                new EqualsBuilder()
                        .appendSuper(super.equals(that))
                        .append(firstName, that.firstName)
                        .append(lastName, that.lastName)
                        .append(email, that.email)
                        .append(instructorDetail, that.instructorDetail)
                        .isEquals();
//                Objects.equals(firstName, that.firstName) &&
//                Objects.equals(lastName, that.lastName) &&
//                Objects.equals(email, that.email) &&
//                Objects.equals(instructorDetail, that.instructorDetail);
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder()
                .append(firstName)
                .append(lastName)
                .append(email)
                .append(instructorDetail)
                .toHashCode();
//                Objects.hash(firstName, lastName, email, instructorDetail);
    }
}
