package com.springsecurity.jwt2.controller;

import com.springsecurity.jwt2.model.dto.CustomResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/me")
    public CustomResponse adminMe() {
        return CustomResponse.ok("ONLY admins can see this - me");
    }
}