package com.myStartup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myStartup.dto.UserDto;
import com.myStartup.exceptions.AlreadyExistsException;
import com.myStartup.exceptions.ResourceNotFoundException;
import com.myStartup.model.User;
import com.myStartup.requests.ApiResponse;
import com.myStartup.requests.CreateUserRequest;
import com.myStartup.requests.UserUpdateRequest;
import com.myStartup.services.userService.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@GetMapping("/{userId}/user")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
		try {
			User user = userService.getUserById(userId);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Success", userDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
		try {
			User user = userService.createUser(request);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Create User Success!", userDto));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/{userId}/update")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
		try {
			User user = userService.updateUser(request, userId);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/{userId}/delete")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
		try {
			userService.deleteUser(userId);
			return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
