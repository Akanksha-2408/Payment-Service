package com.example.SpringBootCaseStudy.Payment_Service.Controller;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import com.example.SpringBootCaseStudy.Payment_Service.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Tag(name = "User Management", description = "CRUD operations for user accounts.") // ⬅️ ADD @Tag
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Create a new user",
            description = "Adds a new User entity to the database."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully created and returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided.", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User u = userService.addUser(user);
        return ResponseEntity.ok(u);
    }

    @Operation(
            summary = "Retrieve user record by email",
            description = "Checks if a user exists based on their email address."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns true if user is found, false otherwise.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))
            )
    })
    @GetMapping("/get")
    public boolean getUserRecord(@RequestParam String email){
        User user = userService.getUser(email);
        if(user.getEmail().equals(email)){
            return true;
        } else {
            return false;
        }
    }

    @Operation(
            summary = "Update user data",
            description = "Updates specific data for an existing user based on ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User data successfully updated and returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found with the given ID.", content = @Content)
    })
    @PutMapping("/update")
    public User updateUserData(@RequestParam int id, @RequestParam String data) {
        return userService.updateUser(id, data);
    }

    @Operation(
            summary = "Delete user record",
            description = "Deletes a user record permanently by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully deleted.", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found with the given ID.", content = @Content)
    })
    @DeleteMapping("/delete")
    public void deleteUserRecord(@RequestParam int id){
        userService.deleteUser(id);
    }
}
