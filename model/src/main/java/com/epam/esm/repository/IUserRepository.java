package com.epam.esm.repository;

import com.epam.esm.entity.User;

/**
 * Interface provides methods to interact with User data from database.
 * Methods should connect to database and manipulate with data(read)
 */
public interface IUserRepository extends ICRDRepository<User, Integer> {
    /**
     * Get count of exist users.
     *
     * @return number of exist users.
     */
    long getCountOfEntities();

    /**
     * Connects to database and returns User by email.
     *
     * @param email is User email value.
     * @return {@link User} the entity on this email.
     */
    User readByEmail(String email);
}
