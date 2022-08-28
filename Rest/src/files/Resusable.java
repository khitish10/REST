package files;

import io.restassured.path.json.JsonPath;

public class Resusable {
	
	public static JsonPath rawToJson(String response) {
		
		JsonPath json=new JsonPath(response);
		return json;
	}

}
