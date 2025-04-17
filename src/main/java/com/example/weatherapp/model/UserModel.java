package com.example.weatherapp.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "app_user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_username")
    private String username;

    @Column
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private UserProfile userProfile;

    //I used EAGER fetch type to avoid lazy loading issues
    //especially when using conversion between UserModel and UserDto
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<RequestHistory> requestHistories;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public UserModel setPassword(String password) {
        this.password = password;
        return null;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<RequestHistory> getRequestHistories() {
        return requestHistories;
    }

    public void setRequestHistories(Set<RequestHistory> requestHistories) {
        this.requestHistories = requestHistories;
    }

    public void addRequest(RequestHistory requestHistory) {
        this.requestHistories.add(requestHistory);
        requestHistory.setUser(this);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
