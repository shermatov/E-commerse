package com.shermatov.dreamshops.service.cart;

import com.shermatov.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface ICardService {
    Cart getCart(Long cartId);
    void clearCart(Long cartId);
    BigDecimal getTotalAmount(Long cartId);

}
