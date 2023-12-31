package com.minas.market.integration.webapi.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minas.market.application.service.security.JwtService;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.infrastructure.persistence.entity.enums.TypeUser;
import com.minas.market.infrastructure.persistence.entity.security.*;
import com.minas.market.infrastructure.persistence.repository.AnnouncementRepository;
import com.minas.market.infrastructure.persistence.repository.security.RoleRepository;
import com.minas.market.infrastructure.persistence.repository.security.TokenRepository;
import com.minas.market.infrastructure.persistence.repository.security.UserRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.*;

import static org.jeasy.random.FieldPredicates.named;

public class TestHelper {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private void inicializaRoles() {
        try {
            Field[] roles = ConstRoles.class.getDeclaredFields();
            for (int i = 0; i < roles.length; i++) {
                if (roleRepository.findByName(roles[i].getName()) == null) {
                    String role = roles[i].getName();
                    Modulo modulo = Modulo.valueOf(role.split("_")[1]);
                    Role rol;

                    if (role.split("_").length > 2) {
                        ModuloAcao moduloAcao = ModuloAcao.valueOf(role.split("_")[2]);
                        rol = new Role(role, modulo, moduloAcao);
                    } else {
                        rol = new Role(role, modulo);
                    }

                    roleRepository.save(rol);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UUID createUser(UUID userId) {
        inicializaRoles();
        Optional<User> userRepositoryByEmail = userRepository.findByEmail("test@hotmail.com");
        if (userRepositoryByEmail.isPresent()) {
            return userRepositoryByEmail.get().getId();
        } else {
            User user = User.builder()
                    .id(userId)
                    .firstname("Test")
                    .lastname("Test")
                    .roles(List.of(roleRepository.getOneRoleByName(ConstRoles.ROLE_ADMIN_ADMIN), roleRepository.getOneRoleByName(ConstRoles.ROLE_PJ_GET)))
                    .password(passwordEncoder.encode("TEST"))
                    .email("test@hotmail.com")
                    .type(TypeUser.PF)
                    .build();

            var jwtToken = jwtService.generateToken(user);
            jwtService.generateRefreshToken(user);
            userRepository.save(user);
            saveUserToken(user, jwtToken);
            return user.getId();
        }
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

    public HttpHeaders getAuthorization(UUID userId){
        Token token = tokenRepository.findAllValidTokenByUser(userId).stream().findFirst().orElse(null);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer ".concat(token.getToken()));
        return httpHeaders;
    }

    public UUID createAnnouncement(UUID userId) {
        AnnouncementEntity announcementEntity = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("userId"), () -> userId)
                        .randomize(named("description"), () -> "test")
                        .excludeField(named("id"))
                        .excludeField(named("createdBy"))
                        .excludeField(named("createdDate"))
                        .excludeField(named("lastModifiedBy"))
                        .excludeField(named("lastModifiedDate"))
                        .excludeField(named("images"))
                        .excludeField(named("messages"))
        ).nextObject(AnnouncementEntity.class);
        return announcementRepository.save(announcementEntity).getId();
    }

    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void afterHelper() {
        announcementRepository.deleteAll();
    }
}
