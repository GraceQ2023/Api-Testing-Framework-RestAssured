package com.myorg.apitests.dataproviders;

import com.myorg.apitests.utils.ExcelReaderUtility;
import org.testng.annotations.DataProvider;

import java.util.List;

/**
 * ClassName: DataProvider
 * Package: com.myorg.apitests.dataproviders
 * Description:
 *
 * @Author Grace
 * @Create 19/9/2025 9:58 am
 * Version 1.0
 */

// Convert excel raw data: List<String[]> into TestNG's required Object[][] format.
public class TestDataProvider {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/testData.xlsx";

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData(){
        List<String[]> data = ExcelReaderUtility.getSheetData(FILE_PATH, "Login");

        // Convert List to 2D object array -> Object[][]
        Object[][] result = new Object[data.size()][];
        for (int i =0; i <data.size(); i++){
            result[i] = data.get(i);
        }
        return result;
    }


}
