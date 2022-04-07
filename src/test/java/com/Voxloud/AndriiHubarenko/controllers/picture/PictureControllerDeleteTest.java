package com.Voxloud.AndriiHubarenko.controllers.picture;

import com.Voxloud.AndriiHubarenko.domain.ContentType;
import com.Voxloud.AndriiHubarenko.domain.Picture;
import com.Voxloud.AndriiHubarenko.domain.Tag;
import com.Voxloud.AndriiHubarenko.domain.User;
import com.Voxloud.AndriiHubarenko.services.PictureService;
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
public class PictureControllerDeleteTest {

    private User user;
    private Picture picture;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PictureService pictureService;

    @BeforeEach
    public void init(){
        User user = new User();
        user.setEmail("qwerty@gmail.com");
        user.setPassword("123");
        user.setNickName("qwerty");
        this.user = userService.createUser(user);

        Picture picture = new Picture();
        picture.setPictureName("file1");
        picture.setContentType(ContentType.BMP);
        picture.setSize(12.2f);
        picture.setReference("http");
        picture.addTag(new Tag("#picture"));
        picture.addTag(new Tag("#logo"));
        this.picture = pictureService.createPicture(this.user.getId(), picture);
    }

    @AfterEach
    public void cleanUp() {
        userService.deleteUser(this.user.getId());
    }

    @Test
    void deletePicture() throws Exception {
        System.out.println(this.user);
        System.out.println(this.picture);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + this.user.getId() + "/picture/" + this.picture.getId())
                .contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Picture removed"));
    }
}
