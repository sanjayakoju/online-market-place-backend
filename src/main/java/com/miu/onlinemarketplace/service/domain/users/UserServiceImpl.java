package com.miu.onlinemarketplace.service.domain.users;


import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.config.AppProperties;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.service.email.emailsender.EmailSenderService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto findUserByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = userRepository.findAll().stream().map(user -> {
            return modelMapper.map(user, UserDto.class);
        }).toList();
        return userDtoList;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }

}
