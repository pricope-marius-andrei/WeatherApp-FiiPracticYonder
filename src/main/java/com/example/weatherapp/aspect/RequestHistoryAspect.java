package com.example.weatherapp.aspect;

import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.mapper.UserMapper;
import com.example.weatherapp.model.RequestHistory;
import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.service.interfaces.EmailService;
import com.example.weatherapp.service.interfaces.RequestHistoryService;
import com.example.weatherapp.service.interfaces.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(RequestHistoryAspect.class);

    public RequestHistoryAspect(RequestHistoryService requestHistoryService, EmailService emailService, UserService userService, UserMapper userMapper) {
        this.requestHistoryService = requestHistoryService;
        this.emailService = emailService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    private enum RequestType {
        COORDINATES, LOCATION_STRING, UNKNOWN
    }

    private RequestType getRequestType(Object[] args) {
        if (args.length == 2 && args[0] instanceof Double && args[1] instanceof Double) {
            return RequestType.COORDINATES;
        } else if (args.length == 1 && args[0] instanceof String) {
            return RequestType.LOCATION_STRING;
        } else {
            return RequestType.UNKNOWN;
        }
    }

    @Around("@annotation(com.example.weatherapp.controller.annotation.LogRequestHistory)")
    public Object logRequestHistory(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Logging request history...");

        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("Error during method execution", e);
            throw e;
        }

        if (!(result instanceof WeatherDto weatherDto)) {
            return result;
        }

        //I applied this aspect to the method getWeatherDetails and getWeatherDetailsByLocation,
        //so I need to check the structure of the args
        double lat = 0.0, lon = 0.0;
        String location = null;
        Object[] args = joinPoint.getArgs();

        RequestType requestType = getRequestType(args);

        switch (requestType) {
            case COORDINATES -> {
                lat = (Double) args[0];
                lon = (Double) args[1];
            }
            case LOCATION_STRING -> {
                location = (String) args[0];
            }
            case UNKNOWN -> {
                logger.warn("Unknown request type");

            }
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null) {
            logger.warn("Authentication is null");
            throw new IllegalStateException("Authentication is null");
        }

        if (!auth.isAuthenticated()) {
            logger.warn("Authentication is not authenticated");
            throw new IllegalStateException("Authentication is not authenticated");
        }

        String username = auth.getName();

        UserDto user = userService.getUserByUsername(username);

        UserModel userModel = userMapper.toEntity(user);


        if (userModel == null) {
            logger.error("User model is null");
            throw new IllegalStateException("User model is null");
        }

        emailService.sendEmail(username + "@gmail.com", "Weather Request", weatherDto.toString());

        userModel.setVersion(0L); //
        RequestHistory requestHistory = new RequestHistory();
        requestHistory.setUser(userModel);
        requestHistory.setLat(lat);
        requestHistory.setLon(lon);
        requestHistory.setLocation(location);
        requestHistory.setResponse(weatherDto.toString());

        userModel.addRequest(requestHistory);
        requestHistoryService.addRequestHistory(requestHistory);

        return result;
    }
}
