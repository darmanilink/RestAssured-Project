import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.builder.*;
import io.restassured.filter.log.*;
import io.restassured.response.Response;
import io.restassured.specification.*;
import static io.restassured.RestAssured.*;
import io.restassured.http.*;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;

import javax.json.stream.JsonParser;
import java.io.File;
import java.util.*;

public class StoreManager {
    static String URL_BASE = "https://petstore.swagger.io/v2";
    static String PET_BASE = "/pet";
    static String USER_BASE = "/user";
    static String STORE_BASE = "/store";
    static String USER_LOGIN = "user/login";
    static String USER_LOGOUT = "/logout";
    static String ORDER_BASE = "/store/order";

    RequestSpecification rq = new RequestSpecBuilder()
            .setBaseUri(URL_BASE)
            .setContentType(ContentType.JSON)
            .build();

    public void AddPet(String name, String id){
        given(rq).body("{\n" +
                "  \"id\": "+ id +",\n" +
                "  \"name\": \""+ name +"\",\n" +
                "  \"photoUrls\": [],\n" +
                "  \"tags\": [],\n" +
                "  \"status\": \"" + Pet.Statuses[0] + "\"\n" +
                "}").post();
    }
    public void AddPet(String JSONSTRING){
        given(rq).body(JSONSTRING).post();
    }
    public void DeletePet(String id){
        given(rq)
                .delete(PET_BASE + String.format("/%s", id));
    }

    public void BuyPet(int id, RequestSpecification rqusr) {
        Pet temp = new Pet();
        temp.self_as_json = given(rqusr)
                .get(PET_BASE + id).toString();
        temp.self_as_json.replace("Availible", "Sold");

        given(rq)
                .body(temp.self_as_json).put(PET_BASE);

        for (boolean invalidCode = true; invalidCode; ) {
            String orderID = RandomStringUtils.randomNumeric(10);
            if (given(rq).get(ORDER_BASE + String.format("%s", orderID)).statusCode() == 404) {
                invalidCode = false;
                int orderStatus = given(rq).body("{\n" +
                        String.format("\"id\": %s,\n", orderID) +
                        String.format("\"petId\": %s,\n", id) +
                        "\"quantity\": 1,\n" +
                        "\"shipDate\": \""+ DateTime.now()+"\",\n" +
                        "\"status\": \"placed\",\n" +
                        "\"complete\": false\n" +
                        "}").post(ORDER_BASE).statusCode();
                        if(orderStatus != 400)
                            System.out.println("bad format");
            }
        }
    }
    public void UpdatePetName(int id, String name){
        given(rq)
                .post(PET_BASE + "/" + id + "?name=" + name);
    }

    public String GetPet(int id){
       return given(rq)
                .get(PET_BASE + "/" + id).toString();

    }
    public void UploadImage(String path){
        given(rq)
                .contentType("image/jpg").multiPart(new File(path))
                .post(PET_BASE);
    }
    public void UploadImage(String path, String metadata){
        given(rq)
                .contentType("image/jpg").body(metadata).multiPart(new File(path))
                .post(PET_BASE);
    }
    public String GetInventory(){
        return given(rq).get(STORE_BASE + "/inventory").toString();
    }
    public String GetOrder(int id){
        return given(rq).get(ORDER_BASE + "/" + id).toString();
    }
    public void DeleteOrder(int id){
        given(rq).delete(ORDER_BASE + "/" + id);
    }
    public int Login(String username, String password){
       return Integer.parseInt(given(rq).get(USER_LOGIN +
               String.format("?username=%s&password=%s",username,password)).toString()
               .substring(23));
    }
    public void Logout(RequestSpecification rq_user){
        given(rq_user).get(USER_BASE + "/logout");
    }
    public String GetUser(String usrn) {
        return given(rq).get(USER_BASE + "/" + usrn).toString();
    }
    public void CreateAccount(String username, String password){
        given(rq).body("{  \"id\": 0,  " +
                        "\"username\": \""+username+"\",  " +
                        "\"firstName\": \"string\", " +
                        " \"lastName\": \"string\",  " +
                        "\"email\": \"string\",  " +
                        "\"password\": \""+password+"\", " +
                        " \"phone\": \"string\", " +
                        " \"userStatus\": 0}").post(USER_BASE);
    }
    public void UpdateAccount(String current, String upd_username){
        String str = given(rq).put(USER_BASE + String.format("/%s?username=%s", current, upd_username)).toString();
        JsonObject json = new JsonObject();
    }
    public void CreateMultipleAccounts(Account[] acc){
        String json = "{";
        for (Account ac : acc)
            json += ac.toString() + ",\n";
        given(rq).body(json).post(USER_BASE + "/createWithArray");
    }
    public void DeleteAccount(String username){
        given(rq).delete(USER_BASE + String.format("?username=%s", username));
    }
}
