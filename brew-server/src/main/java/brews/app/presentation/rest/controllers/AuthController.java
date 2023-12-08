package brews.app.presentation.rest.controllers;

import brews.app.security.jwt.JwtProvider;
import brews.app.security.jwt.request.LoginForm;
import brews.app.security.jwt.response.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 The Auth Api
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "API for authentication and user registration")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping(value = "/signin", consumes = {"*/*"})
    @ResponseBody
    @Operation(description = "Validates the users login credentials and returns the JWT token if ok")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getUsername(), new ArrayList<>(userDetails.getAuthorities()));

        log.debug("JwtResponse: {}", jwtResponse);

        return ResponseEntity.ok(jwtResponse);
    }

}
