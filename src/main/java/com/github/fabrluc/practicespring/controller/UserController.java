package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.annotations.CustomHasAnyRole;
import com.github.fabrluc.practicespring.configuration.MaskMethodAuthorizationDeniedHandler;
import com.github.fabrluc.practicespring.dto.UserDTO;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    @GetMapping
    @CustomHasAnyRole({"'ADMIN'"})
    @HandleAuthorizationDenied(handlerClass= MaskMethodAuthorizationDeniedHandler.class)
    public List<UserDTO> getUsers() {
        return Arrays.asList(new UserDTO("1","John","john@email.com"),
                new UserDTO("2","Smith","smith@email.com"));
    }
}
