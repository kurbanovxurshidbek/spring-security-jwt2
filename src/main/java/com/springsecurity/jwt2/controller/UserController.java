package com.springsecurity.jwt2.controller;

import com.springsecurity.jwt2.model.dto.CustomResponse;
import com.springsecurity.jwt2.model.security.UserPrinciple;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class UserController {

    @GetMapping("/me")
    public CustomResponse userMe(@AuthenticationPrincipal UserPrinciple userPrinciple) { // #principle - 3
        return CustomResponse.ok("ONLY users can see this - me: " + userPrinciple.toString());
    }
}