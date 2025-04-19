package com.example.weatherapp.controller;

import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.jwt.JwtFilter;
import com.example.weatherapp.jwt.JwtUserDetailsService;
import com.example.weatherapp.service.interfaces.UserService;
import com.example.weatherapp.utils.TestDataUtil;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@SuppressWarnings("removal")
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    public void testGetAllUsers() throws Exception {

        Pageable pageable = PageRequest.of(0, 2, Sort.by("id").ascending());

        final List<UserDto> users = TestDataUtil.createTestUsers();

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), users.size());


        when(userService.getUsers(pageable)).thenReturn(new PageImpl<>(users.subList(start, end), pageable, users.size()));

        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("username1"))
                .andExpect(jsonPath("$[1].username").value("username2"))
                .andExpect(jsonPath("$.size()").value(pageable.getPageSize()));

    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(TestDataUtil.createTestUsers().getFirst());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username1"));
    }

}
