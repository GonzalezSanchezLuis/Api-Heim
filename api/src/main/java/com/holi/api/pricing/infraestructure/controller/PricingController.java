package com.holi.api.pricing.infraestructure.controller;

import com.holi.api.ScheduleMove.domain.entity.ReservationMoving;
import com.holi.api.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {

    private final PricingService pricingService;

    @Autowired
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @PostMapping
    public BigDecimal getPrice(@RequestBody ReservationMoving reservation) {
        return pricingService.calculatePrice(reservation);
    }
}

