package com.myStartup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myStartup.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUserId(Long userId);
}
