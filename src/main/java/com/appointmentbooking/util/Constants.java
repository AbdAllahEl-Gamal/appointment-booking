package com.appointmentbooking.util;

public class Constants {

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
}