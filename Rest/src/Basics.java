import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.Resusable;

public class Basics {

	public static void main(String[] args) {
		
		//Add place->>Update place->>Validate the updated address
		
		
		//Given->> All inputs
		//When->> resource,HTTP request type
		//Then->> status
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		
		//Automate Add place API
		
		String storeResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.addPlace())//calling the request body from files->>Payload
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200)//check if the sattus code is 200
		.body("scope",equalTo("APP"))//Verify the response body has a key scope with value APP
		.header("Server", "Apache/2.4.41 (Ubuntu)")//Check response body for Header->>Server
		.extract().response().asString();//extract the response body and store it in a variable
		
		System.out.println(storeResponse);
		
		//JsonPath json = new JsonPath(storeResponse);//for parsing the Json
		JsonPath json = Resusable.rawToJson(storeResponse);
		String place_id = json.getString("place_id");//store the place id in a variable
		System.out.println(place_id);
		
		//Update Place
		String updatedPlace="70 Street Summer walk, Mexico";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"//Update the place id value from above stored string
				+ "\"address\":\""+updatedPlace+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		String getResponsePlace = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)//No header since Get request we dont have request body
		.when().get("maps/api/place/get/json")
		.then().log().all().statusCode(200).extract().response().asString();
		
		//JsonPath json1=new JsonPath(getResponsePlace);
		//Optimise JsonPath
		JsonPath json1 = Resusable.rawToJson(getResponsePlace);
		String actualAddress = json1.getString("address");
		System.out.println(actualAddress);
		
		//Using TestNG Asserts to check
		Assert.assertEquals(actualAddress, updatedPlace);
		
		
		
	}

}
