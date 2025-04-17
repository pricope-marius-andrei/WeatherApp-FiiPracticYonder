package com.example.weatherapp.aspect;

import com.example.weatherapp.dto.UserDto;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class RequestHistoryAspect {
    private final RequestHistoryService requestHistoryService;
    private final EmailService emailService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final Logger logger = Logger.getLogger(RequestHistoryAspect.class.getName());

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

    @AfterReturning(
            pointcut = "@annotation(com.example.weatherapp.controller.annotation.LogRequestHistory)",
            returning = "response"
    )
    public void logRequestHistory(JoinPoint joinPoint, Object response) {
        logger.info("Logging request history...");
        if (!(response instanceof WeatherDto weatherDto)) {
            return;
        }

        logger.info("Response: " + weatherDto);

        Object[] args = joinPoint.getArgs();

        //I applied this aspect to the method getWeatherDetails and getWeatherDetailsByLocation,
        //so I need to check the structure of the args
        double lat = 0.0, lon = 0.0;
        String location = null;



        RequestType requestType = getRequestType(args);

        logger.info("Request type: " + requestType);

        switch (requestType) {
            case COORDINATES -> {
                lat = (Double) args[0];
                lon = (Double) args[1];
                logger.info("Coordinates: " + lat + ", " + lon);
            }
            case LOCATION_STRING -> {
                location = (String) args[0];
                logger.info("Location: " + location);
            }
            case UNKNOWN -> {
                return; // Don't handle unknown input structure
            }
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null) {
            logger.warning("Authentication is null");
            return;
        }

        if (!auth.isAuthenticated()) {
            logger.warning("Authentication is not authenticated");
            return;
        }


        try {
            String username = auth.getName();
            logger.info("Authenticated user: " + username);

            UserDto user = userService.getUserByUsername(username);
            logger.info("Fetched user from service: " + user);

            user.getRequestHistories().size(); // triggers lazy load while Hibernate session is alive

            UserModel userModel = userMapper.toEntity(user);
            logger.info("Mapped user model: " + userModel);


            if (userModel == null) return;

            emailService.sendEmail(username + "@gmail.com", "Weather Request", weatherDto.toString());

            userModel.setVersion(0L);

            RequestHistory requestHistory = new RequestHistory();
            requestHistory.setUser(userModel);
            requestHistory.setLat(lat);
            requestHistory.setLon(lon);
            requestHistory.setLocation(location);
            requestHistory.setResponse(weatherDto.toString());

            userModel.addRequest(requestHistory);
            requestHistoryService.addRequestHistory(requestHistory);
            logger.info("Request history saved for user: " + username);
        }
        catch (Exception e) {
            logger.warning("Error while logging request history: " + e.getMessage());
            return;
        }

    }
}
