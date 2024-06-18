package com.hackWeb.hackWeb.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private User user;


    private String firstName;

    private String lastName;

    @Min(value = 0, message = "La edad no puede ser menor que 0")
    @Max(value = 120, message = "La edad no puede ser mayor que 120")
    private String age;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(nullable = true,length = 64)
    private String profilePhoto;

    public UserProfile() {
    }


    public UserProfile(int id, User user, String firstName, String lastName, Country country, String profilePhoto,String age) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.profilePhoto = profilePhoto;
        this.age = age;
    }

//    public UserProfile(User user) {
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    @Transient
    public String getPhotoImagePath(){
        if(profilePhoto == null) return null;
        return "/photos/student/" + id  + "/" + profilePhoto;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", user=" + user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country=" + country +
                '}';
    }
}
