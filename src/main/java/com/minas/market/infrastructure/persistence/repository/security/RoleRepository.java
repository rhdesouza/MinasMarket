package com.minas.market.infrastructure.persistence.repository.security;

import com.minas.market.infrastructure.persistence.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  Role findByName(String name);

  @Query("SELECT u FROM Role u WHERE u.name = :name")
  Role getOneRoleByName(@Param(value = "name") String name);

}
