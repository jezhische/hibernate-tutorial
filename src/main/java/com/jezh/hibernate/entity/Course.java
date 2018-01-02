package com.jezh.hibernate.entity;

import com.jezh.hibernate.entity.oneToOneByDirectionalDifferentProbes.Review;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String title;

    //  BI-DIRECTIONAL
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST})
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    //    ЗДЕСЬ СВЯЗЬ UNI-DIRECTIONAL, поэтому все наоборот:
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST})
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    public Course() {
    }

    public Course(String title/*, Instructor instructor*/) {
        this.title = title;
// cannot set instructor because of this is property assigned from outside
//        this.instructor = instructor;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        if (reviews == null) reviews = new ArrayList<>();
        reviews.add(review);
//        review.setCourse(this);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", instructor = " + instructor +
                ", reviews.id = " + getReviewsId(reviews) +
                '}';
    }

    private String getReviewsId(List<Review> reviews) {
        StringBuilder builder = new StringBuilder();
        if (reviews != null) {
            reviews.forEach(review -> builder.append(review.getId() + ", "));
            return builder.toString();
        } else return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(title, course.title) &&
                Objects.equals(instructor, course.instructor);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, instructor);
    }
}
