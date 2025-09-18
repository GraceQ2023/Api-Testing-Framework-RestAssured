package com.myorg.apitests.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * ClassName: RequestSpecManager
 * Package: com.myorg.apitests.utils
 * Description:
 *
 * @Author Grace
 * @Create 18/9/2025 3:14 pm
 * Version 1.0
 */
public class RequestSpecManager extends BaseTest {

    private static RequestSpecification fakeStoreSpec;
    private static RequestSpecification bookerSpec;

    public static RequestSpecification getFakeStoreSpec (){
        if(fakeStoreSpec == null){
            fakeStoreSpec = new RequestSpecBuilder()
                    .setBaseUri(prop.getProperty("fakestore.baseUrl"))
                    .setContentType(ContentType.JSON)
                    .log(LogDetail.ALL)
                    .build();
        }
        return fakeStoreSpec;
    }

    public static RequestSpecification getBookerSpec(){
        if(bookerSpec == null){
            bookerSpec = new RequestSpecBuilder()
                    .setBaseUri(prop.getProperty("booker.baseUrl"))
                    .setContentType(ContentType.JSON)
                    .log(LogDetail.ALL)
                    .build();
        }
        return bookerSpec;
    }
}
