import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GroceryTest {
    
    @Test
    public void allGrocerySuccess(){

                given().header("Content-type", "application/json")
                        .when()
                        .get("https://1b5ba243-4eb2-497c-9cb5-b22ebe6a27d9.mock.pstmn.io/allgrocery")
                        .then().statusCode(200)
                        .contentType(ContentType.JSON);
    }
    @Test
    public void groceryFilterSuccess(){
        RestAssured.defaultParser = Parser.JSON;

        when()
                        .get("https://1b5ba243-4eb2-497c-9cb5-b22ebe6a27d9.mock.pstmn.io/allgrocery/apple")
                        .then().statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("data.name",equalTo("apple"));
    }

    @Test
    public void groceryFilterBadRequest(){
                when()
                        .get("https://1b5ba243-4eb2-497c-9cb5-b22ebe6a27d9.mock.pstmn.io/allgrocery/applee")
                        .then().statusCode(400)
                        .body("message",equalTo("istek hatalı"));
    }

    @Test
    public void groceryFilterNotFound(){
        when()
                .get("https://1b5ba243-4eb2-497c-9cb5-b22ebe6a27d9.mock.pstmn.io/allgrocery\"")
                .then().statusCode(404)
                .body("error.message",equalTo("Double check your method and the request path and try again."));
    }
    @Test
    public void addGrocerySuccess(){
            String postData = "{\n" +
                    "  \"id\": \"6\",\n" +
                    "  \"name\": \"peach\",\n" +
                    "  \"price\": \"12,3\",\n" +
                    "  \"stock\": \"3\" \n}";
            given()
                    .contentType(ContentType.JSON)
                    .body(postData)
                    .when()
                    .post("https://1b5ba243-4eb2-497c-9cb5-b22ebe6a27d9.mock.pstmn.io/add")
                    .then()
                    .statusCode(201);
    }

    @Test
    public void addGroceryNotFound(){
        String postData = "{\n" +
                "  \"id\": \"6\",\n" +
                "  \"name\": \"peach\",\n" +
                "  \"price\": \"12,3\",\n" +
                "  \"stock\": \"3\" \n}";
        given()
                .contentType(ContentType.JSON)
                .body(postData)
                .when()
                .post("https://1b5ba243-4eb2-497c-9cb5-b22ebe6a27d9.mock.pstmn.io/add-")
                .then()
                .statusCode(404)
                .body("error.message",equalTo("Double check your method and the request path and try again."));
    }


}
