package com.example.ecommerce.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.PaymentRequest;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final MockPaymentService mockPaymentService;

    public Payment initiatePayment(PaymentRequest request) {
        Optional<Order> orderOpt = orderService.getOrderById(request.getOrderId());
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        Order order = orderOpt.get();
        
        // Generate payment ID
        String paymentId = "pay_" + UUID.randomUUID().toString().substring(0, 8);
        
        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setStatus("PENDING");
        payment.setPaymentId(paymentId);
        payment.setCreatedAt(Instant.now());
        
        Payment savedPayment = paymentRepository.save(payment);

        // Call mock payment service asynchronously
        mockPaymentService.processMockPayment(paymentId, request.getOrderId(), request.getAmount());

        return savedPayment;
    }

    public Optional<Payment> getPaymentById(String id) {
        return paymentRepository.findById(id);
    }

    public Optional<Payment> getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public Payment updatePaymentStatus(String orderId, String status, String paymentId) {
        Optional<Payment> paymentOpt = paymentRepository.findByOrderId(orderId);
        if (paymentOpt.isEmpty()) {
            throw new RuntimeException("Payment not found for order: " + orderId);
        }

        Payment payment = paymentOpt.get();
        payment.setStatus(status);
        payment.setPaymentId(paymentId);
        
        if ("SUCCESS".equals(status)) {
            orderService.updateOrderStatus(payment.getOrderId(), "PAID");
        } else if ("FAILED".equals(status)) {
            orderService.updateOrderStatus(payment.getOrderId(), "FAILED");
        }

        return paymentRepository.save(payment);
    }
}
