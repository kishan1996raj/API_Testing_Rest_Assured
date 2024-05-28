package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

public class testCase_API_04 {
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

   @Test(priority = 1, groups = "API Test", description = "testCase 4 - Negative test cases ")
   public void registerapi(){
      RestAssured.basePath = "/api/v1/register";
      RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
      email = "testuser"+UUID.randomUUID().toString()+"@gmail.com";
      password = "123456789";
      obj = new JSONObject();
      obj.put("email", email);
      obj.put("password",password);
      obj.put("confirmpassword", password);
      http = RestAssured.given().header("Content-Type","application/json").body(obj.toString()).log().all();
      response = http.when().post();
      Assert.assertEquals(response.statusCode(), 201);

      JsonPath json = new JsonPath(response.body().asString());
      System.out.println(json.getBoolean("success"));
      RestAssured.basePath = "/api/v1/register";
      
      email = "testuser"+UUID.randomUUID().toString()+"@gmail.com";
      password = "123456789";
      obj = new JSONObject();
      obj.put("email", email);
      obj.put("password",password);
      obj.put("confirmpassword", password);
      http = RestAssured.given().header("Content-Type","application/json").body(obj.toString()).log().all();
      response = http.when().post();
      Assert.assertEquals(response.statusCode(), 201);

      json = new JsonPath(response.body().asString());
      System.out.println(json.getBoolean("success"));
   }
 
    }

  

