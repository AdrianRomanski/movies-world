package adrianromanski.movies.controllers.user;

import adrianromanski.movies.controllers.registration.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoginControllerTest {

    @InjectMocks
    LoginController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showLoginForm() throws Exception {
        mockMvc.perform(get("/movies-world/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
    }

    @Test
    void yourLoggedOut() throws Exception {
        mockMvc.perform(get("/movies-world/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("logout-success"));
    }
}