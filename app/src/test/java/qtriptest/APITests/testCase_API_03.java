package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;

public class testCase_API_03 {

    public String username = "";

    public String token = "";

    public String userId = "";
    
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

    public void login() {
        
        String jsonString = "{\"email\":\"" + this.username + "\", \"password\": \"test12345\"}";

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";

       RestAssured.basePath = "/api/v1/login";

       RequestSpecification http = RestAssured.given();

       http.body(jsonString);

       http.contentType(ContentType.JSON);

       Response response = http.request(Method.POST);

       String responseBody = response.body().asString();
       JsonPath jsonPath = new JsonPath(responseBody);
       this.token = jsonPath.getString("data.token");
       this.userId = jsonPath.getString("data.id");
       
    }

    @Test(groups = "API Tests")
    public void makeReservation() {
        register();

        login();

        String jsonString = "{\"userId\":\"" + this.userId + "\", \"name\":\"sourav borah\", \"date\":\"2024-08-07\",\"person\":\"2\",\"adventure\":\"2447910730\"}";

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";

        RestAssured.basePath = "/api/v1/reservations/new";

        RequestSpecification http = RestAssured.given();

        http.contentType(ContentType.JSON);

        http.body(jsonString);

        http.header("Authorization", "Bearer " + this.token);

        Response response = http.request(Method.POST);

        response.then().statusCode(200);

        Response reservationList = getReservations();

        reservationList.then().body("[0].adventure", equalTo("2447910730"));
    }

    public Response getReservations() {

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";

        RestAssured.basePath = "/api/v1/reservations";

        RequestSpecification http = RestAssured.given().queryParam("id", this.userId);

        http.header("Authorization", "Bearer " + this.token);

        Response response = http.request(Method.GET);

        return response;
    }
}
