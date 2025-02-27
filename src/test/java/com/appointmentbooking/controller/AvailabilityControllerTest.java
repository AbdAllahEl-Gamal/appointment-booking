package com.appointmentbooking.controller;

import com.appointmentbooking.dto.AvailabilityRequestDTO;
import com.appointmentbooking.dto.AvailabilityResponseDTO;
import com.appointmentbooking.service.AvailabilityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.appointmentbooking.util.AppConstants.CALENDAR_ENDPOINT;
import static com.appointmentbooking.util.AppConstants.QUERY_ENDPOINT;
import static com.appointmentbooking.util.TestConstants.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AvailabilityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AvailabilityService availabilityService;

    @InjectMocks
    private AvailabilityController availabilityController;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(availabilityController).build();
    }

    @Test
    void getAvailableSlots_ShouldReturnSlots() throws Exception {
        when(availabilityService.getAvailableSlots(new AvailabilityRequestDTO(TEST_DATE, TEST_PRODUCTS, TEST_LANGUAGE, TEST_RATING)))
                .thenReturn(List.of(new AvailabilityResponseDTO(EXPECTED_AVAILABLE_COUNT, EXPECTED_START_DATE)));

        MvcResult mvcResult = mockMvc.perform(post(CALENDAR_ENDPOINT + QUERY_ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .content(REQUEST_BODY))
                .andExpect(status().isOk())
                .andReturn();

        log.atDebug().setMessage(LOG_FETCHING_AVAILABLE_SLOTS).addArgument(mvcResult.getResponse().getContentAsString()).log();
    }
}