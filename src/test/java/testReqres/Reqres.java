package testReqres;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;


import java.io.File;

public class Reqres {
    File jsonSchema = new File("src/test/resources/jsonSchema/getListUser.json");
    JSONObject bodyObject = new JSONObject();

    @Test // GET positive scenario
    public void testGetAllUserList() {
        RestAssured.given().when().get("https://reqres.in/api/users?page=3")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("page", Matchers.equalTo(3));
    }

    @Test // GET positive scenario (Validate JSON Schema)
    public void testGetUserValidateJsonSchema() {
        RestAssured.given().when().get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
    }

    @Test // GET negative scenario
    public void testGetNonExistingUser() {
        RestAssured.given().when().get("https://reqres.in/api/users/29")
                .then().log().all()
                .assertThat().statusCode(404);
    }

    @Test // POST positive Scenario
    public void testPostUser() {
        String valueName = "Galih";
        String valueJob = "Student";
        bodyObject.put("name", valueName);
        bodyObject.put("job", valueJob);

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObject.toString())
                .when().post("https://reqres.in/api/users")
                .then().log().all()
                .assertThat().statusCode(201);
    }

    @Test // POST positive Scenario
    public void testPostRegisterUser() {
        String valueEmail = "michael.lawson@reqres.in";
        String valuePassword = "xzty12";
        bodyObject.put("email", valueEmail);
        bodyObject.put("password", valuePassword);

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObject.toString())
                .when().post("https://reqres.in/api/register")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test // POST negative Scenario (No password data)
    public void testPostFailedRegisterUser() {
        String valueEmail = "kadal@gmail";
        bodyObject.put("email", valueEmail);

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObject.toString())
                .when().post("https://reqres.in/api/register")
                .then().log().all()
                .assertThat().statusCode(400);
    }

    @Test // Put Positive Scenario
    public void testPutUser() {
        String valueName = "Robert";
        String valueJob = "President";

        bodyObject.put("name", valueName);
        bodyObject.put("job", valueJob);

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObject.toString())
                .when().put("https://reqres.in/api/users/2")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test // Patch Positive Scenario
    public void testPatchUser() {
        String valueName = "John";
        String valueJob = "Vice President";

        bodyObject.put("name", valueName);
        bodyObject.put("job", valueJob);

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObject.toString())
                .when().patch("https://reqres.in/api/users/2")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test // Delete Positive Scenario
    public void testDeleteUser() {
        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().delete("https://reqres.in/api/users/2")
                .then()
                .assertThat().statusCode(204).log().all();
    }

    @Test // Test NORMAL login Positive scenario
    public void testLoginUser() {
        String valueEmail = "michael.lawson@reqres.in";
        String valuePassword = "xzty12";
        bodyObject.put("email", valueEmail);
        bodyObject.put("password", valuePassword);

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObject.toString())
                .when().post("https://reqres.in/api/login")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test // Test INCORRECT EMAIL login scenario (negative scenario [edge case])
    public void testLoginIncorrectUserEmail() {
        String valueEmail = "upi.gaming@reqres.in";
        String valuePassword = "xzty12";
        bodyObject.put("email", valueEmail);
        bodyObject.put("password", valuePassword);

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObject.toString())
                .when().post("https://reqres.in/api/login")
                .then().log().all()
                .assertThat().statusCode(400);
    }

    @Test // Test INCORRECT password login scenario (negative scenario [edge case])
    public void testLoginForgotToInputPassword() {
        String valueEmail = "robert@president";
        bodyObject.put("email", valueEmail);

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObject.toString())
                .when().post("https://reqres.in/api/login")
                .then().log().all()
                .assertThat().statusCode(400);
    }

}
