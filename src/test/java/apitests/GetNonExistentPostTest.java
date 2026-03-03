package apitests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetNonExistentPostTest extends BaseApiTest {

        @Test
        public void testGetNonExistentPost(){
            given()
                    .log().all()
                    .when()
                    .get("/posts/99999")
                    .then()
                    .log().all()
                    .statusCode(404); // у всех есть id


        }
}
