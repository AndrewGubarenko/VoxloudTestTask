package com.Voxloud.AndriiHubarenko.services.user;

import com.Voxloud.AndriiHubarenko.domain.User;
import com.Voxloud.AndriiHubarenko.repositories.UserRepository;
import com.Voxloud.AndriiHubarenko.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.NoSuchElementException;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService service;

    @Test
    void createUser() {
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setNickName("userNick");
        user.setPassword("123");
        service.createUser(user);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void errorOnCreate() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            service.createUser(new User());
        });

        Assertions.assertEquals("Field email is Empty", thrown.getMessage());
    }

    @Test
    void getUser() {
        try {
            service.getUser(ArgumentMatchers.anyLong());
        } catch (NoSuchElementException e){

        } finally {
            Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        }
    }

    @Test
    void authenticate() {
        try {
            service.authenticate("user@gmail.com", "123");
        } catch (NoSuchElementException ex) {

        } finally {
            Mockito.verify(userRepository, Mockito.times(1)).findByEmail("user@gmail.com");
        }


    }

    @Test
    void updateUser() {
        User user = new User();
        user.setEmail("qwerty@gmail.com");
        user.setPassword("123");
        user.setNickName("otherNickName");

        try {
            service.updateUser(user.getId(), user);
        } catch (NoSuchElementException ex) {

        } finally {
            Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        }
    }

    @Test
    void errorOnUpdate() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            service.updateUser(1, new User());
        });

        Assertions.assertEquals("Attemption of illegal access!", thrown.getMessage());
    }

    @Test
    void deleteUser() {
        try {
            service.deleteUser(1);
        } catch (NoSuchElementException ex) {

        } finally {
            Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        }

    }
}