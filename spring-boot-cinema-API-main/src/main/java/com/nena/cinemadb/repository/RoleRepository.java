package com.nena.cinemadb.repository;

import com.nena.cinemadb.model.ERole;
import com.nena.cinemadb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
