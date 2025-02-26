package com.appointmentbooking.controller;

import com.appointmentbooking.dto.AvailabilityRequestDTO;
import com.appointmentbooking.dto.AvailabilityResponseDTO;
import com.appointmentbooking.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.appointmentbooking.util.AppConstants.CALENDAR_ENDPOINT;
import static com.appointmentbooking.util.AppConstants.QUERY_ENDPOINT;

@RestController
@RequestMapping(CALENDAR_ENDPOINT)
@RequiredArgsConstructor
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    @PostMapping(QUERY_ENDPOINT)
    public List<AvailabilityResponseDTO> getAvailableSlots(@RequestBody AvailabilityRequestDTO request) {
        return availabilityService.getAvailableSlots(request);
    }
}