package com.myStartup.services.orderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;

import com.myStartup.enums.OrderStatus;
import com.myStartup.exceptions.ResourceNotFoundException;
import com.myStartup.model.Cart;
import com.myStartup.model.Order;
import com.myStartup.model.OrderItem;
import com.myStartup.model.Product;
import com.myStartup.repository.OrderRepository;
import com.myStartup.repository.ProductRepository;
import com.myStartup.services.cartService.CartService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final CartService cartService;
	private final ModelMapper modelMapper;

	@Transactional
	@Override
	public Order placeOrder(Long userId) {
		Cart cart = cartService.getCartByUserId(userId);
		Order order = createOrder(cart);
		List<OrderItem> orderItemList = createOrderItems(order, cart);
		order.setOrderItems(new HashSet<>(orderItemList));
		order.setTotalAmount(calculateTotalAmount(orderItemList));
		Order savedOrder = orderRepository.save(order);
		cartService.clearCart(cart.getId());
		return savedOrder;
	}

	private Order createOrder(Cart cart) {
		Order order = new Order();
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
	}

	private List<OrderItem> createOrderItems(Order order, Cart cart) {
		return cart.getItems().stream().map(cartItem -> {
			Product product = cartItem.getProduct();
			product.setInventory(product.getInventory() - cartItem.getQuantity());
			productRepository.save(product);
			return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
		}).toList();

	}

	private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
		return orderItemList.stream().map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public OrderDto getOrder(Long orderId) {
		return orderRepository.findById(orderId).map(this::convertToDto)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
	}

	@Override
	public List<OrderDto> getUserOrders(Long userId) {
		List<Order> orders = orderRepository.findByUserId(userId);
		return orders.stream().map(this::convertToDto).toList();
	}

	private OrderDto convertToDto(Order order) {
		return modelMapper.map(order, OrderDto.class);
	}
}
