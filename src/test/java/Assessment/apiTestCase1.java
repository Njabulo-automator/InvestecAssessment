package Assessment;

import Utilities.ExcelUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class apiTestCase1 {

    public Properties AutoPropertiesFile = new Properties();
    public Utilities.ExcelUtils ExcelUtils = new ExcelUtils();
    public InputStream input = null;
    static ExtentTest test;
    static ExtentReports report;



    @BeforeTest
    public void setup(){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date());
        report = new ExtentReports(System.getProperty("user.dir") + "\\Reports\\apiTestCase1" + timeStamp + ".html", false);
    }


    @Test
    public void TestExecution() {
        test = report.startTest("api test");
// TODO Auto-generated method stub
        RestAssured.baseURI = "https://swapi.dev/api/people/";

        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.request(Method.GET, "");

        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);
        System.out.println("#########");
        //convert JSON to string
        JsonPath j = new JsonPath(response.asString());
        test.log(LogStatus.INFO,"<b> The response body is </b> </br>" +responseBody);
        //get values of JSON array after getting array size
        int s = j.getInt("results.size()");
        for(int i = 0; i < s; i++) {
            String name = j.getString("results["+i+"].name");
            String skin_color = j.getString("results["+i+"].skin_color");
            //check if condition meets
            if(name.equalsIgnoreCase("R2-D2") && skin_color.contains("white") && skin_color.contains("blue")) {

                    test.log(LogStatus.PASS, "Test Passed Skin Color -"+skin_color+ " Test Passed name "+name );

               break;
            }
            else if(i==s) {
                test.log(LogStatus.FAIL, "Testing colour and name not found");

            }
        }

    }

    @AfterClass
    public void endTest() {
        report.endTest(test);
        report.flush();

    }

}
