package com.myblog.blog1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"password"})
})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    private String name;
    private String username;
    private String email;
    private String password;
}
