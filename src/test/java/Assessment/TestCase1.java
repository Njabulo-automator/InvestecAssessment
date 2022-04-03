package Assessment;

import Utilities.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class TestCase1 {
        WebDriver driver;
        private static final Logger LOG = LogManager.getLogger();
        public Properties AutoPropertiesFile = new Properties();
        public Utilities.ExcelUtils ExcelUtils = new ExcelUtils();
        public InputStream input = null;
        public String[][] readData;
        public Utilities.WebdriverUtil WebdriverUtil;
        public Utilities.Utils Utils;
        public Utilities.CommonUtil CommonUtil;
        static ExtentTest test;
        static ExtentReports report;
        //PageElements pg = new PageElements(driver);
        //CommonFunctions cf = new CommonFunctions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;


        @BeforeTest
        public void SetUp() throws Exception {
        input = new FileInputStream("src/test/resources/selenium.properties");
        AutoPropertiesFile.load(input);
        readData = ExcelUtils.readExcelDataFileToArray(AutoPropertiesFile.getProperty("readData"), "data");
        String baseUrl =AutoPropertiesFile.getProperty("baseUrl");
        //WebdriverUtil = new WebdriverUtil(baseUrl);
        //driver = WebdriverUtil.setWebdriver();
        //Utils = new Utils(driver);
        //CommonUtil = new CommonUtil(driver);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date());
        report = new ExtentReports(System.getProperty("user.dir") + "\\Reports\\TestCase1" + timeStamp + ".html", false);


        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mlung\\OneDrive\\Documents\\Njabulo\\VPC_PublicWEb_TC1\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        System.out.println(driver.getTitle());
        System.out.println("Chrome launched successfully:" +" "+ driver.getCurrentUrl());

//            driver.navigate().to("https://www.investec.com/en_za/focus/money/understanding-interest-rates.html");
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            Thread.sleep(200);
//            js.executeScript("window.scrollBy(0,2000)");
//            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    }

    //@Test (priority=1)
    public void Navigate() {

        driver.navigate().to("https://www.investec.com/en_za/focus/money/understanding-interest-rates.html");
        js.executeScript("window.scrollBy(0,1000)");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @Test
    public void SignUp() throws Exception {
        //navigate to the search screen using the link instead because the search path doesn't work

        try
        {test = report.startTest("Test that user can log in successfully with the header as Thank you");
        driver.navigate().to(readData[1][3]);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        //click sin in button and populate data from excel
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement signBtn = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[7]/div[2]/div/div/div/div/div[2]/div[1]/div[2]/button"));
        signBtn.click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement name = driver.findElement(By.name("name"));
        //read name from data sheet
        name.sendKeys(readData[1][0]);
        WebElement surname = driver.findElement(By.name("surname"));
        //read surname from data sheet
        surname.sendKeys(readData[1][1]);
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys(readData[1][2]);
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,200)");
        List<WebElement> Element = driver.findElements(By.className("checkbox-input__trigger-button"));
        Element.get(0).click();
        WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        submitBtn.click();

        String txt=driver.findElement(By.xpath("v/div/div/div/form/div[2]/div/div/div[1]/h3")).getText();
        System.out.println("page :" + txt);

        if (txt.equals("sss")){
            test.log(LogStatus.PASS, "User has been signed up successfully");
            System.out.println("User has been signed up successfully");
        }
        else{
            test.log(LogStatus.FAIL, "User has not been signed up successfully");
            System.out.println("User has not been signed up successfully");
        }
        }catch (Exception e) {
            LOG.info("step failed " + e.getMessage());
            throw e;
        }

    }
        @AfterClass
        public void afterTest() {
            report.endTest(test);
            report.flush();
            driver.quit();
        }


}
