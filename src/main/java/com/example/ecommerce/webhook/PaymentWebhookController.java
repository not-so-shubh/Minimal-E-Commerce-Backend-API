package com.example.ecommerce.webhook;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/webhooks/payment")
@RequiredArgsConstructor
public class PaymentWebhookController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Map<String, String>> handlePaymentWebhook(@RequestBody PaymentWebhookRequest request) {
        log.info("Received payment webhook: {}", request);
        
        try {
            paymentService.updatePaymentStatus(
                request.getOrderId(),
                request.getStatus(),
                request.getPaymentId()
            );
            Map<String, String> response = new HashMap<>();
            response.put("message", "Webhook processed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error processing payment webhook", e);
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to process webhook");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
