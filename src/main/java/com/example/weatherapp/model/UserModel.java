package com.example.weatherapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

@Entity
@Table(name = "app_user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "user_username")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column
    @NotBlank(message = "Password is mandatory")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
            message = "Password must be at least 8 characters, include uppercase, lowercase, and a number"
    )
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private UserProfileModel userProfileModel;

    //I used EAGER fetch type to avoid lazy loading issues
    //especially when using conversion between UserModel and UserDto
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<RequestHistoryModel> requestHistories;

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

    public UserProfileModel getUserProfile() {
        return userProfileModel;
    }

    public void setUserProfile(UserProfileModel userProfileModel) {
        this.userProfileModel = userProfileModel;
    }

    public Set<RequestHistoryModel> getRequestHistories() {
        return requestHistories;
    }

    public void setRequestHistories(Set<RequestHistoryModel> requestHistories) {
        this.requestHistories = requestHistories;
    }

    public void addRequest(RequestHistoryModel requestHistoryModel) {
        this.requestHistories.add(requestHistoryModel);
        requestHistoryModel.setUser(this);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
