package com.Voxloud.AndriiHubarenko.controllers.picture;

import com.Voxloud.AndriiHubarenko.domain.ContentType;
import com.Voxloud.AndriiHubarenko.domain.Picture;
import com.Voxloud.AndriiHubarenko.domain.Tag;
import com.Voxloud.AndriiHubarenko.domain.User;
import com.Voxloud.AndriiHubarenko.services.PictureService;
import com.Voxloud.AndriiHubarenko.services.UserService;
import org.hamcrest.Matchers;
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
class PictureControllerTest {

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
    void createPicture() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/" + this.user.getId() + "/picture")
                .contentType("application/json;charset=UTF-8")
                .content("{\n" +
                        "    \"pictureName\": \"file2\",\n" +
                        "    \"contentType\": \"BMP\",\n" +
                        "    \"size\": \"12.2\",\n" +
                        "    \"reference\": \"http\",\n" +
                        "    \"tags\": [\n" +
                        "            {\"name\": \"#picture\"},\n" +
                        "            {\"name\": \"#logo\"}\n" +
                        "    ]\n" +
                        "}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"id\":" + (this.picture.getId() + 1) + ",\"pictureName\":\"file2\",\"contentType\":\"BMP\",\"size\":12.2,\"reference\":\"http\"")));
    }


    @Test
    void getPicture() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/" + this.user.getId() + "/picture/" + this.picture.getId())
                .contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"id\":" + this.picture.getId() + ",\"pictureName\":\"file1\",\"contentType\":\"BMP\",\"size\":12.2,\"reference\":\"http\"")));
    }

    @Test
    void updatePicture() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.put("/user/" + this.user.getId() + "/picture")
                .contentType("application/json;charset=UTF-8")
                .content("{\n" +
                        "    \"id\": \"" + this.picture.getId() + "\",\n" +
                        "    \"pictureName\": \"file1\",\n" +
                        "    \"contentType\": \"JPEG\",\n" +
                        "    \"size\": \"13.3\",\n" +
                        "    \"reference\": \"http\",\n" +
                        "    \"tags\": [\n" +
                        "            {\"name\": \"#picture\"},\n" +
                        "            {\"name\": \"#logo\"}\n" +
                        "    ]\n" +
                        "}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                    .string(Matchers.containsString("{\"id\":" + this.picture.getId() + ",\"pictureName\":\"file1\",\"contentType\":\"JPEG\",\"size\":13.3,\"reference\":\"http\"")));
    }

}