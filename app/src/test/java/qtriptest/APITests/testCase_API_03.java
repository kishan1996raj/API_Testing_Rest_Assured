package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;

public class testCase_API_03 {
    JSONObject obj;
    String user_ID;
    String token;
    String email;
    String password;
    RequestSpecification http;
    Response response;
    JsonPath json;

//    @BeforeMethod(alwaysRun = true)
//    public void init(){
//        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
//    }

   @Test(priority = 1, groups = "API Test", description = "registration,login and registration flow")
   public void testCase_03(){
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

     System.out.println("Token :"+token);
     RestAssured.basePath = "/api/v1/reservations/new";
     obj = new JSONObject();
     obj.put("userId",user_ID);
     obj.put("name","testUser");
     obj.put("date","2024-07-09");
     obj.put("person","01");
     obj.put("adventure","1773524915");
     http  = RestAssured.given().header("Content-Type","application/json").header("Authorization", "Bearer "+token).body(obj.toString()).log().all();
     response = http.when().post();
     String body = response.body().asString();
     System.out.println("Response body : "+body);
     json = new JsonPath(response.body().asString());
     Assert.assertTrue(json.getBoolean("success"));

     RestAssured.basePath="/api/v1/reservations/";
     http = RestAssured.given().queryParam("id", user_ID).header("Authorization","Bearer "+token).log().all();
     response = http.when().get();
     String body1 = response.body().asString();
     System.out.println(body1);
     Assert.assertEquals(response.getStatusCode(), 200);
   }

}

