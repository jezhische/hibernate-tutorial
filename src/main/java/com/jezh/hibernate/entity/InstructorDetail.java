package com.jezh.hibernate.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Override
    public String toString() {
        return "InstructorDetail{" +
                "id=" + id +
                ", youtubeChannel='" + youtubeChannel + '\'' +
                ", hobby='" + hobby + '\'' +
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
