package com.ms.email.health.dtos;

import java.util.Date;

public record HealthResponse(String status, Date timestamp, String name) {
}

