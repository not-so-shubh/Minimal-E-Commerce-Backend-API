package com.example.ecommerce.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerce.dto.PaymentWebhookRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MockPaymentService {
    private final RestTemplate restTemplate;

    @Async
    public void processMockPayment(String paymentId, String orderId, Double amount) {
        try {
            // Simulate payment processing delay
            Thread.sleep(3000); // Wait 3 seconds before calling webhook

            // Create webhook request
            PaymentWebhookRequest webhookRequest = new PaymentWebhookRequest();
            webhookRequest.setPaymentId(paymentId);
            webhookRequest.setOrderId(orderId);
            webhookRequest.setStatus("SUCCESS");
            webhookRequest.setMessage("Payment processed successfully");

            // Call webhook endpoint
            String webhookUrl = "http://localhost:8080/api/webhooks/payment";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentWebhookRequest> request = new HttpEntity<>(webhookRequest, headers);

            log.info("Calling webhook for payment: {} with status: SUCCESS", paymentId);
            restTemplate.postForEntity(webhookUrl, request, String.class);

        } catch (InterruptedException e) {
            log.error("Payment processing interrupted", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error calling webhook for payment: {}", paymentId, e);
        }
    }
}
