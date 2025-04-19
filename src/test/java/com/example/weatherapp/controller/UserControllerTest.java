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
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockitoBean
    private JwtFilter jwtFilter;

    @Test
    public void givenUsersInDb_whenGetAllUsers_thenReturnList() throws Exception {

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
    public void givenUsersInDb_whenGetById_thenReturnTheUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(TestDataUtil.createTestUsers().getFirst());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username1"));
    }

    @Test
    public void givenNoUserInDb_whenGetById_thenReturnNotFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found!"));
    }

    @Test
    public void givenUsersInDb_whenGetByProfileId_thenReturnTheUser() throws Exception {
        when(userService.getUserByProfileId(1L)).thenReturn(TestDataUtil.createTestUsers().getFirst());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username1"));
    }

    @Test
    public void givenNoUserInDb_whenGetByProfileId_thenReturnNotFound() throws Exception {
        when(userService.getUserByProfileId(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found!"));
    }

    @Test
    public void givenUsersInDb_whenDeleteById_thenReturnOk() throws Exception {
        when(userService.getUserById(1L)).thenReturn(TestDataUtil.createTestUsers().getFirst());

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message").value("User deleted successfully!"));
    }

    @Test
    public void givenNoUserInDb_whenDeleteById_thenReturnNotFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found!"));
    }

    @Test
    public void givenValidUser_whenUpdateById_thenReturnUpdatedUser() throws Exception {
        UserDto user = TestDataUtil.createTestUsers().getFirst();
        UserDto updatedUser = new UserDto();
        updatedUser.setId(1L);
        updatedUser.setUsername("updatedUser");

        when(userService.getUserById(1L)).thenReturn(user);
        when(userService.updateUser(eq(1L), any(UserDto.class))).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType("application/json")
                .content("{\"username\":\"updatedUser\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"));

    }

    @Test
    public void givenNoUserInDb_whenUpdateById_thenReturnNotFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType("application/json")
                .content("{\"username\":\"updatedUser\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found!"));
    }

}
