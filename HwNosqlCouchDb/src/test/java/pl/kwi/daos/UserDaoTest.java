package pl.kwi.daos;

import junit.framework.Assert;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import pl.kwi.daos.UserDao;
import pl.kwi.entities.UserEntity;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 7/13/12
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserDaoTest {


    private String HOST_PATH = "http://127.0.0.1:5984/";
    private String BASE_NAME = "data_base_test";

    UserDao dao;


    @Before
    public void setUp() throws IOException {
        dao = new UserDao();
        dao.setHOST_PATH(HOST_PATH);
        dao.setBASE_NAME(BASE_NAME);
        deleteDb();
        createDb();
    }

    private void deleteDb() throws IOException {

        HttpClient client = new HttpClient();
        DeleteMethod deleteMethod = new DeleteMethod(HOST_PATH + BASE_NAME);
        client.executeMethod(deleteMethod);

    }

    private void createDb() throws IOException {

        HttpClient client = new HttpClient();
        PutMethod putMethod = new PutMethod(HOST_PATH + BASE_NAME);
        client.executeMethod(putMethod);

    }

    @Test
    public void getNewUUIDS() throws IOException, JSONException {

        String uuids = dao.getNewUUIDS();
        Assert.assertNotNull(uuids);

    }

    @Test
    public void create() throws IOException, JSONException {

        UserEntity entity = new UserEntity();
        entity.setName("Chris");

        Assert.assertNull(entity.get_id());
        Assert.assertNull(entity.get_rev());

        dao.create(entity);

        Assert.assertNotNull(entity.get_id());
        Assert.assertNotNull(entity.get_rev());
        Assert.assertEquals("Chris", entity.getName());

    }

    @Test
    public void read() throws IOException, JSONException {

        UserEntity entity = new UserEntity();
        entity.setName("Chris");

        Assert.assertNull(entity.get_id());
        Assert.assertNull(entity.get_rev());

        dao.create(entity);

        String id = entity.get_id();
        String rev = entity.get_rev();

        entity = dao.read(id);

        Assert.assertEquals(id, entity.get_id());
        Assert.assertEquals(rev, entity.get_rev());
        Assert.assertEquals("Chris", entity.getName());

    }

    @Test
    public void update() throws IOException, JSONException {

        UserEntity entity = new UserEntity();
        entity.setName("Chris");
        dao.create(entity);

        String oldRev = entity.get_rev();

        entity.setName("Jacek");
        dao.update(entity);

        String newRev = entity.get_rev();
        Assert.assertNotSame(oldRev, newRev);

        entity = dao.read(entity.get_id());
        Assert.assertEquals("Jacek", entity.getName());

    }

    @Test
    public void delete() throws Exception {

        UserEntity entity = new UserEntity();
        entity.setName("Chris");
        dao.create(entity);

        Assert.assertNotNull(entity.get_id());
        Assert.assertNotNull(entity.get_rev());

        dao.delete(entity);

    }

    @Test
    public void getUsers() throws IOException, JSONException {

        UserEntity entity = null;

        entity = new UserEntity();
        entity.setName("Chris");
        dao.create(entity);

        entity = new UserEntity();
        entity.setName("Jacek");
        dao.create(entity);

        entity = new UserEntity();
        entity.setName("Ola");
        dao.create(entity);

        List<UserEntity> list = dao.getUsers();

        Assert.assertEquals(3, list.size());
        Assert.assertEquals("Chris", list.get(0).getName());
        Assert.assertEquals("Jacek", list.get(1).getName());
        Assert.assertEquals("Ola", list.get(2).getName());

    }

    @Test
    public void convertSimpleEntityToJsonString(){

        UserEntity entity = new UserEntity();
        entity.set_id("1");
        entity.set_rev("");
        entity.setName("Chris");

        String json = dao.convertSimpleEntityToJsonString(entity);

        Assert.assertEquals("{\"_id\":\"1\",\"_rev\":\"\",\"name\":\"Chris\"}", json);

    }

    @Test
    public void convertJsonStringToSimpleEntity(){

        String json = "{\"_id\":\"1\",\"_rev\":\"2\",\"name\":\"Chris\"}";
        UserEntity entity = dao.convertJsonStringToSimpleEntity(json);

        Assert.assertEquals("1", entity.get_id());
        Assert.assertEquals("2", entity.get_rev());
        Assert.assertEquals("Chris", entity.getName());

    }




}
