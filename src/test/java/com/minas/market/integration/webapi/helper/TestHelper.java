package com.minas.market.integration.webapi.helper;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import static org.jeasy.random.FieldPredicates.named;

public abstract class TestHelper {
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

    public UUID createUser() {
        inicializaRoles();
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            User user = User.builder()
                    .id(UUID.randomUUID())
                    .firstname("Test")
                    .lastname("Test")
                    .roles(List.of(roleRepository.getOneRoleByName(ConstRoles.ROLE_ADMIN_ADMIN), roleRepository.getOneRoleByName(ConstRoles.ROLE_PJ_GET)))
                    .password(passwordEncoder.encode("TEST"))
                    .email("test@hotmail.com")
                    .type(TypeUser.PF)
                    .build();

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            userRepository.save(user);
            saveUserToken(user, jwtToken);
            return user.getId();
        }
        return users.get(0).getId();
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

    public UUID createAnnouncement(UUID userId) {
        AnnouncementEntity announcementEntity = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("userId"), () -> userId)
                        .excludeField(named("id"))
                        .excludeField(named("createdBy"))
                        .excludeField(named("createdDate"))
                        .excludeField(named("lastModifiedBy"))
                        .excludeField(named("lastModifiedDate"))
                        .excludeField(named("images"))
        ).nextObject(AnnouncementEntity.class);
        return announcementRepository.save(announcementEntity).getId();
    }
}