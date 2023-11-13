package com.example.demo.contoller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOneByUUID(@PathVariable("id") String id) {
        try {
            UUID userId = UUID.fromString(id);
            User user = service.getUserById(userId).getBody();
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //    WORKS
    @GetMapping("/profile/{handle}")
    public ResponseEntity<User> getOneByHandle(@PathVariable("handle") String handle) {
        User user = service.getOneUserByHandle(handle);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{handle}")
    public ResponseEntity<List<User>> getUsersByHandler(@PathVariable("handle") String handle) {
        List<User> users = service.getUsersByHandle(handle);
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        Optional<User> optionalUser = Optional.ofNullable(service.getOneUserByHandle(user.getHandle()));

        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User handle already in use");
        }
        try {
            service.saveUser(user);
            return new ResponseEntity<>("User created", HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        try {
            UUID userId = UUID.fromString(id);
            User existingUser = service.getUserById(userId).getBody();
            if (existingUser != null) {
                service.deleteUser(existingUser);
                return new ResponseEntity<>("User deleted", HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UUID");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }
}
