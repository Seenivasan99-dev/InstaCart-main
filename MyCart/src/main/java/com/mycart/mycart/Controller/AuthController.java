package com.mycart.mycart.Controller;

import com.mycart.mycart.Request.Login;
import com.mycart.mycart.Response.AuthResponse;
import com.mycart.mycart.Response.JwtResponse;
import com.mycart.mycart.Security.Jwt.JwtUtils;
import com.mycart.mycart.Security.User.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenicationManager;


    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid  @RequestBody Login login){
        try {
            Authentication auth = authenicationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwt = jwtUtils.generateTokenForUser(auth);
            ShopUserDetails shopUserDetails = (ShopUserDetails) auth.getPrincipal();
            JwtResponse jwtresponse = new JwtResponse(shopUserDetails.getId(), jwt);
            return ResponseEntity.ok(new AuthResponse("LoginSucessfully", jwtresponse));
        }
        catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new AuthResponse("LoginFailed", e.getMessage()));
        }

    }
}
