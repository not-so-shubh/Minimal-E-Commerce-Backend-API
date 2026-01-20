package com.example.ecommerce.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final ProductService productService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order createOrder(CreateOrderRequest request) {
        List<CartItem> cartItems = cartService.getCartItems(request.getUserId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Calculate total and update stock
        double totalAmount = 0.0;
        for (CartItem item : cartItems) {
            Optional<Product> productOpt = productService.getProductById(item.getProductId());
            if (productOpt.isEmpty()) {
                throw new RuntimeException("Product not found: " + item.getProductId());
            }
            
            Product product = productOpt.get();
            boolean stockUpdated = productService.updateStock(item.getProductId(), item.getQuantity());
            if (!stockUpdated) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            
            totalAmount += product.getPrice() * item.getQuantity();
        }

        // Create order
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setTotalAmount(totalAmount);
        order.setStatus("CREATED");
        order.setCreatedAt(Instant.now());

        Order savedOrder = orderRepository.save(order);

        // Create order items
        for (CartItem item : cartItems) {
            Optional<Product> productOpt = productService.getProductById(item.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(savedOrder.getId());
                orderItem.setProductId(item.getProductId());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(product.getPrice());
                orderItemRepository.save(orderItem);
            }
        }

        // Clear cart after order creation
        cartService.clearCart(request.getUserId());

        return savedOrder;
    }

    public Order updateOrderStatus(String orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        Order order = orderOpt.get();
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void cancelOrder(String orderId) {
        updateOrderStatus(orderId, "CANCELLED");
    }

    public Order cancelOrderWithStockRestore(String orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        Order order = orderOpt.get();
        
        // Only allow cancellation if order is not paid
        if ("PAID".equals(order.getStatus())) {
            throw new RuntimeException("Cannot cancel a paid order");
        }

        // Restore stock for all order items
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        for (OrderItem item : items) {
            Optional<Product> productOpt = productService.getProductById(item.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                product.setStock(product.getStock() + item.getQuantity());
                productService.updateProductDirect(product);
            }
        }

        // Update order status to CANCELLED
        order.setStatus("CANCELLED");
        return orderRepository.save(order);
    }
    
    public List<OrderItem> getOrderItems(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
