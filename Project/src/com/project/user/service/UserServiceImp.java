package com.project.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.base.entity.DataGrid;
import com.project.user.dao.UserMapper;
import com.project.user.entity.ServiceType;
import com.project.user.entity.User;

@Service("UserService")
public class UserServiceImp implements UserService {
	@Autowired
	UserMapper userMapper;

	@Override
	public User selectByUserAccount(String useraccount) {
		// TODO Auto-generated method stub
		User user = userMapper.getUserbyAccount(useraccount);
		return user;
	}

	@Override
	public void updatePassword(User user) {
		// TODO Auto-generated method stub
		userMapper.updatePassword(user.getPassword());
	}

	@Override
	public DataGrid<User> dataGrid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		DataGrid<User> dataGrid = new DataGrid<User>();
		long count = userMapper.countUser(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<User> list = userMapper.selectUserList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	@Override
	public long countByUserAccount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.countUser(map);
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		 this.userMapper.save(user);
	}

	@Override
	public User getUserByUserId(long userId) {
		// TODO Auto-generated method stub
		return userMapper.getUserbyUserId(userId);
	}

	@Override
	public void delete(long userId) {
		// TODO Auto-generated method stub
		userMapper.delete(userId);
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		userMapper.update(user);
	}


}
