package com.Voxloud.AndriiHubarenko.controllers.user;

import com.Voxloud.AndriiHubarenko.domain.User;
import com.Voxloud.AndriiHubarenko.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserControllerDeleteTest {

    private long id;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService service;

    @BeforeEach
    public void init(){
        User user = new User();
        user.setEmail("qwerty@gmail.com");
        user.setPassword("123");
        user.setNickName("qwerty");
        this.id = service.createUser(user).getId();
    }

    @Test
    void deleteUser() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + this.id)
                .contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("User with email qwerty@gmail.com was successfully removed."));
    }
}
