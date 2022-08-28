import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.Payload;

public class Basics {

	public static void main(String[] args) {
		
		//Automate Add place API
		
		//Given->> All inputs
		//When->> resource,HTTP request type
		//Then->> status
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.addPlace())//calling the request body from files->>Payload
		.when().post("/maps/api/place/add/json")
		.then().log().all().statusCode(200)
		.body("scope",equalTo("APP"))//Verify the response body has a key scope with value APP
		.header("Server", "Apache/2.4.41 (Ubuntu)");//Check response body for Header->>Server
		
		//Add place->>Update place->>Validate the updated address
		
		
	}

}
