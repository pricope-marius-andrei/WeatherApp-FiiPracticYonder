package com.example.weatherapp.controller;

import com.example.weatherapp.dto.UserProfileDto;
import com.example.weatherapp.jwt.JwtFilter;
import com.example.weatherapp.service.JwtUserDetailsService;
import com.example.weatherapp.service.interfaces.UserProfileService;
import com.example.weatherapp.service.interfaces.UserService;
import com.example.weatherapp.utils.TestDataUtil;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserProfileController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    public void givenUserProfileInDb_whenGetAllUserProfiles_thenReturnList() throws Exception {

        Pageable pageable = PageRequest.of(0, 2, Sort.by("id").ascending());

        final List<UserProfileDto> usersProfiles = TestDataUtil.createTestUserProfiles();

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), usersProfiles.size());


        when(userProfileService.getUserProfiles(pageable)).thenReturn(new PageImpl<>(usersProfiles.subList(start, end), pageable, usersProfiles.size()));

        mockMvc.perform(MockMvcRequestBuilders.get("/user-profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("username1@gmail.com"))
                .andExpect(jsonPath("$[1].email").value("username2@gmail.com"));
    }

}
