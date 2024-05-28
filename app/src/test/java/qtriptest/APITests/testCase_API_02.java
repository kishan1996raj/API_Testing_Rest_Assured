package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
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

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
public class testCase_API_02 {
    RequestSpecification http;
    Response response;
  
    // @BeforeClass(alwaysRun = true)
    // public void init(){
    //     RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
    // }

    @Test(groups = "API Test", description = "Search cities")
    public void testCase02(){
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/cities";
        http = RestAssured.given().queryParam("q", "beng").log().all();
        response = http.when().get();

        JsonPath json = new JsonPath(response.body().asString());
        List<JSONObject> list = json.getList("$");
        Assert.assertEquals(list.size(), 1);
        System.out.println("list is : "+list);
        Assert.assertEquals(json.getString("[0].description"), "100+ Places","Failed description");
        
        File obj = new File("src/test/resources/schema.json");
        JsonSchemaValidator schemaValidator = JsonSchemaValidator.matchesJsonSchema(obj);
        response.then().assertThat().body(schemaValidator).log().all();
    }

}
