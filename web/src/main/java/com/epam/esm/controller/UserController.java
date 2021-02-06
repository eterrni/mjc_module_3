package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller that handles requests related to the user
 *
 * @author Alexander Novikov
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Invokes service method to get List of all Users.
     *
     * @return List of {@link UserDto} objects with User data.
     */
    @GetMapping("/users")
    public List<UserDto> readAll() {
        return userService.readAll();
    }

    /**
     * Invokes service method to get User that matches parameter userID
     *
     * @param userID The user number that we want to receive.
     * @return Object with User data.
     */
    @GetMapping("/user/{userID}")
    public UserDto read(@PathVariable int userID) {
        return userService.read(userID);
    }
}
