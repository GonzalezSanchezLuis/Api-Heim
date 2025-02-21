package com.holi.api.pricing.service;

import com.holi.api.ScheduleMove.domain.entity.ReservationMoving;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

@Service
public class PricingService {

    private final double RATE_BASE  = 5.0;
    private final double COST_BY_KM = 2.0;
    private final double COST_BY_MIN = 0.50;

    private final RestTemplate restTemplate;

    @Value("${google.maps.api.key}") // Cargar API Key desde application.properties
    private String googleApiKey;

    public PricingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal calculatePrice(ReservationMoving reservation) {
        try {
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json" +
                    "?origins=" + reservation.getOrigin() +
                    "&destinations=" + reservation.getDestination() +
                    "&key=" + googleApiKey;

            String response = restTemplate.getForObject(url, String.class);
            JSONObject json = new JSONObject(response);



            JSONObject element = json.getJSONArray("rows")
                    .getJSONObject(0)
                    .getJSONArray("elements")
                    .getJSONObject(0);

            double distanceKm = element.getJSONObject("distance").getDouble("value") / 1000.0;
            double timeMin = element.getJSONObject("duration").getDouble("value") / 60.0;

            double calculatedPrice = RATE_BASE + (COST_BY_KM * distanceKm) + (COST_BY_MIN * timeMin);

            return BigDecimal.valueOf(calculatedPrice);

        } catch (Exception e) {
            throw new RuntimeException("Error al calcular precio", e);
        }
    }
}
