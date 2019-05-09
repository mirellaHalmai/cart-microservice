package com.norestfortheapi.webshop.cart.controller;

import com.norestfortheapi.webshop.cart.model.Cart;
import com.norestfortheapi.webshop.cart.model.CartItem;
import com.norestfortheapi.webshop.cart.repository.CartRepository;
import com.norestfortheapi.webshop.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class CartController {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable("id") Long id) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            return optionalCart.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No available cart by this ID.");
        }
    }

    @PostMapping("/{id}/products")
    public Cart addProductToCart(
            @PathVariable("id") Long id,
            @RequestBody CartItem cartItem
            ) {
        try {
            return cartService.addProductToCart(id, cartItem);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No available cart by this ID.");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Cart createNewCart(@RequestBody Cart cart) {
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }

    @DeleteMapping("/{id}/products/{productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Cart deleteProductFromCart(@PathVariable("id") Long id, @PathVariable("productId") Long productId) {
        try {
            return cartService.deleteProductFromCart(id, productId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No available cart by this ID.");
        }
    }

}
