package com.myStartup.services.orderService;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.myStartup.model.Order;

public interface OrderService {
	Order placeOrder(Long userId);

	OrderDto getOrder(Long orderId);

	List<OrderDto> getUserOrders(Long userId);
}
