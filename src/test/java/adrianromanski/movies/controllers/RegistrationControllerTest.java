package adrianromanski.movies.controllers;

import adrianromanski.movies.model.UserDTO;
import adrianromanski.movies.services.UserService;
import org.junit.Before;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RegistrationControllerTest {


    @Mock
    UserService userService;

    @InjectMocks
    RegistrationController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

    @Test
    public void registrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("userDTO"));
    }

    @Test
    public void checkRegistrationHappyPath() throws Exception {
        UserDTO userDTO = new UserDTO();

        when(userService.save(any())).thenReturn(userDTO);

        mockMvc.perform(post("/checkRegistration")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Adrian")
                .param("lastName", "Romanski")
                .param("gender", "Male")
                .param("username", "adrian1337")
                .param("password", "hothotpassword")
        )
                .andExpect(view().name("successRegistration"))
                .andExpect(model().attributeExists("userDTO"));
    }

    @Test
    public void checkRegistrationUnHappyPath() throws Exception {
        UserDTO userDTO = new UserDTO();

        when(userService.save(any())).thenReturn(userDTO);

        mockMvc.perform(post("/checkRegistration")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("userDTO"));
    }
}
