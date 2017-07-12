package com.project.role.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.base.entity.DataGrid;
import com.project.role.dao.RoleMapper;
import com.project.role.entity.Role;
import com.project.user.entity.User;
@Service("RoleService")
public class RoleServiceImpl implements RoleService{
    @Autowired
	RoleMapper roleMapper;
	@Override
	public List<Role> getAllRole() {
		// TODO Auto-generated method stub
		return roleMapper.getAllRole();
	}
	@Override
	public DataGrid<Role> dataGrid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		DataGrid<Role> dataGrid = new DataGrid<Role>();
		long count = roleMapper.countRole(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Role> list = roleMapper.selectRoleList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}
	@Override
	public long countByRoleName(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return roleMapper.countRole(map);
	}
	@Override
	public void save(Role role) {
		// TODO Auto-generated method stub
		roleMapper.save(role);
		
	}
	@Override
	public Role getRoleByRoleId(long roleId) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleByRoleId(roleId);
	}
	@Override
	public void update(Role role) {
		// TODO Auto-generated method stub
		roleMapper.update(role);
	}
	@Override
	public void delete(long roleId) {
		// TODO Auto-generated method stub
		roleMapper.delete(roleId);
	}

}
