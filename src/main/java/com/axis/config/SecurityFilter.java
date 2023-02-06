package com.axis.config;

import com.axis.service.ManagerSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private ManagerSecurityService managerSecurityService;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader =request.getHeader("Authorization");
        String username =null;
        String token = null;

        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            token =tokenHeader.substring(7);
            try {
                username =jwtUtil.getUsernameFromToken(token);

            }catch (Exception e){
                System.out.println("Forbidden");
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = managerSecurityService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }else {
                System.out.println("Not Validated");
            }
        }

        filterChain.doFilter(request,response);
    }
}
