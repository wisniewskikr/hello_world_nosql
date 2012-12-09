package pl.kwi.daos;


import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import pl.kwi.entities.UserEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {


    private String HOST_PATH = "http://127.0.0.1:5984/";
    private String BASE_NAME = "data_base";

    HttpClient client = new HttpClient();


    protected String getNewUUIDS() throws IOException, JSONException {

        GetMethod getMethod = new GetMethod(getHOST_PATH() + "_uuids");
        client.executeMethod(getMethod);

        JSONObject jsonObject = new JSONObject(getMethod.getResponseBodyAsString());

        return jsonObject.getString("uuids").replace("[\"", "").replace("\"]", "");

    }

    public void create(UserEntity entity) {

        try{

            String uuids = getNewUUIDS();
            String json = convertSimpleEntityToJsonString(entity);

            PutMethod putMethod = new PutMethod(getDATA_BASE_PATH() + uuids);
            putMethod.setRequestEntity(new StringRequestEntity(json, "application/json", "UTF-8"));
            client.executeMethod(putMethod);

            JSONObject jsonObject = new JSONObject(putMethod.getResponseBodyAsString());

            String _id = jsonObject.getString("id");
            String _rev = jsonObject.getString("rev");

            entity.set_id(_id);
            entity.set_rev(_rev);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public UserEntity read(String id) {

        UserEntity entity = null;

        try{

            GetMethod getMethod = new GetMethod(getDATA_BASE_PATH() + id);
            client.executeMethod(getMethod);

            entity = convertJsonStringToSimpleEntity(getMethod.getResponseBodyAsString());

        }catch (Exception e){
            e.printStackTrace();
        }

        return entity;

    }

    public void update(UserEntity entity){

        try{

            String json = convertSimpleEntityToJsonString(entity);

            PutMethod putMethod = new PutMethod(getDATA_BASE_PATH() + entity.get_id());
            putMethod.setRequestEntity(new StringRequestEntity(json, "application/json", "UTF-8"));
            client.executeMethod(putMethod);

            JSONObject jsonObject = new JSONObject(putMethod.getResponseBodyAsString());

            String _rev = jsonObject.getString("rev");

            entity.set_rev(_rev);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void delete(UserEntity entity){

        try{

            String id = entity.get_id();
            String rev = entity.get_rev();

            DeleteMethod deleteMethod = new DeleteMethod(getDATA_BASE_PATH() + id + "?rev=" + rev);
            client.executeMethod(deleteMethod);

            JSONObject jsonObject = new JSONObject(deleteMethod.getResponseBodyAsString());

            boolean isOk = jsonObject.getBoolean("ok");
            if(!isOk){
                throw new Exception("Problem with deleting entity. Response from server: " + jsonObject.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public List<UserEntity> getUsers() {

        List<UserEntity> resultList = new ArrayList<UserEntity>();

        try{

            GetMethod getMethod = new GetMethod(getDATA_BASE_PATH() + "_all_docs");
            client.executeMethod(getMethod);

            JSONObject jsonObject = new JSONObject(getMethod.getResponseBodyAsString());
            JSONArray rows = jsonObject.getJSONArray("rows");
            for (int i = 0; i < rows.length() ; i++) {
                JSONObject jo = rows.getJSONObject(i);
                String id = jo.getString("id");
                resultList.add(read(id));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultList;

    }


    // ***************** HELP METHODS *********************** //


    protected String convertSimpleEntityToJsonString(UserEntity entity){

        Gson gson = new Gson();
        return gson.toJson(entity);

    }

    protected UserEntity convertJsonStringToSimpleEntity(String json){

        Gson gson = new Gson();
        return gson.fromJson(json, UserEntity.class);

    }

    public String getHOST_PATH() {
        return HOST_PATH;
    }
    public void setHOST_PATH(String HOST_PATH) {
        this.HOST_PATH = HOST_PATH;
    }

    public String getBASE_NAME() {
        return BASE_NAME;
    }
    public void setBASE_NAME(String BASE_NAME) {
        this.BASE_NAME = BASE_NAME;
    }

    public String getDATA_BASE_PATH() {
        return HOST_PATH + BASE_NAME + "/";
    }

}
