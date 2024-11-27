package com.shermatov.dreamshops.repository;

import com.shermatov.dreamshops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {
}
