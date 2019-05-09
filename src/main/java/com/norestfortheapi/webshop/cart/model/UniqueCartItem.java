package com.norestfortheapi.webshop.cart.model;

import com.norestfortheapi.webshop.cart.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniqueCartItem implements Serializable {

    private Long productId;

    private Cart cart;
}
