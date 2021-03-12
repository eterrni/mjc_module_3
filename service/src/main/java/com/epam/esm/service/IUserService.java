package com.epam.esm.service;

import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Interface provides methods to interact with UserRepository.
 *
 * @author Alexander Novikov
 */
public interface IUserService extends UserDetailsService {
    /**
     * Invokes DAO method to get User with provided id.
     *
     * @param id is id of user to be returned.
     * @return Object with user data.
     */
    UserDto read(int id);

    /**
     * Invokes DAO method to get List of all Users from database.
     *
     * @return List of {@link UserDto} objects with User data.
     */
    List<UserDto> readAll(int page, int size);

    /**
     * Invokes DAO method to save user in database.
     *
     * @param registrationUserDto object with registration data
     * @return Object with user data.
     */
    UserDto create(RegistrationUserDto registrationUserDto);

    /**
     * Delete user by ID
     *
     * @param id of the user we want to delete
     */
    void delete(int id);

    /**
     * Change Use role
     *
     * @param id of the user we want to change role
     */
    UserDto changeRole(int id);

    /**
     * Invokes DAO method to get user Role.
     *
     * @param id is id of user.
     * @return The Role of the user with the received ID
     */
    Role getUserRole(int id);
}
