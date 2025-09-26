package com.example.SpringBootCaseStudy.Payment_Service.Controller;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import com.example.SpringBootCaseStudy.Payment_Service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User u = userService.addUser(user);
        return ResponseEntity.ok(u);
    }

    @GetMapping("/get")
    public boolean getUserRecord(@RequestParam String email){
        User user = userService.getUser(email);
        if(user.getEmail().equals(email)){
            return true;
        } else {
            return false;
        }
    }

    @PutMapping("/update")
    public User updateUserData(@RequestParam int id, @RequestParam String data) {
        return userService.updateUser(id, data);
    }

    @DeleteMapping("/delete")
    public void deleteUserRecord(@RequestParam int id){
        userService.deleteUser(id);
    }
}
