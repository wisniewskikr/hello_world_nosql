package pl.kwi.daos;


import pl.kwi.entities.UserEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class UserDao {
	
	public DBCollection collection;
	
	public UserDao(){
		
		try {
			
			Mongo mongo = new Mongo("127.0.0.1", 27017);
			DB db = mongo.getDB("mongodb");
			collection = db.getCollection("users");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}


    public void create(UserEntity entity) {

        try{

        	BasicDBObject document = new BasicDBObject();
			document.put("name", entity.getName());
			collection.insert(document);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public UserEntity read(String id) {

        UserEntity entity = null;

        try{

        	BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("_id", new ObjectId(id));
			
			DBCursor cursor = collection.find(searchQuery);
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				entity = new UserEntity();
				entity.set_id(((ObjectId)obj.get("_id")).toString());
				entity.setName((String)obj.get("name"));
			}


        }catch (Exception e){
            e.printStackTrace();
        }

        return entity;

    }

    public void update(UserEntity entity){

        try{

        	BasicDBObject documentOld = new BasicDBObject();
        	documentOld.put("_id", new ObjectId(entity.get_id()));
			
			BasicDBObject documentNew = new BasicDBObject();
        	documentNew.put("_id", new ObjectId(entity.get_id()));
			documentNew.put("name", entity.getName());
			collection.update(documentOld, documentNew);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void delete(UserEntity entity){

        try{
        	
        	BasicDBObject document = new BasicDBObject();
        	document.put("_id", new ObjectId(entity.get_id()));
        	collection.remove(document);
            
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public List<UserEntity> getUsers() {

        List<UserEntity> resultList = new ArrayList<UserEntity>();

        try{

        	DBCursor cursor = collection.find();
        	UserEntity user = null;
        	while (cursor.hasNext()) {
        		DBObject obj = cursor.next();
        		user = new UserEntity();
        		user.set_id(((ObjectId)obj.get("_id")).toString());
        		user.setName((String)obj.get("name"));
        		resultList.add(user);
    		}

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultList;

    }
    
    public static void main(String[] args) {
		
    	UserDao dao = new UserDao();
    	
//    	UserEntity user = new UserEntity();
//    	user.setName("Waclaw");
//    	dao.create(user);
    	
//    	List<UserEntity> users = dao.getUsers();
//    	for (UserEntity user : users) {
//    		System.out.println(user.get_id());
//    		System.out.println(user.getName());
//
//		}
    	
//    	UserEntity user = dao.read("50cdad99c84f0a41677ec696");
//		System.out.println(user.get_id());
//		System.out.println(user.getName());
		
//		UserEntity user = new UserEntity();
//		user.set_id("50cdad99c84f0a41677ec696");
//		user.setName("Chris2");
//		dao.update(user);
    	
    	UserEntity user = new UserEntity();
		user.set_id("50cdad99c84f0a41677ec696");
		user.setName("Chris2");
		dao.delete(user);
    	
    	
	}


    // ***************** HELP METHODS *********************** //




}
