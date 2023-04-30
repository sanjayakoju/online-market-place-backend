package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.service.domain.users.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUser() {
        log.info("User API: getAllUser");
        List<UserDto> userDTOList = userService.getAllUsers();
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or #id == authentication.principal.userId")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        log.info("User API: getUserById: ", id);
        UserDto userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        log.info("User API: updateUser");
        UserDto returnedUserDto = userService.updateUser(userDto);
        return new ResponseEntity<>(returnedUserDto, HttpStatus.OK);
    }

}
