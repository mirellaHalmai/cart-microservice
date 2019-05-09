package com.norestfortheapi.webshop.cart.repository;

import com.norestfortheapi.webshop.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
