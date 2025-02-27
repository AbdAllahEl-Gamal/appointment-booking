package com.appointmentbooking.service;

import com.appointmentbooking.dto.AvailabilityRequestDTO;
import com.appointmentbooking.dto.AvailabilityResponseDTO;
import com.appointmentbooking.model.SalesManager;
import com.appointmentbooking.model.Slot;
import com.appointmentbooking.repository.SalesManagerRepository;
import com.appointmentbooking.repository.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.appointmentbooking.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    private SalesManagerRepository salesManagerRepository;

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private AvailabilityService availabilityService;

    private AvailabilityRequestDTO request;
    private SalesManager salesManager;
    private Slot availableSlot;
    private Slot bookedSlot;

    @BeforeEach
    void setUp() {
        request = new AvailabilityRequestDTO(TEST_DATE, TEST_PRODUCTS, TEST_LANGUAGE, TEST_RATING);

        salesManager = new SalesManager();
        salesManager.setId(1L);

        availableSlot = new Slot();
        availableSlot.setSalesManager(salesManager); // Set the full SalesManager object
        availableSlot.setStartDate(AVAILABLE_SLOT_START_DATE);
        availableSlot.setEndDate(AVAILABLE_SLOT_END_DATE);
        availableSlot.setBooked(false);

        bookedSlot = new Slot();
        bookedSlot.setSalesManager(salesManager); // Set the full SalesManager object
        bookedSlot.setStartDate(BOOKED_SLOT_1_START_DATE);
        bookedSlot.setEndDate(BOOKED_SLOT_1_END_DATE);
        bookedSlot.setBooked(true);
    }

    @Test
    void getAvailableSlots_ShouldReturnNonOverlappingSlots() {
        when(salesManagerRepository.findByCriteria(TEST_LANGUAGE, TEST_PRODUCT_SOLAR_PANELS, TEST_RATING))
                .thenReturn(List.of(salesManager));

        when(slotRepository.findBySalesManagerIdAndBookedFalseAndStartDateBetween(anyLong(), any(), any()))
                .thenReturn(List.of(availableSlot));

        when(slotRepository.findBySalesManagerIdAndBookedTrueAndStartDateBetween(anyLong(), any(), any()))
                .thenReturn(List.of(bookedSlot));

        List<AvailabilityResponseDTO> response = availabilityService.getAvailableSlots(request);

        assertEquals(0, response.size(), ASSERT_NON_OVERLAPPING_SLOTS);
    }

    @Test
    void getAvailableSlots_ShouldReturnSlotsWhenNoOverlap() {
        bookedSlot.setStartDate(BOOKED_SLOT_2_START_DATE);
        bookedSlot.setEndDate(BOOKED_SLOT_2_END_DATE);

        when(salesManagerRepository.findByCriteria(TEST_LANGUAGE, TEST_PRODUCT_SOLAR_PANELS, TEST_RATING))
                .thenReturn(List.of(salesManager));

        when(slotRepository.findBySalesManagerIdAndBookedFalseAndStartDateBetween(anyLong(), any(), any()))
                .thenReturn(List.of(availableSlot));

        when(slotRepository.findBySalesManagerIdAndBookedTrueAndStartDateBetween(anyLong(), any(), any()))
                .thenReturn(List.of(bookedSlot));

        List<AvailabilityResponseDTO> response = availabilityService.getAvailableSlots(request);

        assertEquals(1, response.size(), ASSERT_AVAILABLE_SLOTS);
        assertEquals(1, response.getFirst().availableCount());
    }

    @Test
    void getAvailableSlots_ShouldReturnEmptyList_WhenNoManagersFound() {
        when(salesManagerRepository.findByCriteria(any(), any(), anyString()))
                .thenReturn(List.of());

        List<AvailabilityResponseDTO> response = availabilityService.getAvailableSlots(request);

        assertEquals(0, response.size(), ASSERT_NO_MANAGERS_FOUND);
    }

    @Test
    void getAvailableSlots_ShouldReturnEmptyList_WhenNoAvailableSlots() {
        when(salesManagerRepository.findByCriteria(TEST_LANGUAGE, TEST_PRODUCT_SOLAR_PANELS, TEST_RATING))
                .thenReturn(List.of(salesManager));

        when(slotRepository.findBySalesManagerIdAndBookedFalseAndStartDateBetween(anyLong(), any(), any()))
                .thenReturn(List.of());

        when(slotRepository.findBySalesManagerIdAndBookedTrueAndStartDateBetween(anyLong(), any(), any()))
                .thenReturn(List.of());

        List<AvailabilityResponseDTO> response = availabilityService.getAvailableSlots(request);

        assertEquals(0, response.size(), ASSERT_NO_AVAILABLE_SLOTS);
    }
}