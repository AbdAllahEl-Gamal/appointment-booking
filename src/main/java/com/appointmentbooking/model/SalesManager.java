package com.appointmentbooking.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

import static com.appointmentbooking.util.Constants.*;

@Entity
@Table(name = TABLE_SALES_MANAGERS)
@Data
public class SalesManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = COLUMN_LANGUAGES, columnDefinition = TYPE_TEXT_ARRAY)
    private List<String> languages;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = COLUMN_PRODUCTS, columnDefinition = TYPE_TEXT_ARRAY)
    private List<String> products;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = COLUMN_CUSTOMER_RATINGS, columnDefinition = TYPE_TEXT_ARRAY)
    private List<String> customerRatings;
}