package com.project.role.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.base.action.AdminBaseAction;
import com.project.base.action.Message;
import com.project.login.action.LoginAction;
import com.project.role.entity.Role;
import com.project.role.service.RoleService;

public class RoleAction extends AdminBaseAction{
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	@Autowired
	RoleService roleService;
	Role role;
	long roleId;
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
				return role;
			}

			public void setRole(Role role) {
				this.role = role;
			}

	public void getAllRole(){
		List<Role> roles = roleService.getAllRole();
		super.writeJson(roleService.getAllRole());
	}
	
	public void dataGrid(){
//		String userAccount = getRequest().getParameter("userAccount");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
//		map.put("userAccount", userAccount);
		super.writeJson(roleService.dataGrid(map));
	}
	public void save(){
		Message message = new Message();
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleName", role.getRoleName());
			map.put("systemAdmin", 2);
			long count = roleService.countByRoleName(map);
			if (count > 0)
			{
				message.setMsg("该机构已经注册");
				message.setSuccess(false);
			} else
			{
				
				roleService.save(role);
				message.setMsg("新增成功");
				message.setSuccess(true);
			}
		} catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("新增失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	public void update(){
		Message message = new Message();
		try
		{
			roleService.update(role);
				message.setMsg("更新成功");
				message.setSuccess(true);
			
		} catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("更新失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	public void delete(){
		Message message = new Message();
		if(roleId!=0){
			roleService.delete(roleId);
			message.setMsg("删除成功");
			message.setSuccess(true);
			super.writeJson(message);
		}else{
			
			message.setMsg("删除失败");
			message.setSuccess(false);
			super.writeJson(message);
		}
	}
	public void getRoleByRoleId(){
		if(role.getRoleId()!=0){
			Role r = roleService.getRoleByRoleId(role.getRoleId());
			super.writeJson(roleService.getRoleByRoleId(role.getRoleId()));
		}else{
			Message message = new Message();
			message.setMsg("未找到角色，roleId为空");
			message.setSuccess(false);
			super.writeJson(message);
		}
	}
}
