package com.Voxloud.AndriiHubarenko.services.interfaces;

import com.Voxloud.AndriiHubarenko.domain.User;

import java.util.UUID;

/**
 * @author Andrii Hubarenko
 * Interface for User service
 */
public interface UserServiceInterface {

    /**
     * Method for creating user in data base
     * @param user new user
     * @return created user
     */
    User createUser (User user);

    /**
     * Method for extraction of user by it`s Id
     * @param id of user, that should be retrieved
     * @return extracted user or throw exception
     */
    User getUser (long id);

    /**
     * Method for user's authentication
     * @param email from client
     * @param password from client
     * @return authenticated user
     */
    User authenticate (String email, String password);

    /**
     * Method for updating of user
     * id of user? who request update
     * @param user the updated user from client
     * @retur user from DB after update
     */
    User updateUser(long id, User user);

    /**
     * Method for removing user from data base by it`s Id
     * @param id of user? that should be removed
     * @return string acknowledge of operation
     */
    String deleteUser(long id);

}
