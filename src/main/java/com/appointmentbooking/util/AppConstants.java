package com.appointmentbooking.util;

public class AppConstants {

    // Endpoints
    public static final String CALENDAR_ENDPOINT = "/calendar";
    public static final String QUERY_ENDPOINT = "/query";

    // Table Names
    public static final String TABLE_SALES_MANAGERS = "sales_managers";
    public static final String TABLE_SLOTS = "slots";

    // Column Names
    public static final String COLUMN_LANGUAGES = "languages";
    public static final String COLUMN_PRODUCTS = "products";
    public static final String COLUMN_CUSTOMER_RATINGS = "customer_ratings";
    public static final String COLUMN_SALES_MANAGER_ID = "sales_manager_id";

    // Data Types
    public static final String TYPE_TEXT_ARRAY = "text[]";

    // Query Parameters
    public static final String PARAM_LANGUAGE = "language";
    public static final String PARAM_PRODUCT = "product";
    public static final String PARAM_RATING = "rating";

    // Queries
    public static final String FIND_SALES_MANAGERS_BY_CRITERIA = """
        SELECT * FROM sales_managers WHERE :language = ANY(languages)
        AND EXISTS (SELECT 1 FROM unnest(products) p WHERE p = :product)
        AND :rating = ANY(customer_ratings)
        """;

    // Standard date-time format for parsing and formatting ZonedDateTime
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    // Time suffixes for constructing start and end of the day in UTC
    public static final String START_OF_DAY_SUFFIX = "T00:00:00Z";
    public static final String END_OF_DAY_SUFFIX = "T23:59:59Z";

    // Log message templates
    public static final String LOG_FETCHING_SLOTS = "Fetching available slots for request: {}";
    public static final String LOG_FETCHED_MANAGERS = "Fetched available managers: {}";
    public static final String LOG_FETCHED_AVAILABLE_SLOTS = "Fetched available slots: {}";
    public static final String LOG_FETCHED_BOOKED_SLOTS = "Fetched booked slots: {}";
    public static final String LOG_FETCHED_NON_OVERLAPPING_SLOTS = "Fetched available nonOverlappingSlots: {}";

    private AppConstants() {
        // Private constructor to prevent instantiation of utility class
    }
}