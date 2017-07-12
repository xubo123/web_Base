package com.project.role.dao;

import java.util.List;
import java.util.Map;

import com.project.role.entity.Role;

public interface RoleMapper {
       List<Role> getAllRole();

	long countRole(Map<String, Object> map);

	List<Role> selectRoleList(Map<String, Object> map);

	void save(Role role);

	Role getRoleByRoleId(long roleId);

	void update(Role role);

	void delete(long roleId);
}
