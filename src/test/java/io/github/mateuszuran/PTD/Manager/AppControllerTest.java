package io.github.mateuszuran.PTD.Manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "admin@o2.pl", password = "admin", authorities = "Admin")
    @Test
    void shouldGetHomePage() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().is(200));
    }

    @WithMockUser
    @Test
    void shouldReturnForbiddenRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnStatus200forAnonymousUser() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/login").with(anonymous()))
                .andExpect(status().is(200));
    }
}