package com.miu.onlinemarketplace.service.domain.users;

import com.miu.onlinemarketplace.common.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findUserByEmail(String email);

    List<UserDto> getAllUsers();

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long id);

    UserDto updateUser(UserDto userDto);

}
