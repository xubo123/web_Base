package com.project.user.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.base.action.AdminBaseAction;
import com.project.base.action.Message;
import com.project.user.entity.User;
import com.project.user.service.UserService;

public class UserAction extends AdminBaseAction{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserAction.class);
    
	@Autowired
	private UserService userService;
	User user;
	int userId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void dataGrid(){
		String userAccount = getRequest().getParameter("userAccount");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		map.put("userAccount", userAccount);
		super.writeJson(userService.dataGrid(map));
	}
	public void save(){
		Message message = new Message();
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userAccount", user.getUserAccount());
			map.put("userId", user.getUserId());
			long count = userService.countByUserAccount(map);
			if (count > 0)
			{
				message.setMsg("用户帐号已被占用");
				message.setSuccess(false);
			} else
			{
				user.setLastLoginTime(new Date());
				userService.save(user);
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
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userAccount", user.getUserAccount());
			map.put("userId", user.getUserId());
			userService.update(user);
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
	public void getUserByUserId(){
		if(user.getUserId()!=0){
			super.writeJson(userService.getUserByUserId(user.getUserId()));
		}else{
			Message message = new Message();
			message.setMsg("未找到用户，userId为空");
			message.setSuccess(false);
			super.writeJson(message);
		}
	}
	
	public void delete(){
		Message message = new Message();
		if(userId!=0){
			userService.delete(userId);
			message.setMsg("删除成功");
			message.setSuccess(true);
			super.writeJson(message);
		}else{
			
			message.setMsg("删除失败");
			message.setSuccess(false);
			super.writeJson(message);
		}
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
