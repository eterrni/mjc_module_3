package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Role;
import com.epam.esm.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<UserDto> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        List<UserDto> userDtoList = userService.readAll(page, size);
        addLinksToListUser(userDtoList);
        return addPagination(userDtoList,page,size,userService.getCountOfEntities());
    }

    /**
     * Invokes service method to get User that matches parameter userID
     *
     * @param userID The user number that we want to receive.
     * @return Object with User data.
     */
    @GetMapping("/user/{userID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public UserDto read(@PathVariable int userID) {

        return addLinksToUser(userService.read(userID));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeUser(@PathVariable int id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserRole(@PathVariable int id) {
        Role role = userService.getUserRole(id);
        return role.name();
    }

//    @PatchMapping("/{id}/role")
//    @PreAuthorize("hasRole('ADMIN')")
//    public RepresentationModel changeRole(@PathVariable int id) {
//        UserDto userDto = userService.changeRole(id);
//        return hateoasBuilder.addLinksForUser(userDto);
//    }
}
