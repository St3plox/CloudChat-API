package ru.tvey.cloudserverapp;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Profile("dev")
public class FileSystemTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String bearerToken;

    @BeforeEach
    void getAuthToken() throws Exception {
        String username = "testUser";
        String password = "testPassword";

        String body = MessageFormat.format("'{'\"username\":" +
                " \"{0}\",\"password\": \"{1}\"'}'", username, password);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getHeader("Authorization");

        bearerToken = "Bearer " + response;
    }

}
