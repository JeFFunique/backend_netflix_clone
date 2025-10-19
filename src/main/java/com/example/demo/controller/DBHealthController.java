package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/api/health")
public class DBHealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db")
    public ResponseEntity<String> checkDatabase() {
        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(2)) {
                return ResponseEntity.ok("Database is awake");
            } else {
                return ResponseEntity.status(503).body("Database connection not valid");
            }
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Database is asleep or unreachable");
        }
    }
}
