package com.example.demo.contoller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service){
        this.service= service;
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") String id){
        return service.getUser(UUID.fromString(id));
    }
    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user){
        service.saveUser(user);

        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }
}
