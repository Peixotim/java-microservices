package com.ms.email.Health.controller;

import com.ms.email.Health.dtos.HealthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/health")
@RestController
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<HealthResponse> health(){
        HealthResponse response = new HealthResponse("OK",new Date(),"ms-user");
        return ResponseEntity.ok(response);
    }
}
