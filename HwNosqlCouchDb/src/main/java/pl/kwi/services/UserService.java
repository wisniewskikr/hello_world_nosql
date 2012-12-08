package pl.kwi.services;

import java.util.List;


import pl.kwi.daos.UserDao;
import pl.kwi.entities.UserEntity;


public class UserService {

    UserDao dao = new UserDao();
	
	public List<UserEntity> getUsers(){
          return dao.getUsers();
	}
	
	public void create(UserEntity entity){
          dao.create(entity);
	}
	
	public UserEntity read(String id){
         return dao.read(id);
	}
	
	public void update(UserEntity entity){
        dao.update(entity);
	}
	
	public void delete(UserEntity entity){
        dao.delete(entity);
	}

}
