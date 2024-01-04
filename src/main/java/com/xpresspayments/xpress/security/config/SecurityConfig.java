package com.xpresspayments.xpress.security.config;

import com.xpresspayments.xpress.security.filters.JwtAuthorizationFilter;
import com.xpresspayments.xpress.security.filters.XpressUsernamePasswordAuthenticationFilter;
import com.xpresspayments.xpress.security.manager.XpressAuthenticationManager;
import com.xpresspayments.xpress.security.providers.XpressUsernamePasswordAuthenticationProvider;
import com.xpresspayments.xpress.security.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(){
        Set<AuthenticationProvider> providers = Set.of(new XpressUsernamePasswordAuthenticationProvider(userDetailsService, passwordEncoder()));
        return new XpressAuthenticationManager(providers);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(c-> c.sessionCreationPolicy(STATELESS))
                .addFilterAt(new XpressUsernamePasswordAuthenticationFilter(authenticationManager(), jwtService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtService), XpressUsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c -> c.requestMatchers("/login").permitAll())
                .authorizeHttpRequests(c -> c.anyRequest().authenticated())
                .build();
    }
}
