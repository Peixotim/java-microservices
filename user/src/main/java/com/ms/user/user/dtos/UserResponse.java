package com.ms.user.user.dtos;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email) {
}
