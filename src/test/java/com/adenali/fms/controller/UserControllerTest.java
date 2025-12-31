package com.adenali.fms.controller;

import com.adenali.fms.model.RegisterRequest;
import com.adenali.fms.model.Role;
import com.adenali.fms.model.User;
import com.adenali.fms.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    UserService userService;

    @MockitoBean
    PasswordEncoder passwordEncoder;


    @Test
    @WithMockUser(username = "john", roles = {"DRIVER"})
    void shouldRegisterUserSuccessfully() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPassword("Password@123");
        request.setRole(Role.DRIVER);

        Mockito.when(userService.findUserByEmail(request.getEmail())).thenReturn(null);

        Mockito.doNothing().when(userService).saveUser(Mockito.any(User.class));

        mockMvc.perform(post("/api/authservice/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "john", roles = {"DRIVER"})
    void shouldNotRegisterExistingUser() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPassword("Password@123");
        request.setRole(Role.DRIVER);

        Mockito.when(userService.findUserByEmail(request.getEmail())).thenReturn(new User());


        mockMvc.perform(post("/api/authservice/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrors() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setName("");
        request.setEmail("bad-email");
        request.setPassword("123");
        request.setRole(null);

        mockMvc.perform(post("/api/authservice/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }
}