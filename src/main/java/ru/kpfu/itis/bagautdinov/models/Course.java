package ru.kpfu.itis.bagautdinov.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;

    @OneToOne
    private User creator;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "subscribers_id")
    private List<User> subscribers;

}
