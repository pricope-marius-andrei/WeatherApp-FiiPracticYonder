package com.example.weatherapp.aspect;

import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.mapper.UserMapper;
import com.example.weatherapp.model.RequestHistory;
import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.service.interfaces.EmailService;
import com.example.weatherapp.service.interfaces.RequestHistoryService;
import com.example.weatherapp.service.interfaces.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestHistoryAspect {
    private final RequestHistoryService requestHistoryService;
    private final EmailService emailService;
    private final UserService userService;
    private final UserMapper userMapper;

    public RequestHistoryAspect(RequestHistoryService requestHistoryService, EmailService emailService, UserService userService, UserMapper userMapper) {
        this.requestHistoryService = requestHistoryService;
        this.emailService = emailService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @AfterReturning(
            pointcut = "@annotation(com.example.weatherapp.controller.annotation.LogRequestHistory)",
            returning = "response"
    )
    public void logRequestHistory(JoinPoint joinPoint, Object response) {

        if (!(response instanceof ResponseEntity<?> res && res.getBody() instanceof WeatherDto weatherDto)) {
            return;
        }

        Object[] args = joinPoint.getArgs();
        double lat = (double) args[0];
        double lon = (double) args[1];

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return;


        String username = auth.getName();
        UserModel userModel = userMapper.toEntity(userService.getUserByUsername(username));

        if (userModel == null) return;

        emailService.sendEmail(username + "@gmail.com", "Weather Request", weatherDto.toString());

        userModel.setVersion(0L);

        RequestHistory requestHistory = new RequestHistory();
        requestHistory.setUser(userModel);
        requestHistory.setLat(lat);
        requestHistory.setLon(lon);
        requestHistory.setResponse(weatherDto.toString());

        userModel.addRequest(requestHistory);
        requestHistoryService.addRequestHistory(requestHistory);
    }
}
