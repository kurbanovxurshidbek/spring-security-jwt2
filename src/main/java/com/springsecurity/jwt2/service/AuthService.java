package com.springsecurity.jwt2.service;


import com.springsecurity.jwt2.model.dto.CustomResponse;
import com.springsecurity.jwt2.model.dto.SignInRequest;
import com.springsecurity.jwt2.model.dto.SignUpRequest;
import com.springsecurity.jwt2.model.entity.Role;
import com.springsecurity.jwt2.model.entity.User;
import com.springsecurity.jwt2.model.security.UserPrinciple;
import com.springsecurity.jwt2.repository.RoleRepository;
import com.springsecurity.jwt2.repository.UserRepository;
import com.springsecurity.jwt2.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public CustomResponse signup(SignUpRequest request) {
        var firstName = request.getFirstName();
        var lastName = request.getLastName();
        var email = request.getEmail();
        var password = passwordEncoder.encode(request.getPassword());

        var user = new User(firstName, lastName, email, password);

        var roleIds = request.getRoleIds();
        for (Long roleId : roleIds) {
            var role = roleRepository.findById(roleId).orElseThrow();
            user.getRoles().add(role);
        }

        userService.save(user);
        //var jwt = jwtProvider.generateToken(user);
        return CustomResponse.ok("Signed up successfully");
    }

    public CustomResponse signin(SignInRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = userRepository.findByEmail(request.getEmail()).map(UserPrinciple::from).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
            //var user = (User) authenticate.getPrincipal();
            var jwt = jwtProvider.generateToken(user);

            return CustomResponse.ok(jwt);
        } catch (BadCredentialsException e) {
            return CustomResponse.error(e.getMessage());
        }
    }
}
