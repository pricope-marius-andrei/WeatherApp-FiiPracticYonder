package com.example.weatherapp.controller;

import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.dto.RequestHistoryDto;
import com.example.weatherapp.service.RequestHistoryServiceImpl;
import com.example.weatherapp.service.interfaces.RequestHistoryService;
import com.example.weatherapp.service.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request-history")
public class RequestHistoryController {

    private final RequestHistoryService requestHistoryService;
    private final UserService userService;

    public RequestHistoryController(RequestHistoryService requestHistoryServiceImpl, UserService userService) {
        this.requestHistoryService = requestHistoryServiceImpl;
        this.userService = userService;
    }

    @GetMapping
    public List<RequestHistoryDto> getAllRequests(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        UserDto user = userService.getUserByUsername(userDetails.getUsername());

        return requestHistoryService.getPagedRequestHistoriesByUserId(user.getId(), pageable).getContent();
    }
}
