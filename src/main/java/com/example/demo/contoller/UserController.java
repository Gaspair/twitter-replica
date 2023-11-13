package com.example.demo.contoller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public User getOneByHandle(@PathVariable("handle") String handle){
        return service.getOneUserByHandle(handle);
    }

    @GetMapping("/search/{handle}")
    public List<User> getUsersByHandler(@PathVariable("handle") String handle){
        return service.getUsersByHandle(handle);
    }


    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user){
        service.saveUser(user);

        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }



    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id){
        service.deleteUser(service.getUserById(UUID.fromString(id)).getBody());

        return  new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
