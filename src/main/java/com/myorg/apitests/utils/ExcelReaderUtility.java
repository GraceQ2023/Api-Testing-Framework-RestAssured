package com.myorg.apitests.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ExcelReaderUtility
 * Package: com.myorg.apitests.utils
 * Description:
 *
 * @Author Grace
 * @Create 18/9/2025 5:44 pm
 * Version 1.0
 */

// read Excel and return raw data: List<String[]>
public class ExcelReaderUtility {

    // Reads sheet and returns as List<String[]>
    public static List<String[]> getSheetData (String filePath, String sheetName) {
        List<String[]> data = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IllegalArgumentException("Sheet not found: " + sheetName);

            for(Row row : sheet){
                if(row.getRowNum() == 0) continue;
                List<String> rowData = new ArrayList<>();

                for(Cell cell : row){
                    rowData.add(getCellValue(cell));
                }
                data.add(rowData.toArray(new String[0]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }
        return data;
    }



    // Helper: convert cell to String
    public static String getCellValue(Cell cell){
        if(cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getDateCellValue().toString(); // if it's a date
                } else {
                    yield String.valueOf((int) cell.getNumericCellValue()); // if it's a number
                }
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> " ";
        };
    }

}




