package com.example.ecommerce.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerce.model.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentServiceClient {
    private final RestTemplate restTemplate;
    
    // Mock payment service URL - replace with actual payment gateway URL
    private static final String PAYMENT_SERVICE_URL = "https://api.payment-gateway.com/process";

    public String processPayment(Payment payment) {
        try {
            // Prepare payment request
            Map<String, Object> paymentRequest = new HashMap<>();
            paymentRequest.put("amount", payment.getAmount());
            paymentRequest.put("currency", "USD");
            paymentRequest.put("orderId", payment.getOrderId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer YOUR_API_KEY");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(paymentRequest, headers);

            // Call external payment service
            ResponseEntity<Map> response = restTemplate.postForEntity(
                PAYMENT_SERVICE_URL,
                request,
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("paymentId");
            }

            throw new RuntimeException("Payment processing failed");
        } catch (Exception e) {
            log.error("Error processing payment", e);
            // For mock purposes, return a dummy payment ID
            return "PAY-" + UUID.randomUUID().toString();
        }
    }
}
