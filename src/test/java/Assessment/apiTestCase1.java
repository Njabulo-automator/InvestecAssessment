package Assessment;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class apiTestCase1 {


    public static void main(String[] args) {
// TODO Auto-generated method stub
        RestAssured.baseURI = "https://swapi.dev/api/people/";

        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.request(Method.GET, "");

        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);
        System.out.println("#########");
        //convert JSON to string
        JsonPath j = new JsonPath(response.asString());

        //get values of JSON array after getting array size
        int s = j.getInt("results.size()");
        for(int i = 0; i < s; i++) {
            String name = j.getString("results["+i+"].name");
            String skin_color = j.getString("results["+i+"].skin_color");
            //check if condition meets
            if(name.equalsIgnoreCase("R2-D2") && skin_color.contains("white") && skin_color.contains("blue")) {

                System.out.println("Test Passed Skin Color -"+skin_color);
                System.out.println("Test Passed name - "+name);
                break;
            }
        }

    }

}
