package com.lapanik.kameleoontrialtask.service;

import com.lapanik.kameleoontrialtask.exception.NotFoundException;
import com.lapanik.kameleoontrialtask.model.dto.UserDto;
import com.lapanik.kameleoontrialtask.model.entity.User;
import com.lapanik.kameleoontrialtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean checkEmail(String email) {
        return userRepository.existsByUsername(email);
    }

    public UserDto createUserService(UserDto userDto) {
        if (userRepository.existsByUsernameAndEmail(userDto.getUsername(), userDto.getEmail())) {
            throw new IllegalArgumentException("Username or Email already exist");
        } else {
            userRepository.save(
                    new User(
                            userDto.getUsername(),
                            passwordEncoder.encode(userDto.getPassword()),
                            userDto.getEmail()
                    )
            );
        }
        return userDto;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Not found user with username: " + username));
    }
}
