package com.example.demo.api.repository;

import com.example.demo.model.PersonalInfo;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
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
    private UserRepository userRepo;

    @Test
    @DirtiesContext
    public void UserRepository_Save_ReturnSavedUser() {
        // Arrange
        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Ned")
                .lastName("Stark").build();
        User user = User.builder()
                .handle("TheWinterIsComing")
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

        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Ned")
                .lastName("Stark").build();
        User user = User.builder()
                .handle("TheWinterIsComing")
                .personalInfo(personalInfo)
                .build();

        // Act

        User savedUser1 = userRepo.save(user);

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
                .firstName("Ned")
                .lastName("Stark").build();
        User user = User.builder()
                .handle("TheWinterIsComing")
                .personalInfo(personalInfo)
                .build();

        PersonalInfo personalInfo1 = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Robert")
                .lastName("Baratheon").build();
        User user1 = User.builder()
                .handle("TheKing")
                .personalInfo(personalInfo1)
                .build();

        // Act
        User savedUser = userRepo.save(user);
        User savedUser1 = userRepo.save(user1);

        // Assertions
        List<User> userList = userRepo.findByHandleContaining("The");


        Assertions.assertThat(userList).isNotNull().hasSize(2);
    }
    @Test
    @DirtiesContext
    public void UserRepository_GetOneByHandle_ReturnMoreThanOneUser() {
//Arrange

        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Ned")
                .lastName("Stark").build();
        User user = User.builder()
                .handle("TheWinterIsComing")
                .personalInfo(personalInfo)
                .build();

        // Act

        User savedUser = userRepo.save(user);

        // Assertions
        Optional<User> optionalFoundUser = userRepo.findByHandle(savedUser.getHandle());

        User foundUser = optionalFoundUser.get();


        Assertions.assertThat(foundUser).isNotNull();

    }

    @Test

    public void shouldDeleteUserByHandle() {
        // Arrange
        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Ned")
                .lastName("Stark").build();
        User user = User.builder()
                .handle("TheWinterIsComing")
                .personalInfo(personalInfo)
                .build();

        // Act
        User savedUser = userRepo.save(user);

        // Assert
        Optional<User> optionalUser = userRepo.findByHandle(savedUser.getHandle());
        User retrievedUser = optionalUser.orElse(null);

        Assertions.assertThat(retrievedUser).isNotNull();

        // Act
        userRepo.delete(retrievedUser);

        // Assert
        Optional<User> deletedUser = userRepo.findByHandle(savedUser.getHandle());

        Assertions.assertThat(deletedUser).isEmpty();
    }



}
