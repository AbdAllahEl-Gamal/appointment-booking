package com.appointmentbooking.repository;

import com.appointmentbooking.model.SalesManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.appointmentbooking.util.AppConstants.*;

public interface SalesManagerRepository extends JpaRepository<SalesManager, Long> {

    @Query(value = FIND_SALES_MANAGERS_BY_CRITERIA, nativeQuery = true)
    List<SalesManager> findByCriteria(
            @Param(PARAM_LANGUAGE) String language,
            @Param(PARAM_PRODUCT) String product,
            @Param(PARAM_RATING) String rating
    );
}