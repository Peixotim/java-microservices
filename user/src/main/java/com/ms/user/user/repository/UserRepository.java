package com.ms.user.user.repository;

import com.ms.user.user.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
   boolean existsByEmail(String email);
}
