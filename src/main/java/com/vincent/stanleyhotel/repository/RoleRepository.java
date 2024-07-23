package com.vincent.stanleyhotel.repository;

import com.vincent.stanleyhotel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String roleName);

    boolean existsByName(String roleName);
}
