package com.project.resource.dao;

import java.util.List;

import com.project.resource.entity.Resource;
import com.project.role.entity.Role;

public interface ResourceMapper {
	List<Resource> selectAll();

	List<Resource> getMenubyRoleId(long roleId);
}
