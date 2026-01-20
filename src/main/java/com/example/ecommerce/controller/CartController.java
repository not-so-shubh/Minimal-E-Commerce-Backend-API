package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.dto.CartClearResponse;
import com.example.ecommerce.dto.CartItemResponse;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(@RequestBody AddToCartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(cartService.addToCart(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@PathVariable String userId) {
        List<CartItem> items = cartService.getCartItems(userId);
        List<CartItemResponse> response = items.stream().map(item -> {
            CartItemResponse.ProductInfo productInfo = null;
            var product = productService.getProductById(item.getProductId());
            if (product.isPresent()) {
                Product p = product.get();
                productInfo = new CartItemResponse.ProductInfo(p.getId(), p.getName(), p.getPrice());
            }
            return new CartItemResponse(item.getId(), item.getProductId(), item.getQuantity(), productInfo);
        }).toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable String cartItemId) {
        cartService.removeFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<CartClearResponse> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(new CartClearResponse("Cart cleared successfully"));
    }

    @GetMapping("/{userId}/total")
    public ResponseEntity<Double> getCartTotal(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.calculateTotal(userId));
    }
}
