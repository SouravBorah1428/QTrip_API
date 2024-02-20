package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.asynchttpclient.Request;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import java.util.UUID;
import static org.hamcrest.Matchers.equalTo;


public class testCase_API_01 {

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

        int responseStatusCode = response.getStatusCode();

        this.username = username;

        response.then().statusCode(201);
    }

    @Test(groups = "API Tests")
    public void login() {

        register();
        
        String jsonString = "{\"email\":\"" + this.username + "\", \"password\": \"test12345\"}";

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";

       RestAssured.basePath = "/api/v1/login";

       RequestSpecification http = RestAssured.given();

       http.body(jsonString);

       http.contentType(ContentType.JSON);

       Response response = http.request(Method.POST);

       response.then().statusCode(201);

       response.then().body("success", equalTo(true));
    }
   
}
