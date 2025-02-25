package com.appointmentbooking.model;

import lombok.Data;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

import static com.appointmentbooking.util.Constants.COLUMN_SALES_MANAGER_ID;
import static com.appointmentbooking.util.Constants.TABLE_SLOTS;

@Entity
@Table(name = TABLE_SLOTS)
@Data
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private boolean booked;

    @ManyToOne
    @JoinColumn(name = COLUMN_SALES_MANAGER_ID, nullable = false)
    private SalesManager salesManager;
}