package ru.kpfu.itis.bagautdinov.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @OneToOne
    private FileInfo video;

    @OneToOne(cascade = CascadeType.MERGE)
    private Task task;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Homework> homeworks;

    @OneToOne
    private Course course;

}
