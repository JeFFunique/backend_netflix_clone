package com.example.demo.service;

import com.example.demo.dto.LoginSuccessState;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("Username already taken");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email already taken");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public LoginSuccessState login(String email, String password) {
    User dbUser = userRepository.findByEmail(email);
    LoginSuccessState successResponse = new LoginSuccessState();
    if(dbUser != null && dbUser.getPassword().equals(password)){
        successResponse.setSuccess(true);
        successResponse.setUserId(dbUser.getId());
        successResponse.setUsername(dbUser.getUsername());
    }
    else{
        successResponse.setSuccess(false);
        successResponse.setUserId(null);
        successResponse.setUsername(null);
    }
    return successResponse;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}