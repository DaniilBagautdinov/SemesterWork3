package ru.kpfu.itis.bagautdinov.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String username;

    @Column
    private String passwordHash;

    @Column
    private String registrationDate;

    @OneToOne
    private FileInfo avatar;

    @ManyToMany(mappedBy = "subscribers", cascade = CascadeType.MERGE)
    private List<Course> courses;
}
