package com.ms.user.user.services;

import com.ms.user.core.exceptions.ApiException;
import com.ms.user.producers.UserProducer;
import com.ms.user.user.dtos.UserCreateRequest;
import com.ms.user.user.dtos.UserResponse;
import com.ms.user.user.models.UserModel;
import com.ms.user.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserProducer userProducer;

    public UserService(UserRepository repository,UserProducer userProducer){
        this.repository = repository;
        this.userProducer = userProducer;
    }


    public UserResponse findById(UUID id){

        Optional<UserModel> user = repository.findById(id);
        if(user.isEmpty()){
            throw ApiException.notFound("User Not Found");
        }

        return toResponse(user.get());
    }


    public List<UserResponse> list(){
        return repository.findAll().stream().map(this::toResponse).toList();
    }


    @Transactional
    public UserResponse create(UserCreateRequest request){
        String email = request.email().trim().toLowerCase(Locale.ROOT);

        if(repository.existsByEmail(email)){
            throw ApiException.conflict("A user with this email already exists");
        }

        try {
            UserModel user = repository.saveAndFlush(new UserModel(request.name().trim(), email));
            userProducer.publishMessageEmail(user);
            return toResponse(user);
        } catch (DataIntegrityViolationException error) {
            throw ApiException.conflict("A user with this email already exists");
        }
    }


    private UserResponse toResponse(UserModel user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
