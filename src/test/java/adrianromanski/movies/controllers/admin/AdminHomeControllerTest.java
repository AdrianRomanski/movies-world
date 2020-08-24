package adrianromanski.movies.controllers.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminHomeControllerTest {

    AdminHomeController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new AdminHomeController();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Happy Path, method = getAdminHome")
    void getAdminHome() throws Exception {
        mockMvc.perform(get("/admin/"))
                .andExpect(status().isOk())
                .andExpect(view().name( "admin/adminHome"));
    }
}