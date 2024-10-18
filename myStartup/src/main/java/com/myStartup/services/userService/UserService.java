package com.myStartup.services.userService;

import com.myStartup.dto.UserDto;
import com.myStartup.model.User;
import com.myStartup.requests.CreateUserRequest;
import com.myStartup.requests.UserUpdateRequest;

public interface UserService {
	User getUserById(Long userId);

	User createUser(CreateUserRequest request);

	User updateUser(UserUpdateRequest request, Long userId);

	void deleteUser(Long userId);

	UserDto convertUserToDto(User user);
}
