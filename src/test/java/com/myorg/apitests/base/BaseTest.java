package com.myorg.apitests.base;

import com.myorg.apitests.utils.ExtentManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;

/**
 * ClassName: BaseTest
 * Package: com.myorg.apitests.base
 * Description:
 *
 * @Author Grace
 * @Create 18/9/2025 3:13 pm
 * Version 1.0
 */
public class BaseTest {

    protected static Properties prop;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class); // can skip LoggerManager entirely — Log4j2 let every test class, listener just gets its own logger.

    @BeforeSuite
    public void loadConfig() {
        prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/config.properties");
            prop.load(fis);
            logger.info("Config file loaded: "+ prop.getProperty("booker.baseUrl"));
        } catch (IOException e) {
            logger.error("❌ Failed to load config file");
            throw new RuntimeException("Config file loading failed", e);
        }
    }

    @BeforeClass
    public void setUp(){
        logger.info("Setting up test environment...");
    }


    @AfterClass
    public void tearDown(){
        logger.info("Close test environment...");
    }

}
