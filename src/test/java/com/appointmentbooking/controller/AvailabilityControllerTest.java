package com.appointmentbooking.controller;

import com.appointmentbooking.dto.AvailabilityRequestDTO;
import com.appointmentbooking.dto.AvailabilityResponseDTO;
import com.appointmentbooking.service.AvailabilityService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.appointmentbooking.util.Constants.CALENDAR_ENDPOINT;
import static com.appointmentbooking.util.Constants.QUERY_ENDPOINT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(MockitoExtension.class)
class AvailabilityControllerTest {

    private final MockMvc mockMvc;

    @Mock
    private AvailabilityService availabilityService;

    @InjectMocks
    private AvailabilityController availabilityController;

    AvailabilityControllerTest() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(availabilityController).build();
    }

    @Test
    void getAvailableSlots_ShouldReturnSlots() throws Exception {
        when(availabilityService.getAvailableSlots(new AvailabilityRequestDTO("2024-05-03", List.of("SolarPanels"), "German", "Bronze")))
                .thenReturn(List.of(new AvailabilityResponseDTO(1, "2024-05-03T10:30:00.000Z")));

        mockMvc.perform(post(CALENDAR_ENDPOINT + QUERY_ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .content("""
                        {
                            "date": "2024-05-03",
                            "products": ["SolarPanels"],
                            "language": "German",
                            "rating": "Bronze"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].available_count").value(1))
                .andExpect(jsonPath("$[0].start_date").value("2024-05-03T10:30:00.000Z"));
    }
}