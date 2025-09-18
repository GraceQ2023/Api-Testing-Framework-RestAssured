package com.myorg.apitests.base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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

    @BeforeSuite
    public void loadConfig() {
        prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
            prop.load(fis);
            System.out.println("load config: "+ prop.getProperty("fakestore.baseUrl"));
        } catch (IOException e) {
            System.out.println("❌ Failed to load config.properties");
            throw new RuntimeException("Config file loading failed", e);
        }
    }

    @BeforeClass
    public void setUp(){
        System.out.println("Setting up test environment...");
    }


    @AfterClass
    public void tearDown(){
        System.out.println("Close test environment...");

    }

}
