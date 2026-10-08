package com.ms.email.Health.dtos;

import java.util.Date;

public record HealthResponse(String status, Date timestamp, String name) {
}

