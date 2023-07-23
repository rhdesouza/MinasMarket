package com.minas.market.infrastructure.config;

import com.minas.market.application.service.security.JwtService;
import com.minas.market.infrastructure.persistence.entity.enums.TypeUser;
import com.minas.market.infrastructure.persistence.entity.security.*;
import com.minas.market.infrastructure.persistence.repository.security.RoleRepository;
import com.minas.market.infrastructure.persistence.repository.security.TokenRepository;
import com.minas.market.infrastructure.persistence.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = Logger.getLogger(DataInitializr.class.getName());

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        this.inicializaRoles();
        this.initialUserAdmin();
    }

    /**
     * Inicializa as roles conforme arquivo de Constantes.
     */
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
            LOGGER.log(Level.SEVERE, "Erro ao verificar/criar ROLES. Error: {0}", e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Inicializa o acesso Admin.
     */
    private void initialUserAdmin() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {

            User user = User.builder()
                    .id(UUID.fromString("96f95386-8a3e-43f7-a38f-ab325bfb77ae"))
                    .firstname("Rafael")
                    .lastname("Henrique")
                    .roles(List.of(roleRepository.getOneRoleByName(ConstRoles.ROLE_ADMIN_ADMIN), roleRepository.getOneRoleByName(ConstRoles.ROLE_PJ_GET)))
                    .password(passwordEncoder.encode("SENHA"))
                    .email("rhdesouza@hotmail.com")
                    .type(TypeUser.PF)
                    .build();

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            userRepository.save(user);

            saveUserToken(user, jwtToken);

            System.out.println("Admin token: " + jwtToken);
            System.out.println("Admin Id: " + UUID.fromString("96f95386-8a3e-43f7-a38f-ab325bfb77ae"));
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

}
