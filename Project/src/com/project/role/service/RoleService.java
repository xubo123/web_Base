package com.project.role.service;

import java.util.List;
import java.util.Map;

import com.project.base.entity.DataGrid;
import com.project.role.entity.Role;
import com.project.user.entity.User;


public interface RoleService {
       List<Role> getAllRole();

   	DataGrid<Role> dataGrid(Map<String, Object> map);

	long countByRoleName(Map<String, Object> map);

	void save(Role role);

	Role getRoleByRoleId(long roleId);

	void update(Role role);

	void delete(long roleId);
}
