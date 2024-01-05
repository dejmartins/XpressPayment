package com.xpresspayments.xpress.services;

import com.xpresspayments.xpress.dtos.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @Sql(scripts = {"/db/insert.sql"})
    void testGetUser(){
        UserResponse user = userService.getUserBy("john@email.com");
        assertThat(user).isNotNull();
    }

}