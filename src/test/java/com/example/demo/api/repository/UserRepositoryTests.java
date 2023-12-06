package com.example.demo.api.repository;


import com.example.demo.model.PersonalInfo;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepo userRepo;


    @Test
    public void UserRepository_Save_ReturnSavedUser() {

//        Arrange
        PersonalInfo personalInfo = PersonalInfo
                .builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Cherescu")
                .lastName("Parosu").build();
        User user = User
                .builder()
                .handle("NewUser")
                .personalInfo(personalInfo)
                .build();

//Act
        User savedUser = userRepo.save(user);

//        Assertions

        Assertions.assertThat(savedUser).isNotNull();

    }
}
