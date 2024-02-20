package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.hasSize;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;
public class testCase_API_02 {

    @Test(groups = "API Tests")
    public void searchCity() {

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";

        RestAssured.basePath = "/api/v1/cities";

        RequestSpecification http = RestAssured.given().queryParam("q", "beng");

        Response response = http.request(Method.GET);

        int responseStatusCode = response.getStatusCode();

        response.then().statusCode(200);
        response.then().body("", hasSize(1));
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "/src/test/resources/searchCitySchema.json")));
    }
}
