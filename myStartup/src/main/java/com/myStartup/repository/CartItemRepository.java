package com.myStartup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myStartup.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	void deleteAllByCartId(Long id);

}
