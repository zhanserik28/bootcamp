package com.example.bootcamp.controller;

import com.example.bootcamp.dto.UserDto;
import com.example.bootcamp.entity.User;
import com.example.bootcamp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
@Api("User management service")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("Create user")
    public ResponseEntity<User> register(@RequestBody UserDto user, @ApiIgnore HttpSession session) {
        User build = User.builder().email(user.getEmail()).password(user.getPassword()).build();
        User register = userService.register(build);
        if (register == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        session.setAttribute("user", register);
        return new ResponseEntity<>(register, HttpStatus.OK);
    }

    @PostMapping("/login")
    @ApiOperation("Login user")
    public ResponseEntity<User> login(@RequestBody UserDto user, @ApiIgnore HttpSession session) {
        User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        if (loggedInUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            session.setAttribute("user", loggedInUser);
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        }
    }

}
