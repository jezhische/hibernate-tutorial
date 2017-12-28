package com.jezh.hibernate.entity.oneToOneByDirectionalDifferentProbes;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bi_instructor_detail")
public class InstructorDetailBiDir {

//    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column/*(unique = true, updatable = false)*/
    private int id;

    @Column(name = "bi_youtube_channel")
    private String youtubeChannel;

    @Column(name = "bi_hobby")
    private String hobby;

//    TO GET BI-DIRECTIONAL RELATIONSHIP:
//    todo: NB: имя поля, а не имя таблицы
    @OneToOne(mappedBy = "instructorDetailBiDir", cascade = /*CascadeType.ALL*/ {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
//    @Column(name = "bi_instructorBiDir")
    private InstructorBiDir instructorBiDir;

    public InstructorDetailBiDir() {
    }


    public InstructorDetailBiDir(String youtubeChannel, String hobby) {
        this.youtubeChannel = youtubeChannel;
        this.hobby = hobby;
    }

    public InstructorDetailBiDir(String youtubeChannel, String hobby, InstructorBiDir instructorBiDir) {
        this.youtubeChannel = youtubeChannel;
        this.hobby = hobby;
        this.instructorBiDir = instructorBiDir;
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

    public InstructorBiDir getInstructorBiDir() {
        return instructorBiDir;
    }

    public void setInstructorBiDir(InstructorBiDir instructorBiDir) {
        this.instructorBiDir = instructorBiDir;
    }

    @Override
    public String toString() {
        return "InstructorDetailBiDir{" +
                "id=" + id +
                ", youtubeChannel='" + youtubeChannel + '\'' +
                ", hobby='" + hobby + '\'' +
                ", instructorBiDir=" /*+ instructorBiDir*/ +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructorDetailBiDir that = (InstructorDetailBiDir) o;
        return Objects.equals(youtubeChannel, that.youtubeChannel) &&
                Objects.equals(hobby, that.hobby);
    }

    @Override
    public int hashCode() {

        return Objects.hash(youtubeChannel, hobby);
    }
}
