package com.jezh.hibernate.entity.oneToOneByDirectionalDifferentProbes;

import com.jezh.hibernate.entity.Course;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String comment;

//    uni-directional связь - поле не мапится на reviews в Course, тем более, что там List<Review>
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
//    @Column(name = "course_id")
    private Course course;

    public Review() {
    }

    public Review(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", course=" + course +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(comment, review.comment) &&
                Objects.equals(course, review.course);
    }

    @Override
    public int hashCode() {

        return Objects.hash(comment, course);
    }
}
