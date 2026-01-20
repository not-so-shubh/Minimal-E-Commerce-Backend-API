package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public List<CartItem> getCartItems(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public CartItem addToCart(AddToCartRequest request) {
        Optional<Product> productOpt = productService.getProductById(request.getProductId());
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        Optional<CartItem> existingItem = cartRepository.findByUserIdAndProductId(
            request.getUserId(), request.getProductId()
        );

        CartItem cartItem;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setUserId(request.getUserId());
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
        }

        return cartRepository.save(cartItem);
    }

    public void removeFromCart(String cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }

    public Double calculateTotal(String userId) {
        List<CartItem> items = cartRepository.findByUserId(userId);
        double total = 0.0;
        for (CartItem item : items) {
            Optional<Product> productOpt = productService.getProductById(item.getProductId());
            if (productOpt.isPresent()) {
                total += productOpt.get().getPrice() * item.getQuantity();
            }
        }
        return total;
    }
}
