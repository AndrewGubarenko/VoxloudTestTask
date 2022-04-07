package com.Voxloud.AndriiHubarenko.controllers.user;

import com.Voxloud.AndriiHubarenko.domain.User;
import com.Voxloud.AndriiHubarenko.services.UserService;
import org.junit.jupiter.api.AfterEach;
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
class UserControllerTest {

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

    @AfterEach
    public void cleanUp() {
        service.deleteUser(this.id);
    }

    @Test
    void authenticate() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/credentials")
                .contentType("application/json;charset=UTF-8")
                .content("{\n" +
                        "    \"email\": \"qwerty@gmail.com\",\n" +
                        "    \"password\": \"123\" \n" +
                        "}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":" + this.id + ",\"email\":\"qwerty@gmail.com\",\"nickName\":\"qwerty\",\"password\":\"\",\"pictures\":[]}"));
    }

    @Test
    void createUser() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user")
                    .contentType("application/json;charset=UTF-8")
                    .content("{\n" +
                            "    \"email\": \"user@gmail.com\",\n" +
                            "    \"nickName\": \"userNick\",\n" +
                            "    \"password\": \"123\"\n" +
                            "}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                    .string("{\"id\":" + (this.id + 1) + ",\"email\":\"user@gmail.com\",\"nickName\":\"userNick\",\"password\":\"\",\"pictures\":[]}"));
    }

    @Test
    void getUser() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/" + this.id)
                .contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                    .string("{\"id\":" + this.id + ",\"email\":\"qwerty@gmail.com\",\"nickName\":\"qwerty\",\"password\":\"\",\"pictures\":[]}"));
    }

    @Test
    void updateUser() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.put("/user/" + this.id)
                .contentType("application/json;charset=UTF-8")
                .content("{\n" +
                        "    \"id\": \"1\",\n" +
                        "    \"email\": \"qwerty@gmail.com\",\n" +
                        "    \"nickName\": \"userNick\",\n" +
                        "    \"password\": \"123\"\n" +
                        "}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":"+ this.id + ",\"email\":\"qwerty@gmail.com\",\"nickName\":\"userNick\",\"password\":\"\",\"pictures\":[]}"));
    }

}