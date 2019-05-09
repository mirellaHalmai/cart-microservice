package com.norestfortheapi.webshop.cart.service;

import com.norestfortheapi.webshop.cart.model.Cart;
import com.norestfortheapi.webshop.cart.model.CartItem;
import com.norestfortheapi.webshop.cart.repository.CartItemRepository;
import com.norestfortheapi.webshop.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public Cart deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.getOne(cartId);
        List<CartItem> productsInCart = cart.getProducts();
        productsInCart.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .ifPresent(cartItem -> decreaseProductQuantity(cart, cartItem));
        return cartRepository.getOne(cartId);
    }

    private void decreaseProductQuantity(Cart cart, CartItem cartItem) {
        int quantity = cartItem.getQuantity();
        if (quantity > 1) {
            cartItem.setQuantity(--quantity);
            cartRepository.save(cart);
        } else if (quantity == 1) {
            cartItemRepository.delete(cartItem);
        }
    }

    public Cart addProductToCart(Long id, CartItem productToAdd) {
        Cart cart = cartRepository.getOne(id);
        Optional<CartItem> optionalCartItem = cart.getProducts().stream()
                .filter(product -> product.getProductId().equals(productToAdd.getProductId()))
                .findFirst();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            increaseProductQuantity(cartItem);
        } else {
            addNewProductToCart(cart, productToAdd);
        }
        cartRepository.save(cart);
        return cart;
    }

    private void addNewProductToCart(Cart cart, CartItem productToAdd) {
        CartItem cartItem = CartItem.builder()
                .productId(productToAdd.getProductId())
                .price(productToAdd.getPrice())
                .cart(cart)
                .name(productToAdd.getName())
                .quantity(1)
                .build();
        List<CartItem> products = cart.getProducts();
        products.add(cartItem);
        cartRepository.save(cart);
    }

    private void increaseProductQuantity(CartItem cartItem) {
        cartItem.setQuantity(cartItem.getQuantity() + 1);
    }
}
