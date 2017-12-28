package com.jezh.hibernate.entity.oneToOneByDirectionalDifferentProbes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "bi_instructor")
public class InstructorBiDir {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "bi_first_name")
    private String firstName;

    @Column(name = "bi_last_name")
    private String lastName;

    @Column(name = "bi_email")
    private String email;

    @OneToOne(cascade = /*CascadeType.ALL*/
            {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
            /*CascadeType.REMOVE*/})
    @JoinColumn(name = "bi_instructor_detail_id")
    private InstructorDetailBiDir instructorDetailBiDir;

    public InstructorBiDir() {
    }

    public InstructorBiDir(String firstName, String lastName, String email, InstructorDetailBiDir instructorDetailBiDir) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.instructorDetailBiDir = instructorDetailBiDir;
    }

    public InstructorBiDir(String firstName, String lastName, String email) {
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

    public InstructorDetailBiDir getInstructorDetailBiDir() {
        return instructorDetailBiDir;
    }

    public void setInstructorDetailBiDir(InstructorDetailBiDir instructorDetailBiDir) {
        this.instructorDetailBiDir = instructorDetailBiDir;
    }

    @Override
    public String toString() {
        return "InstructorBiDir{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", instructorDetailBiDir=" /*+ instructorDetailBiDir*/ +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructorBiDir that = (InstructorBiDir) o;
        return
                new EqualsBuilder()
                        .appendSuper(super.equals(that))
                        .append(firstName, that.firstName)
                        .append(lastName, that.lastName)
                        .append(email, that.email)
                        .append(instructorDetailBiDir, that.instructorDetailBiDir)
                        .isEquals();
//                Objects.equals(firstName, that.firstName) &&
//                Objects.equals(lastName, that.lastName) &&
//                Objects.equals(email, that.email) &&
//                Objects.equals(instructorDetailBiDir, that.instructorDetailBiDir);
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder()
                .append(firstName)
                .append(lastName)
                .append(email)
                .append(instructorDetailBiDir)
                .toHashCode();
//                Objects.hash(firstName, lastName, email, instructorDetailBiDir);
    }
}
