package com.minas.market.webapi.controller;

import com.minas.market.webapi.contract.AutenticationAPI;
import com.minas.market.webapi.dto.request.RegisterUserRequest;
import com.minas.market.domain.interfaces.AuthenticationService;
import com.minas.market.webapi.dto.AuthenticationRequest;
import com.minas.market.webapi.dto.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AuthenticationController implements AutenticationAPI {

    private final AuthenticationService service;

    @Override
    public ResponseEntity<AuthenticationResponse> register(RegisterUserRequest registerUserRequest) {
        return ResponseEntity.ok(service.register(registerUserRequest));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
