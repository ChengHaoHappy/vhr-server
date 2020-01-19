package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.RoleMapper;
import org.javaboy.vhr.model.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.InsufficientResourcesException;
import java.util.List;

/**
 * @author ：HappyCheng
 * @date ：2019/10/26
 */
@Service
public class RoleService {
    @Resource
    RoleMapper roleMapper;
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insert(role);
    }

    public Integer delRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
