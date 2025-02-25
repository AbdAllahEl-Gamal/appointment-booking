package com.appointmentbooking.service;

import com.appointmentbooking.dto.AvailabilityRequestDTO;
import com.appointmentbooking.dto.AvailabilityResponseDTO;
import com.appointmentbooking.model.SalesManager;
import com.appointmentbooking.model.Slot;
import com.appointmentbooking.repository.SalesManagerRepository;
import com.appointmentbooking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final SalesManagerRepository salesManagerRepository;
    private final SlotRepository slotRepository;
    private static final Logger logger = LoggerFactory.getLogger(AvailabilityService.class);

    public List<AvailabilityResponseDTO> getAvailableSlots(AvailabilityRequestDTO request) {
        logger.info("Fetching available slots for request: {}", request);

        // Fetch matching sales managers
        List<SalesManager> managers = request.products().stream()
                .flatMap(product -> salesManagerRepository.findByCriteria(
                        request.language(), product, request.rating()).stream())
                .distinct() // Avoid duplicates if a manager supports multiple requested products
                .toList();

        return managers.stream()
                .flatMap(manager -> {
                    // Fetch available and booked slots
                    List<Slot> availableSlots = slotRepository.findBySalesManagerIdAndBookedFalseAndStartDateBetween(
                            manager.getId(),
                            ZonedDateTime.parse(request.date() + "T00:00:00Z"),
                            ZonedDateTime.parse(request.date() + "T23:59:59Z")
                    );

                    List<Slot> bookedSlots = slotRepository.findBySalesManagerIdAndBookedTrueAndStartDateBetween(
                            manager.getId(),
                            ZonedDateTime.parse(request.date() + "T00:00:00Z"),
                            ZonedDateTime.parse(request.date() + "T23:59:59Z")
                    );

                    // Filter out overlapping slots
                    List<Slot> nonOverlappingSlots = availableSlots.stream()
                            .filter(slot -> bookedSlots.stream()
                                    .noneMatch(booked -> isOverlapping(slot, booked))) // Remove overlapping slots
                            .toList();

                    // Group remaining slots by start time and count them
                    return nonOverlappingSlots.stream()
                            .collect(Collectors.groupingBy(Slot::getStartDate, Collectors.counting())) // Group & count
                            .entrySet().stream()
                            .map(entry -> new AvailabilityResponseDTO(entry.getValue().intValue(), formatDate(entry.getKey())));
                })
                .collect(Collectors.toList());
    }

    private boolean isOverlapping(Slot availableSlot, Slot bookedSlot) {
        return !(availableSlot.getEndDate().isBefore(bookedSlot.getStartDate()) ||
                availableSlot.getStartDate().isAfter(bookedSlot.getEndDate()));
    }

    private String formatDate(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
    }
}