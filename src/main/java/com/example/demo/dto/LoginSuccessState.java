package com.example.demo.dto;

import lombok.Data;


@Data
public class LoginSuccessState {
    private boolean success;
    private Long userId;
    private String username;
}
