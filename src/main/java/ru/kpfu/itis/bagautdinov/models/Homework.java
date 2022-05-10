package ru.kpfu.itis.bagautdinov.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<FileInfo> photos;

    @Column
    private String answer;

    @OneToOne
    private User student;

    @Column
    private Integer score;

    @OneToOne
    private Lesson lesson;

    @Enumerated(value = EnumType.STRING)
    private Status status;


    public enum Status {
        CHECKED,
        UNCHECKED
    }

}
