package com.ms.user.health.controller;

import com.ms.user.health.dtos.HealthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RequestMapping("/health")
@RestController
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<HealthResponse> health(){
        HealthResponse response = new HealthResponse("OK",new Date(),"ms-user");
        return ResponseEntity.ok(response);
    }
}
