 package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import java.util.UUID;



public class testCase_API_01 {
   
     JSONObject obj;
     String user_ID;
     String token;
     String email;
     String password;
     RequestSpecification http;
     Response response;
     JsonPath json;

    // @BeforeMethod(alwaysRun = true)
    // public void init(){
    //     RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
      
    // }

    @Test(priority = 1, groups = "API Test", description = "Registration and login flow")
    public void registerapi(){
    RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
       RestAssured.basePath = "/api/v1/register";
       
       email = "testuser"+UUID.randomUUID().toString()+"@gmail.com";
       password = UUID.randomUUID().toString();


       obj = new JSONObject();
       obj.put("email", email);
       obj.put("password",password);
       obj.put("confirmpassword", password);
       http = RestAssured.given().header("Content-Type","application/json").body(obj.toString()).log().all();
       response = http.when().post();
       Assert.assertEquals(response.statusCode(), 201);

       json = new JsonPath(response.body().asString());
       System.out.println(json.getBoolean("success"));

       RestAssured.basePath = "/api/v1/login";
       System.out.println(email +"    "+password);
       obj = new JSONObject();
       obj.put("email",email);
       obj.put("password",password);
       
       http = RestAssured.given().header("Content-Type","application/json").body(obj.toString()).log().all();
       response = http.when().post();
       Assert.assertEquals(response.statusCode(), 201);
 
       json = new JsonPath(response.body().asString());
       token = json.getString("data.token");
       user_ID = json.getString("data.id");
       System.out.println("Token : " +token);
       System.out.println("User ID  : "+user_ID);
    }
 
}
