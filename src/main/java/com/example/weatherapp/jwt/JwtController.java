package com.example.weatherapp.jwt;

import com.example.weatherapp.exception.PasswordIsNullException;
import com.example.weatherapp.exception.UsernameAlreadyExistsException;
import com.example.weatherapp.exception.UsernameIsNullException;
import com.example.weatherapp.jwt.model.JwtRequestModel;
import com.example.weatherapp.jwt.model.JwtResponseModel;
import com.example.weatherapp.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {

    private final JwtUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil tokenManager;



    public JwtController(JwtUserDetailsService userDetailsService, JwtUtil tokenManager, AuthenticationManager authenticationManager) {
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

        final String jwtToken = tokenManager.generateJWTToken(userDetails);


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
    public ResponseEntity<String> register(@RequestBody UserModel request) {
        try {
            userDetailsService.saveUser(request);
        } catch (UsernameAlreadyExistsException | UsernameIsNullException | PasswordIsNullException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }

        return ResponseEntity.ok("User registered successfully");
    }
}
