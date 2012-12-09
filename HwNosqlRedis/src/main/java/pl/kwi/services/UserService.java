package pl.kwi.services;

import java.sql.Connection;
import java.util.List;

import pl.kwi.daos.UserDao;
import pl.kwi.entities.UserEntity;


public class UserService {
	
	public void createUser(UserEntity user) throws Exception{
		
		UserDao dao = new UserDao();
		dao.createUser(user);
		
	}
	
	public UserEntity readUser(Long id) throws Exception{
		
		UserEntity user;
		
		UserDao dao = new UserDao();
		user = dao.readUser(id);
		
		return user;
		
	}
	
	public void updateUser(UserEntity user) throws Exception{
		
		UserDao dao = new UserDao();
		dao.updateUser(user);
		
	}
	
	public void deleteUser(Long id) throws Exception{
		
		UserDao dao = new UserDao();
		dao.deleteUser(id);
		
	}
	
	public List<UserEntity> getUsers() throws Exception{
		
		List<UserEntity> users;
		
		UserDao dao = new UserDao();
		users = dao.getUsers();
		
		return users;
		
	}

}
