package com.norestfortheapi.webshop.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cart {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    private Long userId;

    private Status status;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.ALL})
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties({"cart"})
    private List<CartItem> products;
}
