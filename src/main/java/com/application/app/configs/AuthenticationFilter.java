package com.application.app.configs;

import com.application.app.utils.JwtUtilToken;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

import static com.application.app.utils.Constants.*;

public class AuthenticationFilter extends OncePerRequestFilter {

    //@Autowired
    private final UserDetailsService userDetailsService;

    //@Autowired
    private final JwtUtilToken jwtTokenUtil;

    public AuthenticationFilter(UserDetailsService userDetailsService, JwtUtilToken jwtTokenUtil){
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;

        if(header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");

            try{
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error(JWT_ILLEGAL_ARGUMENT_MESSAGE, e);
            } catch (ExpiredJwtException e) {
                logger.warn(JWT_EXPIRED_MESSAGE, e);
            }

        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            boolean isValidToken = jwtTokenUtil.validateToken(authToken, userDetails);

            if (Boolean.TRUE.equals(isValidToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, "", userDetails.getAuthorities()
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                logger.info("authenticated user " + username);


                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }

        filterChain.doFilter(request, response);


    }










}
