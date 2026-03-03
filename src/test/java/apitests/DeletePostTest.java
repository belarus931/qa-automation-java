package apitests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeletePostTest extends BaseApiTest{

    @Test
    public void testDeletePost(){
        given()
                .log().all()
                .when()
                .delete("/posts/1")
                .then()
                .log().all()
                .statusCode(200);
//                .body("body", equalTo("This is a test post created by API"))
//                .body("title", equalTo("My Test Post"));
    }
}
