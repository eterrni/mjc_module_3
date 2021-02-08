package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.List;

/**
 * Interface provides methods to interact with UserRepository.
 *
 * @author Alexander Novikov
 */
public interface IUserService {
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
}
