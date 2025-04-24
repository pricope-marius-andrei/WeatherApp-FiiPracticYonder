package com.example.weatherapp.aspect;

import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.mapper.UserMapper;
import com.example.weatherapp.model.RequestHistoryModel;
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


    @Around("@annotation(com.example.weatherapp.controller.annotation.LogRequestHistory)")
    public Object logRequestHistory(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Logging request history...");
        
        Object result = executeTargetMethod(joinPoint);

        if (!(result instanceof WeatherDto weatherDto)) {
            return result;
        }

        RequestDetails requestDetails = extractRequestDetails(joinPoint.getArgs());
        UserModel userModel = getAuthenticatedUser();

        sendEmailNotification(weatherDto, userModel);
        logRequest(weatherDto, userModel, requestDetails);

        return result;
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

    private enum RequestType {
        COORDINATES, LOCATION_STRING, UNKNOWN
    }

    private void logRequest(WeatherDto weatherDto, UserModel userModel, RequestDetails requestDetails) {
        userModel.setVersion(0L);
        RequestHistoryModel requestHistoryModel = new RequestHistoryModel();
        requestHistoryModel.setUser(userModel);
        requestHistoryModel.setLat(requestDetails.lat());
        requestHistoryModel.setLon(requestDetails.lon());
        requestHistoryModel.setLocation(requestDetails.location());
        requestHistoryModel.setResponse(weatherDto.toString());
        requestHistoryModel.setAlerts(false);
        requestHistoryModel.setDays(0);
        requestHistoryModel.setQ(true);
        requestHistoryModel.setAqi(true);

        userModel.addRequest(requestHistoryModel);
        requestHistoryService.addRequestHistory(requestHistoryModel);
    }

    private void sendEmailNotification(WeatherDto weatherDto, UserModel userModel) {
        if (Boolean.TRUE.equals(userModel.getUserProfile().getEmailNotification())) {
            String email = userModel.getUsername() + "@gmail.com";
            emailService.sendEmail(email, "Weather Request", weatherDto.toString());
        } else {
            logger.info("User {} has disabled email notifications", userModel.getUsername());
        }
    }

    private RequestDetails extractRequestDetails(Object[] args) {

        RequestType requestType = getRequestType(args);
        return switch (requestType) {
            case COORDINATES -> {
                double lat = (Double) args[0];
                double lon = (Double) args[1];

                yield new RequestDetails(lat, lon, null);
            }
            case LOCATION_STRING -> new RequestDetails(0.0, 0.0, (String) args[0]);
            case UNKNOWN -> {
                logger.warn("Unknown request type");
                throw new IllegalArgumentException("Unknown request type");
            }
        };
    }

    private record RequestDetails(double lat, double lon, String location) {
    }

    private static Object executeTargetMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            logger.error("Error during method execution", e);
            throw e;
        }
    }

    private UserModel getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        String username = auth.getName();
        UserDto userDto = userService.getUserByUsername(username);
        UserModel userModel = userMapper.toEntity(userDto);

        if (userModel == null) {
            throw new IllegalStateException("Mapped user model is null");
        }

        return userModel;
    }

}
