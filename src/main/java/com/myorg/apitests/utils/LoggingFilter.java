package com.myorg.apitests.utils;
import com.aventstack.extentreports.ExtentTest;
import com.myorg.apitests.listeners.TestListener;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * ClassName: LoggingFilter
 * Package: com.myorg.apitests.utils
 * Description:
 *
 * @Author Grace
 * @Create 23/9/2025 11:34 pm
 * Version 1.0
 */
public class LoggingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        // get ectentTest for the current test run
        ExtentTest extentTest = TestListener.getExtentTest();

        // log request details
        logger.info("\n--- REQUEST ---\n");
        logger.info("Method: {}",requestSpec.getMethod());
        logger.info("URI: {}", requestSpec.getURI());

        if(requestSpec.getHeaders() != null && !requestSpec.getHeaders().asList().isEmpty()){
            logger.info("Headers: {}", requestSpec.getHeaders());
        }

        if(requestSpec.getBody() != null){
            logger.info("Request body: {}", (Object)requestSpec.getBody());
        }

        // ctx works as a chain manager in a pipeline of filters, instruct:
        // step 1. take requestSpec and execute the request - sending HTTP request. RequestSpec = your prepared HTTP request (method, URL, headers, body).
        // step 2. when response come back, apply any validation from responseSpec, then return real response object so that can log it. ResponseSpec = expectations/validations defined (like status code checks).
        Response response = ctx.next(requestSpec, responseSpec);

        // log response details
        logger.info("\n--- API RESPONSE ---\n");
        logger.info("Status code: {}", response.getStatusCode());
        logger.info("Status line: {}", response.getStatusLine());

        if(response.getHeaders() != null && !response.getHeaders().asList().isEmpty()){
            logger.info("Headers: {}", response.getHeaders());
        }

        if(response.getBody() != null){
            logger.info("Response body: {}", response.getBody().asPrettyString());
        }

        logger.info("\n------------------------------------\n");

        return response;  // pass response back to the test
    }
}
