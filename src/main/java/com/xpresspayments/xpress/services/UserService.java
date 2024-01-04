package com.xpresspayments.xpress.services;

import com.xpresspayments.xpress.dtos.responses.UserResponse;

public interface UserService {

    UserResponse getUserBy(String username);
}
