package com.jezh.hibernate.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @OneToOne(/*fetch = FetchType.EAGER,*/
            cascade = CascadeType.ALL
          /*  {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
            *//*, CascadeType.REMOVE*//*}*/)
    @JoinColumn(name = "instructor_detail_id")
    private InstructorDetail instructorDetail;

//    BI-DIRECTIONAL here:
    // mapped by field (property) instructor in the Course class
//    (замаплен на поле instructor класса Course)
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "instructor",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST})
    private List<Course> courses;

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, String email, InstructorDetail instructorDetail,
                      List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.instructorDetail = instructorDetail;
        this.courses = courses;
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

    public void addCourse(Course course) {
        if (courses == null) courses = new ArrayList<>();
        courses.add(course);
//      todo: manifestation of bi-directional relationship:
        course.setInstructor(this);
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
        instructorDetail.setInstructor(this);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id = " + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", instructorDetail=" + /*instructorDetail +*/
                ", courses=" + /*courses +*/
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instructor that = (Instructor) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(instructorDetail, that.instructorDetail) &&
                Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, email, instructorDetail, courses);
    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Instructor that = (Instructor) o;
//        return
//                new EqualsBuilder()
//                        .appendSuper(super.equals(that))
//                        .append(firstName, that.firstName)
//                        .append(lastName, that.lastName)
//                        .append(email, that.email)
//                        .append(instructorDetail, that.instructorDetail)
//                        .isEquals();
//    }
//
//    @Override
//    public int hashCode() {
//
//        return new HashCodeBuilder()
//                .append(firstName)
//                .append(lastName)
//                .append(email)
//                .append(instructorDetail)
//                .toHashCode();
//    }
}
