package com.example.weatherapp.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;

    @OneToOne
    private UserProfile userProfile;


    @OneToMany(mappedBy = "user")
    private Set<RequestHistory> requestHistories;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userProfile=" + userProfile +
                ", requestHistories=" + requestHistories +
                '}';
    }
}
