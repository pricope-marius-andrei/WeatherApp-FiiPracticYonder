package com.example.weatherapp.controller;

import com.example.weatherapp.dto.RequestHistoryDto;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.jwt.JwtFilter;
import com.example.weatherapp.service.interfaces.RequestHistoryService;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestHistoryController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class RequestHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestHistoryService requestHistoryService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetails userDetails;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    @WithMockUser(username = "username1")
    public void testGetAllRequests() throws Exception {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id").ascending());

        UserDto user = TestDataUtil.createTestUsers().getFirst();

        when(userService.getUserByUsername(user.getUsername())).thenReturn(user);

        Set<RequestHistoryDto> requestHistories = user.getRequestHistories();

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), requestHistories.size());

        when(requestHistoryService.getPagedRequestHistoriesByUserId(eq(user.getId()), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.copyOf(requestHistories).subList(start, end), pageable, requestHistories.size()));

        mockMvc.perform(MockMvcRequestBuilders.get("/request-history")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sortBy", "id"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()").value(pageable.getPageSize()));

    }

}
