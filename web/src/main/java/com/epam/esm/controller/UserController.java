package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles requests related to the user
 *
 * @author Alexander Novikov
 */
@RestController
@RequestMapping("/api")
public class UserController extends HATEOASController<UserDto> {
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
    public PagedModel<UserDto> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        List<UserDto> userDtoList = userService.readAll(page, size);
        addLinksToListUser(userDtoList);
        return addPagination(userDtoList, page, size, userService.getCountOfEntities());
    }

    /**
     * Invokes service method to get User that matches parameter userID
     *
     * @param userID The user number that we want to receive.
     * @return Object with User data.
     */
    @GetMapping("/user/{userID}")
    public UserDto read(@PathVariable int userID) {

        return addLinksToUser(userService.read(userID));
    }
}
