package com.example.bootcamp.service;

import com.example.bootcamp.entity.User;
import com.example.bootcamp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(BidService.class);


    public User register(User user) {
        User newUser = userRepository.findByEmail(user.getEmail());
        if (newUser == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            LOGGER.info("Message: {}", "user successfully registered");
            return userRepository.save(user);
        }
        return null;
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            LOGGER.info("Message: {}", "user successfully login");
            return user;
        }
        return null;
    }

}
