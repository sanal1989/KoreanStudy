package com.koreanstudy.repository;

import com.koreanstudy.entity.Role;
import com.koreanstudy.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(ERole role);

    boolean existsByRole(ERole roleAdmin);
}
