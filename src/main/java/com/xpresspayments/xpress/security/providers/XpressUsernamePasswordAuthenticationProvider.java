package com.xpresspayments.xpress.security.providers;

import com.xpresspayments.xpress.exceptions.loginException.InvalidLoginDetailsException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class XpressUsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        boolean isValidPasswordMatch = passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword());
        if (isValidPasswordMatch)
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(),
                    userDetails.getAuthorities());
        throw new InvalidLoginDetailsException("Invalid Login Details");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
