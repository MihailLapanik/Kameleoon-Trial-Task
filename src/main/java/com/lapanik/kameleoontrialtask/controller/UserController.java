package com.lapanik.kameleoontrialtask.controller;

import com.lapanik.kameleoontrialtask.model.dto.UserDto;
import com.lapanik.kameleoontrialtask.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "User Management", description = "Endpoints for managing User entities")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/check/username")
    public Boolean checkUsername(@RequestParam String username) {
        return userService.checkUsername(username);
    }

    @GetMapping("/check/email")
    public Boolean checkEmail(@RequestParam String email) {
        return userService.checkEmail(email);
    }

    @PostMapping("/create")
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUserService(userDto);
    }
}
