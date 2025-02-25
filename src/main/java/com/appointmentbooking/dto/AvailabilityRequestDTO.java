package com.appointmentbooking.dto;

import java.util.List;

public record AvailabilityRequestDTO(String date, List<String> products, String language, String rating) {}