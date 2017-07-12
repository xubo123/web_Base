package com.project.user.dao;

import java.util.List;
import java.util.Map;

import com.project.user.entity.ServiceType;
import com.project.user.entity.User;

public interface UserMapper {
     User getUserbyAccount(String userAccount);

	void updatePassword(String password);

	long countUser(Map<String, Object> map);

	List<User> selectUserList(Map<String, Object> map);

	void save(User user);

	User getUserbyUserId(long userId);

	void delete(long userId);

	void update(User user);

}
