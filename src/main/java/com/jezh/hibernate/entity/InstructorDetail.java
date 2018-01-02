package com.jezh.hibernate.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "instructor_detail")
public class InstructorDetail {

//    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column/*(unique = true, updatable = false)*/
    private int id;

    @Column(name = "youtube_channel")
    private String youtubeChannel;

    @Column
    private String hobby;

    //    TO GET BI-DIRECTIONAL RELATIONSHIP:
    //    замаплен на поле instructorDetail класса Instructor
//    todo: NB: имя поля, а не имя таблицы
    @OneToOne(mappedBy = "instructorDetail", cascade = /*CascadeType.ALL*/ {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
//    @Column(name = "bi_instructorBiDir")
    private Instructor instructor;


    public InstructorDetail() {
    }

    public InstructorDetail(String youtubeChannel, String hobby) {
        this.youtubeChannel = youtubeChannel;
        this.hobby = hobby;
    }

    public int getId() {
        return id;
    }

    public String getYoutubeChannel() {
        return youtubeChannel;
    }

    public void setYoutubeChannel(String youtubeChannel) {
        this.youtubeChannel = youtubeChannel;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

// todo: если здесь вызываем instructor, получаем StackOverflowError - зацикливание вызовов. Т.е., либо здесь вызываем
// инструктора, но в инструкторе НЕ вызываем в toString детали, либо наоборот
    @Override
    public String toString() {
        return "InstructorDetail{" +
                "id=" + id +
                ", youtubeChannel='" + youtubeChannel + '\'' +
                ", hobby='" + hobby + '\'' +
                ", instructor=" + instructor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructorDetail that = (InstructorDetail) o;
        return Objects.equals(youtubeChannel, that.youtubeChannel) &&
                Objects.equals(hobby, that.hobby);
    }

    @Override
    public int hashCode() {

        return Objects.hash(youtubeChannel, hobby);
    }
}
