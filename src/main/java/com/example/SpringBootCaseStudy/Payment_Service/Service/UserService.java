package com.example.SpringBootCaseStudy.Payment_Service.Service;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import com.example.SpringBootCaseStudy.Payment_Service.Interface.UserInterface;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Service
public class UserService implements UserInterface {

    @Autowired
    UserRepo userRepo;

    @Override
    public User addUser(@RequestBody User user) {
        User u = new User();
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setMobile(user.getMobile());
        return userRepo.save(u);
    }

    @Override
    public User getUser(@RequestParam String email) {
        Optional<User> user = userRepo.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    public User updateUser(@RequestParam int id, @RequestParam String data) {
        User user = null;
        if(userRepo.findById(id).isPresent()) {
            user = userRepo.findById(id).get();

            if (data.contains("@")) {
                user.setEmail(data);
            } else {
                user.setMobile(data);
            }
            userRepo.save(user);
        }
        return user;
    }

    @Override
    public void deleteUser(@RequestParam int id) {
        if(userRepo.findById(id).isPresent()) {
            userRepo.deleteById(id);
        }
    }
}
