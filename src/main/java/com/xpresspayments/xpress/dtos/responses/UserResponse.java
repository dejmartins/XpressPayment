package com.xpresspayments.xpress.dtos.responses;

import com.xpresspayments.xpress.models.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private Set<Authority> authorities;
}
