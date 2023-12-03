package com.example.demo.dto;

public class PersonalInfoDTO {

    private String firstName;
    private String lastName;
    private Boolean married;
    private String email;


    public PersonalInfoDTO() {
    }

    public PersonalInfoDTO(String firstName, String lastName, boolean married, String email) {
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

