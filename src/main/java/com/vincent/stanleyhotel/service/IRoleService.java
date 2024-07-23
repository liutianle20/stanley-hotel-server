package com.vincent.stanleyhotel.service;

import com.vincent.stanleyhotel.model.Role;
import com.vincent.stanleyhotel.model.User;

import java.util.List;

public interface IRoleService {
    List<Role> getRoles();
    Role createRole(Role role);
    void deleteRole(Long id);
    Role findRoleByName(String name);
    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}
