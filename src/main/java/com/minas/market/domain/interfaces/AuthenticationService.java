package com.minas.market.domain.interfaces;

import com.minas.market.webapi.dto.AuthenticationRequest;
import com.minas.market.webapi.dto.AuthenticationResponse;
import com.minas.market.webapi.dto.request.RegisterUserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterUserRequest registerUserRequest);

    public AuthenticationResponse authenticate(AuthenticationRequest request);

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
