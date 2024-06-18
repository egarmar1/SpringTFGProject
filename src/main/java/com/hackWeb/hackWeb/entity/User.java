package com.hackWeb.hackWeb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty
    @Column(unique = true,nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    private boolean is_active;
    @NotEmpty
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registrationDate;

    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user")
    private List<UserAttack> userAttacks;



    public User() {
    }

    public User(Integer id, String email, boolean is_active, String password, Date registrationDate, UserType userType, UserProfile userProfile, List<UserAttack> userAttacks) {
        this.id = id;
        this.email = email;
        this.is_active = is_active;
        this.password = password;
        this.registrationDate = registrationDate;
        this.userType = userType;
        this.userProfile = userProfile;
        this.userAttacks = userAttacks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return is_active;
    }

    public void setActive(boolean is_active) {
        this.is_active = is_active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public List<UserAttack> getUserAttacks() {
        return userAttacks;
    }

    public void setUserAttacks(List<UserAttack> userAttacks) {
        this.userAttacks = userAttacks;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", is_active=" + is_active +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", userType=" + userType +
                '}';
    }
}
