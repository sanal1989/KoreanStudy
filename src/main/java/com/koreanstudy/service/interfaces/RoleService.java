package com.koreanstudy.service.interfaces;

import com.koreanstudy.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> saveRole(Role role);

    List<Role> getAllRoles();

    List<Role> deleteRoleById(Long id);

    Role getRoleById(Long id);
}
