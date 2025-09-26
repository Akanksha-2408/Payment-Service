package com.example.SpringBootCaseStudy.Payment_Service.Interface;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface UserInterface {
    public User addUser(@RequestBody User user);
    public User getUser(@RequestParam String email);
    public User updateUser(@RequestParam int id, @RequestParam String data);
    public void deleteUser(@RequestParam int id);
}
