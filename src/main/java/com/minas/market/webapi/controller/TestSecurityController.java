package com.minas.market.webapi.controller;

import com.minas.market.infrastructure.persistence.entity.security.ConstRoles;
import com.minas.market.infrastructure.persistence.entity.security.User;
import com.minas.market.infrastructure.persistence.repository.security.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/controller")
@RequiredArgsConstructor
public class TestSecurityController {

    //TODO: Apagar essa classe após os testes de security

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN_ADMIN')")
    @Secured({ConstRoles.ROLE_ADMIN_ADMIN})
    public String post() {
        return "POST:: admin controller";
    }

    @GetMapping("/pj")
    //@PreAuthorize("hasRole('ROLE_PJ_POST')")
    @Secured({ConstRoles.ROLE_PJ_POST})
    public String postAll() {
        return "POST:: All controller";
    }

    @GetMapping("/all")
    @ResponseBody
    @Transactional
    public List<? extends GrantedAuthority> posts() {
        User principal = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities().stream().toList();

    }

}
