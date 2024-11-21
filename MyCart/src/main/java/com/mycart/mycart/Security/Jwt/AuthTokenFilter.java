package com.mycart.mycart.Security.Jwt;

import com.mycart.mycart.Security.User.ShopUserDetails;
import com.mycart.mycart.Security.User.ShopUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtutils;

    @Autowired
    private ShopUserDetailsService shopUserDetailsservice;

    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String Jwt = ParseJwt(request);
            if (StringUtils.hasText(Jwt) && jwtutils.validateToken(Jwt)) {
                String username = jwtutils.getUsernameFromToken(Jwt);
                UserDetails userdetails = shopUserDetailsservice.loadUserByUsername(username);
                Authentication auth = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        catch (JwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            response.getWriter().write(e.getMessage()+"Inavlid Credentials or Expired");
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }
    private String ParseJwt(HttpServletRequest request) {
        String token=request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return "";
    }
}
