package com.xpresspayments.xpress.services;

import com.xpresspayments.xpress.dtos.responses.UserResponse;
import com.xpresspayments.xpress.models.User;
import com.xpresspayments.xpress.repositories.UserRepository;
import com.xpresspayments.xpress.security.entities.SecureUser;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class XpressUserService implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse getUserBy(String username) {
        return modelMapper.map(getUserByUsername(username), UserResponse.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecureUser(getUserByUsername(username));
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Authentication Credentials"));
    }
}
