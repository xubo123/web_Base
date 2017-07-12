package com.project.login.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.base.action.AdminBaseAction;
import com.project.base.action.Message;
import com.project.base.entity.Tree;
import com.project.login.action.LoginAction;
import com.project.resource.dao.ResourceMapper;
import com.project.resource.entity.Resource;
import com.project.resource.service.ResourceService;
import com.project.role.entity.Role;
import com.project.system.util.SecretUtil;
import com.project.user.entity.User;
import com.project.user.service.UserService;

public class LoginAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	@Autowired
	UserService userService;
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	ResourceService resourceService;
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	private String userAccount;

	private String userPassword;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void doNotNeedSessionAndSecurity_login() {
		Message message = new Message();
		User selectUser = userService.selectByUserAccount(this.userAccount);
		List<Resource> menus = new ArrayList<Resource>();
		if (selectUser == null) {
			message.setMsg("账号错误");
			message.setSuccess(false);
		} else if (!selectUser.getPassword().equals(this.userPassword)) {
			message.setMsg("密码错误");
			message.setSuccess(false);
		} else {
			Role myrole = selectUser.getRole();
			if (myrole != null) {
				if (myrole.getSystemAdmin() == 1) {
					logger.info("##################获取系统菜单资源＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
					List<Resource> resources = resourceMapper.selectAll();
					Map<String, Object> dictionaryInfoMap = new HashMap<String, Object>();
					dictionaryInfoMap.put("resources", resources);
					logger.info("##################成功获取系统菜单资源＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
					@SuppressWarnings("unchecked")
					List<Resource> allList = (List<Resource>) dictionaryInfoMap
							.get("resources");
					for (Resource resource : allList) {
						// 后台菜单
						if (resource.getType().equals("菜单")) {
							menus.add(resource);
						}
					}
				} else {

					List<Resource> resources = resourceMapper
							.getMenubyRoleId(myrole.getRoleId());
					// 拥有的菜单

					for (Resource resource : resources) {
						if (resource.getType().equals("菜单")) {
							menus.add(resource);
						}

					}

				}
				message.setSuccess(true);
				getSession().put("menus", menus);
				getSession().put("user", selectUser);

			}
		}

		super.writeJson(message);
	}

	/**
	 * 退出
	 */
	public void doNotNeedSessionAndSecurity_logout() {
		Message message = new Message();
		getSession().clear();
		message.setSuccess(true);
		super.writeJson(message);
	}

	/**
	 * 初始化后台菜单
	 * 
	 * @throws Exception
	 */
	public void doNotNeedSecurity_initMenu() {
		// 获取session中的菜单
		@SuppressWarnings("unchecked")
		List<Resource> menus = (List<Resource>) getSession().get("menus");
		if (menus != null && menus.size() > 0) {
			List<Tree> treeList = new ArrayList<Tree>();
			List<Tree> tree = new ArrayList<Tree>();
			for (Resource resource : menus) {
				if (resource.getType().equals("菜单")) {
					Tree node = new Tree();
					node.setId(resource.getId());
					node.setPid(resource.getPid());
					node.setText(resource.getName());
					node.setIconCls(resource.getIconCls());
					Map<String, String> attributes = new HashMap<String, String>();
					attributes.put("url", resource.getUrl());
					node.setAttributes(attributes);
					treeList.add(node);
				}
			}
			resourceService.parseTree(tree, treeList);
			super.writeJson(tree);
		} else {
			List<Tree> tree = new ArrayList<Tree>();
			super.writeJson(tree);
		}
	}

	/**
	 * 修改密码
	 */
	public void doNotNeedSecurity_updateCurrentPwd() {
		Message message = new Message();
		try {
			String pwd = getRequest().getParameter("pwd");
			User user = (User) getSession().get("user");
			user.setPassword(SecretUtil.encryptToSHA(pwd));
			userService.updatePassword(user);
			message.setMsg("密码修改成功!");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("密码修改失败!");
			message.setSuccess(false);
		}
		super.writeJson(message);

	}
}
