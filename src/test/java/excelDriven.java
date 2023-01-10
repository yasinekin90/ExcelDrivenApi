import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.*;

public class excelDriven {

    @Test
    public void addBook() throws IOException {

        RestAssured.baseURI="http://216.10.245.166";

        ArrayList<String> d = ReusableMethods.getData("RestAddABook","RestAssured");

        Response response=
                given().header("Content-Type","application/json")
                        .body(ReusableMethods.addBodyToMap(d.get(1),d.get(2),d.get(3),d.get(4)))
                        .post("/Library/Addbook.php")
                        .then().log().all().assertThat().statusCode(200)
                        .extract().response();
        JsonPath js=ReusableMethods.rawToJson(response);
        String id=js.get("ID");
        System.out.println(id);


    }
}
