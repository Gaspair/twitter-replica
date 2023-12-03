package com.example.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Embeddable
public class PersonalInfo {




    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name = "married")
    private Boolean married;

    @Column(name="email")
    private String email;

    public PersonalInfo() {
    }

    public PersonalInfo(String firstName, String lastName, Boolean married, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.married = married;
        this.email = email;
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

    public Boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
