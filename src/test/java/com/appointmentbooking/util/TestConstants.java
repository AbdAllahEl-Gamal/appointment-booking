package com.appointmentbooking.util;

import java.util.List;

public class TestConstants {

    // Test Request Data
    public static final String TEST_DATE = "2024-05-03";
    public static final List<String> TEST_PRODUCTS = List.of("SolarPanels");
    public static final String TEST_LANGUAGE = "German";
    public static final String TEST_RATING = "Bronze";

    // Expected Response Data
    public static final int EXPECTED_AVAILABLE_COUNT = 1;
    public static final String EXPECTED_START_DATE = "2024-05-03T10:30:00.000Z";

    // JSON Request Body
    public static final String REQUEST_BODY = """
        {
            "date": "2024-05-03",
            "products": ["SolarPanels"],
            "language": "German",
            "rating": "Bronze"
        }
    """;

    // Log Message Templates
    public static final String LOG_FETCHING_AVAILABLE_SLOTS = "getAvailableSlots returned: {}";

    private TestConstants() {
        // Private constructor to prevent instantiation of utility class
    }
}