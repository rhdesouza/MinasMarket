package com.minas.market.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minas.market.application.service.security.JwtService;
import com.minas.market.domain.interfaces.AuthenticationService;
import com.minas.market.infrastructure.mapper.AuthenticationMapper;
import com.minas.market.infrastructure.mapper.UserMapper;
import com.minas.market.infrastructure.persistence.entity.security.Role;
import com.minas.market.infrastructure.persistence.entity.security.Token;
import com.minas.market.infrastructure.persistence.entity.security.TokenType;
import com.minas.market.infrastructure.persistence.entity.security.User;
import com.minas.market.infrastructure.persistence.repository.security.RoleRepository;
import com.minas.market.infrastructure.persistence.repository.security.TokenRepository;
import com.minas.market.infrastructure.persistence.repository.security.UserRepository;
import com.minas.market.webapi.dto.AuthenticationRequest;
import com.minas.market.webapi.dto.AuthenticationResponse;
import com.minas.market.webapi.dto.request.RegisterUserRequest;
import com.minas.market.webapi.dto.request.UserDTO;
import com.minas.market.webapi.exception.BusinessRuleException;
import com.minas.market.application.service.security.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImp userServiceImp;
    private final UserMapper userMapper;
    private final AuthenticationMapper authenticationMapper;

    @Override
    public AuthenticationResponse register(RegisterUserRequest registerUserRequest) {
        UserDTO userRequest = registerUserRequest.getUser();
        List<Role> roles = roleRepository.findAllById(new ArrayList<>(userRequest.getRoleId()));
        User user = userMapper.toEntity(userRequest, roles, registerUserRequest.getAddress());

        if (Boolean.TRUE.equals(userServiceImp.isUserExists(user))) {
            throw new BusinessRuleException("Usuário ja cadastrado");
        }

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User savedUser = userServiceImp.saved(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);
        return authenticationMapper.toAuthenticationResponse(jwtToken, refreshToken);

        // TODO: Incluir a service para salvar o endereço no banco.
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return authenticationMapper.toAuthenticationResponse(jwtToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = authenticationMapper.toAuthenticationResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
