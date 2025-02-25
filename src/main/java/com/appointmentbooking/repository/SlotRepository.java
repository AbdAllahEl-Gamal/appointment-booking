package com.appointmentbooking.repository;

import com.appointmentbooking.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    List<Slot> findBySalesManagerIdAndBookedFalseAndStartDateBetween(
            Long salesManagerId, ZonedDateTime start, ZonedDateTime end);

    List<Slot> findBySalesManagerIdAndBookedTrueAndStartDateBetween(
            Long salesManagerId, ZonedDateTime start, ZonedDateTime end);
}