package com.example.SpringBootCaseStudy.Payment_Service.Repository;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    @Query("Select user from User user where email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
