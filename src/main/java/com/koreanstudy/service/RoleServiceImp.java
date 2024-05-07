package com.koreanstudy.service;

import com.koreanstudy.entity.Role;
import com.koreanstudy.entity.enums.ERole;
import com.koreanstudy.repository.RoleRepository;
import com.koreanstudy.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> saveRole(Role role) {
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        return null;
    }

    @Override
    public List<Role> deleteRoleById(Long id) {
        return null;
    }

    @Override
    public Role getRoleById(Long id) {
        return null;
    }

    public Role findByName(ERole role) {
        return roleRepository.findByRole(role);
    }
}
