package ru.digitalleague.TestSpringSecurityApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String login;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
