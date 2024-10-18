package com.myStartup.services.cartService;

import java.math.BigDecimal;

import com.myStartup.model.Cart;

public interface CartService {
	Cart getCart(Long id);

	void clearCart(Long id);

	BigDecimal getTotalPrice(Long id);

	Long initializeNewCart();

	Cart getCartByUserId(Long userId);
}
