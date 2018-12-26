package com.ccic.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 8000600758 on 2018/9/29.
 */
public class ExcelUtil {
    private ExcelUtil() {
    }

    public static List<Map<String, String>> readExcelByStream(String excelPath) throws Exception {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(excelPath);
        String extString = excelPath.substring(excelPath.lastIndexOf("."));
        return readExcelByStream(stream, extString, null);
    }
    //读取两种格式的Excel内容

    /**
     * @param stream    输入流
     * @param extString excel后缀
     * @param columns   列名称
     * @description: 读取两种格式的Excel
     * @author: baipengfei
     * @since: 2018年3月21日 下午12:01:44
     */
    public static List<Map<String, String>> readExcelByStream(InputStream stream, String extString, String[] columns) throws Exception {
        Workbook wb = getExcelWb(stream, extString);
        List<Map<String, String>> list = null;
        String cellData = null;
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<Map<String, String>>();
            //获取第一个sheet
            Sheet sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            Row row = sheet.getRow(0);
            //获取最大列数
            int column = row.getPhysicalNumberOfCells();
            //已第一行的表头数据为列的名称
            if (null == columns) {
                columns = new String[column];
                for (int i = 0; i < column; i++) {
                    columns[i] = row.getCell(i).getStringCellValue();
                }
            }
            for (int i = 1; i < rownum; i++) {
                Map<String, String> map = new LinkedHashMap<String, String>();
                row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < column; j++) {
                        if (columns[j].toString().equals("提出时间")) {
                            Date dateValue = row.getCell(j).getDateCellValue();
                            cellData = new SimpleDateFormat("yyyy-MM-dd").format(dateValue==null?new Date("2018-09-29"):dateValue);
                        } else {
                            cellData = String.valueOf(getCellFormatValue(row.getCell(j)));
                        }


                        map.put(columns[j], cellData);
                    }
                } else {
                    break;
                }
                list.add(map);
            }

        }
        return list;
    }

    //获取Workbook对象
    private static Workbook getExcelWb(InputStream stream, String extString) throws Exception {
        Workbook wb = null;
        if (stream == null) {
            return null;
        }

        if (".xls".equals(extString)) {
            return wb = new HSSFWorkbook(stream);
        } else if (".xlsx".equals(extString)) {
            return wb = new XSSFWorkbook(stream);
        } else {
            return wb = null;
        }

    }

    //获取单元格值
    private static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }

                case Cell.CELL_TYPE_FORMULA: {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue();
                    } else {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }

                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue();
                    break;
                }

                default:
                    cellValue = "";

            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }
}


