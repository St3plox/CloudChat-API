package ru.tvey.cloudserverapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
@Profile("dev")
class CloudServerAppApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() {
		assertNotNull(mvc);
	}

/*
	@Test
	void unauthorizedRequestForbidden() throws Exception{
		RequestBuilder getUserRequest = MockMvcRequestBuilders.get("/cloud/1");

		mvc.perform(getUserRequest)
				.andExpect(status().isForbidden());
	}
*/
/*	@Test
	void existentUserCanGetTokenAndAuthentication() throws Exception {
		String username = "testUser";
		String password = "testPassword";

		String body = MessageFormat.format("'{'\"username\":" +
				" \"{0}\",\"password\": \"{1}\"'}'", username, password);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login")
						.content(body))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getHeader("Authorization");

		String bearerToken = "Bearer " + response;

		mvc.perform(MockMvcRequestBuilders.get("/cloud/user/10")
						.header("Authorization", bearerToken))
				.andExpect(status().isOk());
	}*//*



*/

}
