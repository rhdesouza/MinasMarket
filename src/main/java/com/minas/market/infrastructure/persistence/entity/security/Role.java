package com.minas.market.infrastructure.persistence.entity.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_role")
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modulo modulo;

    @Enumerated(EnumType.STRING)
    private ModuloAcao acao;

    public Role(String name, Modulo modulo, ModuloAcao acao) {
        super();
        this.name = name;
        this.modulo = modulo;
        this.acao = acao;
    }

    public Role(String name, Modulo modulo) {
        super();
        this.name = name;
        this.modulo = modulo;
    }

    public Role() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    public ModuloAcao getAcao() {
        return acao;
    }

    public void setAcao(ModuloAcao acao) {
        this.acao = acao;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
