package com.application.app.configs;

import com.application.app.services.UserServiceImpl;
import com.application.app.utils.JwtUtilToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final UserServiceImpl userServiceImpl;
    private final JwtUtilToken jwtTokenUtil;
    private final AuthEntryPoint unauthorizedHandler;

    public WebSecurityConfig(
            UserServiceImpl userServiceImpl,
            JwtUtilToken jwtTokenUtil,
            AuthEntryPoint unauthorizedHandler
    ){
        this.userServiceImpl = userServiceImpl;
        this.jwtTokenUtil = jwtTokenUtil;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authz) -> authz

                        .requestMatchers(
                                "/auth/*",
                                "/token/*",
                                "/v2/api-docs",
                                "/swagger-resources/**",
                                "/swagger-ui.html**",
                                "/webjars/**",
                                "/",
                                "/uploads/**",
                                "favicon.ico"
                        ).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

                return http.build();


    }

    @Bean
    public AuthenticationFilter authenticationTokenFilterBean() throws  Exception{
        return new AuthenticationFilter(userServiceImpl, jwtTokenUtil);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
