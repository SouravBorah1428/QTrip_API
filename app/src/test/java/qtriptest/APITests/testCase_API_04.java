package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import java.util.UUID;

public class testCase_API_04 {

    public String username = "";

    public void register() {

        String username = UUID.randomUUID().toString();

        String jsonString = "{\"email\":\"" + username + "\", \"password\": \"test12345\", \"confirmpassword\": \"test12345\"}";
        
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";

        RestAssured.basePath = "/api/v1/register";

        RequestSpecification http = RestAssured.given();

        http.body(jsonString);
        http.contentType(ContentType.JSON);

        Response response = http.request(Method.POST);

        this.username = username;
    }

    @Test(groups = "API Tests")
    public void registerTest() {
        
        register();

        String jsonString = "{\"email\":\"" + this.username + "\", \"password\": \"test12345\", \"confirmpassword\": \"test12345\"}";
        
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";

        RestAssured.basePath = "/api/v1/register";

        RequestSpecification http = RestAssured.given();

        http.body(jsonString);
        http.contentType(ContentType.JSON);

        Response response = http.request(Method.POST);

        response.then().statusCode(400);

        response.then().body("message", equalTo("Email already exists"));
    }
}

  

