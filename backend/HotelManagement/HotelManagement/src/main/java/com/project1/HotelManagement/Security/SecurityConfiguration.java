package com.project1.HotelManagement.Security;

import com.project1.HotelManagement.Filter.JwtFilter;
import com.project1.HotelManagement.Service.UserAccount.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserAccountService userAccountService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userAccountService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                configurer -> configurer
                        .requestMatchers(HttpMethod.GET, Endpoint.PUBLIC_GET_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.POST, Endpoint.PUBLIC_POST_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.PUT, Endpoint.PUBLIC_PUT_ENDPOINTS).hasAnyAuthority("CUSTOMER","ADMIN")
                        .requestMatchers(HttpMethod.GET, Endpoint.PRIVATE_GET_ENDPOINTS).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, Endpoint.PRIVATE_POST_ENDPOINTS).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, Endpoint.PRIVATE_PUT_ENDPOINTS).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, Endpoint.PRIVATE_DELETE_ENDPOINTS).hasAuthority("ADMIN")
        );
        http.cors(cors ->{
            cors.configurationSource(request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedOrigin("*");
                corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                corsConfiguration.addAllowedHeader("*");
                return corsConfiguration;
            });
        });
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf->csrf.disable());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
