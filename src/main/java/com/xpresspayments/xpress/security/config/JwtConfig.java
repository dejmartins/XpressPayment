package com.xpresspayments.xpress.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.signing.key}")
    private String jwtSecretKey;
    @Value("${jwt.expiry.days}")
    private String jwtDuration;


    public String getJwtSecret(){
        return jwtSecretKey;
    }
    public int getJwtDuration(){
        return Integer.parseInt(jwtDuration);
    }
}
