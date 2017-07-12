package com.project.user.service;

import java.util.List;
import java.util.Map;

import com.project.base.entity.DataGrid;
import com.project.user.entity.ServiceType;
import com.project.user.entity.User;

public interface UserService {
	User selectByUserAccount(String useraccount) ;

	void updatePassword(User user);

	DataGrid<User> dataGrid(Map<String, Object> map);

	long countByUserAccount(Map<String, Object> map);

	void save(User user);

	User getUserByUserId(long userId);

	void delete(long userId);

	void update(User user);

}
