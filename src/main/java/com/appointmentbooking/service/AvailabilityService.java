package com.appointmentbooking.service;

import com.appointmentbooking.dto.AvailabilityRequestDTO;
import com.appointmentbooking.dto.AvailabilityResponseDTO;
import com.appointmentbooking.model.SalesManager;
import com.appointmentbooking.model.Slot;
import com.appointmentbooking.repository.SalesManagerRepository;
import com.appointmentbooking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.appointmentbooking.util.AppConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvailabilityService {

    private final SalesManagerRepository salesManagerRepository;
    private final SlotRepository slotRepository;

    public List<AvailabilityResponseDTO> getAvailableSlots(AvailabilityRequestDTO request) {
        log.atDebug().setMessage(LOG_FETCHING_SLOTS).addArgument(request).log();

        // Fetch matching sales managers
        List<SalesManager> managers = request.products().stream()
                .flatMap(product -> salesManagerRepository.findByCriteria(
                        request.language(), product, request.rating()).stream())
                .distinct() // Avoid duplicates if a manager supports multiple requested products
                .toList();
        log.atDebug().setMessage(LOG_FETCHED_MANAGERS).addArgument(managers).log();

        return managers.stream()
                .flatMap(manager -> {
                    // Fetch available and booked slots
                    List<Slot> availableSlots = slotRepository.findBySalesManagerIdAndBookedFalseAndStartDateBetween(
                            manager.getId(),
                            ZonedDateTime.parse(request.date() + START_OF_DAY_SUFFIX),
                            ZonedDateTime.parse(request.date() + END_OF_DAY_SUFFIX)
                    );
                    log.atDebug().setMessage(LOG_FETCHED_AVAILABLE_SLOTS).addArgument(availableSlots).log();

                    List<Slot> bookedSlots = slotRepository.findBySalesManagerIdAndBookedTrueAndStartDateBetween(
                            manager.getId(),
                            ZonedDateTime.parse(request.date() + START_OF_DAY_SUFFIX),
                            ZonedDateTime.parse(request.date() + END_OF_DAY_SUFFIX)
                    );
                    log.atDebug().setMessage(LOG_FETCHED_BOOKED_SLOTS).addArgument(bookedSlots).log();

                    // Filter out overlapping slots
                    List<Slot> nonOverlappingSlots = availableSlots.stream()
                            .filter(slot -> bookedSlots.stream()
                                    .noneMatch(booked -> isOverlapping(slot, booked))) // Remove overlapping slots
                            .toList();
                    log.atDebug().setMessage(LOG_FETCHED_NON_OVERLAPPING_SLOTS).addArgument(nonOverlappingSlots).log();

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
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}