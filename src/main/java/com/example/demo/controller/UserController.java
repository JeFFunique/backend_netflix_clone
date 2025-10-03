package com.example.demo.controller;
import com.example.demo.dto.APIResponseUser;
import com.example.demo.dto.LoginSuccessState;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponseUser> createUser(@RequestBody User user) {
    try{
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(new APIResponseUser(true, "New account successfully created"));
    }
    catch(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponseUser(false, e.getMessage()));
    }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginSuccessState> login(@RequestBody UserLoginRequest loginRequest) {
        LoginSuccessState successResponse = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(successResponse);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}