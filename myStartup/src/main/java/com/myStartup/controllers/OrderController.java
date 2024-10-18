package com.myStartup.controllers;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myStartup.exceptions.ResourceNotFoundException;
import com.myStartup.model.Order;
import com.myStartup.requests.ApiResponse;
import com.myStartup.services.orderService.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	 private final OrderService orderService;

	    @PostMapping("/order")
	    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
	        try {
	            Order order =  orderService.placeOrder(userId);
	            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
	        } catch (Exception e) {
	            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured!", e.getMessage()));
	        }
	    }

	    @GetMapping("/{orderId}/order")
	    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
	        try {
	            OrderDto order = orderService.getOrder(orderId);
	            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
	        } catch (ResourceNotFoundException e) {
	           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
	        }
	    }

	    @GetMapping("/{userId}/order")
	    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
	        try {
	            List<OrderDto> order = orderService.getUserOrders(userId);
	            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
	        } catch (ResourceNotFoundException e) {
	            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
	        }
	    }
}
