package com.appointmentbooking.util;

import java.time.ZonedDateTime;
import java.util.List;

public class TestConstants {

    // Test Request Data
    public static final String TEST_DATE = "2024-05-03";
    public static final List<String> TEST_PRODUCTS = List.of("SolarPanels");
    public static final String TEST_PRODUCT_SOLAR_PANELS = "SolarPanels";
    public static final String TEST_LANGUAGE = "German";
    public static final String TEST_RATING = "Bronze";

    // Expected Response Data
    public static final int EXPECTED_AVAILABLE_COUNT = 1;
    public static final String EXPECTED_START_DATE = "2024-05-03T10:30:00.000Z";

    // Test Slots Data
    public static final ZonedDateTime AVAILABLE_SLOT_START_DATE = ZonedDateTime.parse("2024-05-03T10:00:00Z");
    public static final ZonedDateTime AVAILABLE_SLOT_END_DATE = ZonedDateTime.parse("2024-05-03T11:00:00Z");
    public static final ZonedDateTime BOOKED_SLOT_1_START_DATE = ZonedDateTime.parse("2024-05-03T10:30:00Z");
    public static final ZonedDateTime BOOKED_SLOT_1_END_DATE = ZonedDateTime.parse("2024-05-03T11:30:00Z");
    public static final ZonedDateTime BOOKED_SLOT_2_START_DATE = ZonedDateTime.parse("2024-05-03T12:00:00Z");
    public static final ZonedDateTime BOOKED_SLOT_2_END_DATE = ZonedDateTime.parse("2024-05-03T13:00:00Z");

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

    // Assertion Messages
    public static final String ASSERT_NO_MANAGERS_FOUND = "Should return empty list when no managers found.";
    public static final String ASSERT_NO_AVAILABLE_SLOTS = "Should return empty list when no available slots found.";
    public static final String ASSERT_NON_OVERLAPPING_SLOTS = "Non-overlapping slots should be returned correctly.";
    public static final String ASSERT_AVAILABLE_SLOTS = "Available slots should be returned correctly.";

    private TestConstants() {
        // Private constructor to prevent instantiation of utility class
    }
}