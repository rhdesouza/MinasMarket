package com.minas.market.infrastructure.persistence.entity.security;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.minas.market.infrastructure.persistence.entity.enums.TypeUser;
import com.minas.market.infrastructure.persistence.entity.AddressEntity;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    private UUID id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeUser type;
    private String cpf;
    @Column(name = "birth_year")
    private Integer birthYear;
    private String cnpj;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "fantasy_name")
    private String fantasyName;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private List<AddressEntity> address;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private List<AnnouncementEntity> announcement;

    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private List<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
    }
}
