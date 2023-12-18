package com.example.demo.api.service;

import com.example.demo.dto.PersonalInfoDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.PersonalInfo;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.UserStore;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserStore store;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }



//
    @Test
    public void UserService_CreateUser_ReturnsUserDTO(){

        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Ned")
                .lastName("Stark").build();
        User user = User.builder()
                .handle("TheWinterIsComing")
                .personalInfo(personalInfo)
                .build();


        PersonalInfoDTO personalInfoDTO = PersonalInfoDTO
                .builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Ned").
                lastName("Stark").build();
        UserDTO userDTO = UserDTO.builder().handle("TheWinterIsComing").personalInfoDTO(personalInfoDTO).build();

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        ResponseEntity<?> savedUser = userService.saveUser(userDTO);



        Assertions.assertThat(savedUser.getBody()).isInstanceOf(UserDTO.class);
        UserDTO returnedUserDTO = (UserDTO) savedUser.getBody();
        Assertions.assertThat(returnedUserDTO.getHandle()).isEqualTo("TheWinterIsComing");
    }

}
