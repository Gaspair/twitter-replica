package com.example.demo.api.repository;

import com.example.demo.model.PersonalInfo;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Autowired
    private UserRepo userRepo;

    @Test
    @DirtiesContext
    public void UserRepository_Save_ReturnSavedUser() {
        // Arrange
        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Cherescu")
                .lastName("Parosu").build();
        User user = User.builder()
                .handle("NewUser")
                .personalInfo(personalInfo)
                .build();

        // Act
        User savedUser = userRepo.save(user);

        // Assertions
        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    @DirtiesContext
    public void UserRepository_GetOneByID_ReturnMoreThanOneUser() {
//Arrange

        PersonalInfo personalInfo1 = PersonalInfo.builder()
                .email("Testing1@yahoo.com")
                .married(true)
                .firstName("Cherescu1")
                .lastName("Parosu1").build();
        User user1 = User.builder()
                .handle("NewUser1")
                .personalInfo(personalInfo1)
                .build();

        // Act

        User savedUser1 = userRepo.save(user1);

        // Assertions
        Optional<User> optionalUser = userRepo.findById(savedUser1.getUserID());

        User savedUser = optionalUser.get();


        Assertions.assertThat(savedUser).isNotNull();

    }


    @Test
    public void UserRepository_GetAllByHandleContains_ReturnMoreThanOneUser() {
        // Arrange
        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Cherescu")
                .lastName("Parosu").build();
        User user = User.builder()
                .handle("NewUser2")
                .personalInfo(personalInfo)
                .build();

        PersonalInfo personalInfo1 = PersonalInfo.builder()
                .email("Testing1@yahoo.com")
                .married(true)
                .firstName("Cherescu1")
                .lastName("Parosu1").build();
        User user1 = User.builder()
                .handle("NewUser3")
                .personalInfo(personalInfo1)
                .build();

        // Act
        User savedUser = userRepo.save(user);
        User savedUser1 = userRepo.save(user1);

        // Assertions
        List<User> userList = userRepo.findByHandleContaining("New");


        Assertions.assertThat(userList).isNotNull().hasSize(2);
    }
}
