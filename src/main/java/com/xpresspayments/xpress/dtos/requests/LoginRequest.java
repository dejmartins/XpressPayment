package com.xpresspayments.xpress.dtos.requests;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
