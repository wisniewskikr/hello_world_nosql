package pl.kwi.daos;

import java.util.ArrayList;
import java.util.List;

import pl.kwi.entities.UserEntity;
import redis.clients.jedis.Jedis;

public class UserDao {
	
	private Jedis jedis;
	
	public UserDao(){
		jedis = new Jedis("localhost");
	}
		
	
	public void createUser(UserEntity user) throws Exception{				
		
		Long id = createId();
		jedis.set(id.toString(), user.getName());
		
	}
	
	public UserEntity readUser(Long id) throws Exception{
		
		String name = jedis.get(id.toString());
		UserEntity user = new UserEntity();
		user.setId(id);
		user.setName(name);
		return user;
		
	}
	
	public void updateUser(UserEntity user) throws Exception{
		
		jedis.set(user.getId().toString(), user.getName());
		
	}
	
	public void deleteUser(Long id) throws Exception {
		
		jedis.del(id.toString());
		
	}
	
	public List<UserEntity> getUsers() throws Exception{
		
		List<UserEntity> users = new ArrayList<UserEntity>();
		
		String userId = jedis.get("userId");
		if(userId == null){
			return users;
		}
		
		String name = null;
		UserEntity user;
		for (int i = 1; i <= Integer.valueOf(userId); i++) {
			
			name = jedis.get(String.valueOf(i));
			if(name == null){
				continue;
			}
			user = new UserEntity();
			user.setId(Long.valueOf(i));
			user.setName(name);
			users.add(user);
			
		}
		
		
		return users;
		
	}
	
	public Long createId(){		
		return jedis.incr("userId");
	}

}
