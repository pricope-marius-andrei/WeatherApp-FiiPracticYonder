package com.example.weatherapp.controller;

import com.example.weatherapp.exception.PasswordIsNullException;
import com.example.weatherapp.exception.UsernameAlreadyExistsException;
import com.example.weatherapp.exception.UsernameIsNullException;
import com.example.weatherapp.jwt.JwtUtil;
import com.example.weatherapp.jwt.model.JwtRequestModel;
import com.example.weatherapp.jwt.model.JwtResponseModel;
import com.example.weatherapp.jwt.model.UserRegisterModel;
import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.model.UserProfileModel;
import com.example.weatherapp.service.JwtUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final JwtUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil tokenManager;

    private final Logger logger = Logger.getLogger(AuthController.class.getName());



    public AuthController(JwtUserDetailsService userDetailsService, JwtUtil tokenManager, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.tokenManager = tokenManager;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseModel> createToken(@RequestBody JwtRequestModel
                                                                request) throws Exception {

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new JwtResponseModel("User not found"));
        }

        logger.info("User found: " + userDetails.getUsername());

        final String jwtToken = tokenManager.generateJWTToken(userDetails);

        logger.info("The JWT Token was generated!");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

      return ResponseEntity.ok(new JwtResponseModel(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRegisterModel request) {
        try {

            UserModel user = new UserModel();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());

            if(user.getUserProfile() == null)
            {
                user.setUserProfile(new UserProfileModel());
            }

            user.getUserProfile().setEmail(request.getEmail());

            userDetailsService.saveUser(user);
        } catch (UsernameAlreadyExistsException | UsernameIsNullException | PasswordIsNullException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }

        return ResponseEntity.ok("User registered successfully");
    }
}
