package com.ms.user.user.controller;

import com.ms.user.user.dtos.UserCreateRequest;
import com.ms.user.user.dtos.UserResponse;
import com.ms.user.user.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> list(){
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse>getById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }


    @PostMapping("/")
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest request){
        UserResponse user = service.create(request);
        return ResponseEntity.created(URI.create("/users/" + user.id())).body(user);
    }
}
