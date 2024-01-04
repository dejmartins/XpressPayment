package com.xpresspayments.xpress.security.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Set;

@RequiredArgsConstructor
public class XpressAuthenticationManager implements AuthenticationManager {
    private final Set<AuthenticationProvider> providers;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationProvider provider = getProviderFor(authentication);
        return provider.authenticate(authentication);
    }

    private AuthenticationProvider getProviderFor(Authentication authentication) {
        return providers.stream()
                .filter(authenticationProvider -> authenticationProvider.supports(authentication.getClass()))
                .findAny()
                .orElseThrow(() -> new ProviderNotFoundException("No authentication provider exists for authentication type"));
    }
}
