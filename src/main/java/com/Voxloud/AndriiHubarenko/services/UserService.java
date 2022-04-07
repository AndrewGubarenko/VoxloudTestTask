package com.Voxloud.AndriiHubarenko.services;

import com.Voxloud.AndriiHubarenko.domain.User;
import com.Voxloud.AndriiHubarenko.repositories.UserRepository;
import com.Voxloud.AndriiHubarenko.services.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
         this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if(user.getEmail() == null || user.getEmail().trim() =="")
            throw new RuntimeException("Field email is Empty");
        else if(user.getPassword() == null || user.getPassword().trim() == "")
            throw new RuntimeException("Field password is Empty");

        Optional<User> ifUserExist = userRepository.findByEmail(user.getEmail());
        if(ifUserExist.isPresent()) {
            throw new RuntimeException("User with such email already exist");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUser(long id) {
        Optional<User> foundUserOpt = userRepository.findById(id);
        if(!foundUserOpt.isPresent()) {
            throw new NoSuchElementException("User wasn't found!");
        }
        return foundUserOpt.get();
    }

    @Override
    @Transactional
    public User authenticate(String email, String password) {

        if(email == null || email =="")
            throw new RuntimeException("Field email is Empty");
        else if(password == null || password == "")
            throw new RuntimeException("Field password is Empty");

        Optional<User> userForAuthOpt = userRepository.findByEmail(email);
        if(!userForAuthOpt.isPresent())
            throw new NoSuchElementException("User wasn't found!");
        else {
            User userForAuth = userForAuthOpt.get();
            if (userForAuth.getPassword().equals(password))
                return userForAuth;
            else
                throw new RuntimeException("Incorrect password!");
        }
    }

    @Override
    @Transactional
    public User updateUser(long id, User updatedUser) {
        if (updatedUser.getId() != id) {
            throw new RuntimeException("Attemption of illegal access!");
        }
        User userForUpdate = this.getUser(updatedUser.getId());
        userForUpdate.setNickName(updatedUser.getNickName());
        userForUpdate.setPassword(updatedUser.getPassword());
        return userRepository.save(userForUpdate);
    }

    @Override
    @Transactional
    public String deleteUser(long id) {
        User userForDelete = userRepository.findById(id).get();
        userRepository.delete(userForDelete);
        return "User with email " + userForDelete.getEmail() + " was successfully removed.";
    }
}
