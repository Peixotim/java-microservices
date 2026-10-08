package com.ms.user.Health.dtos;

import java.util.Date;

public record HealthResponse(String status, Date timestamp,String name) {
}
