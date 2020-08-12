package cn.hiboot.framework.research.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.*;

public class PoiDemo {

    private static final String EXCEL_FILE_PATH = "G:\\data\\test.xlsx";

    @Test
    public void create() throws Exception {
        try(XSSFWorkbook workbook = new XSSFWorkbook()){
            FileOutputStream out = new FileOutputStream(new File("G:\\data\\t.xlsx"));
            XSSFSheet spreadsheet = workbook.createSheet("用户");
            Map<String, Object[] > empinfo = new TreeMap<>();
            empinfo.put( "1", new Object[] {
                    "EMP ID", "EMP NAME", "DESIGNATION" });
            empinfo.put( "2", new Object[] {
                    "tp01", "Gopal", "Technical Manager" });
            empinfo.put( "3", new Object[] {
                    "tp02", "Manisha", "Proof Reader" });
            empinfo.put( "4", new Object[] {
                    "tp03", "Masthan", "Technical Writer" });
            empinfo.put( "5", new Object[] {
                    "tp04", "Satish", "Technical Writer" });
            empinfo.put( "6", new Object[] {
                    "tp05", "Krishna", "Technical Writer" });
            Set<String> keyid = empinfo.keySet();
            int rowid = 0;
            XSSFRow row;
            for (String key : keyid) {
                row = spreadsheet.createRow(rowid++);
                Object[] objectArr = empinfo.get(key);
                int cellid = 0;
                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String)obj);
                }
            }

            //set print area with indexes
            workbook.setPrintArea(
                    0, //sheet index
                    0, //start column
                    3, //end column
                    0, //start row
                    5 //end row
            );
            //set paper size
            spreadsheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
            //set display grid lines or not
            spreadsheet.setDisplayGridlines(true);
            //set print grid lines or not
            spreadsheet.setPrintGridlines(true);

            workbook.write(out);
            out.close();
            System.out.println("written successfully");
        }

    }

    @Test
    public void poi() throws Exception {
        List<Row> rowList = new ArrayList<>();
        try(XSSFWorkbook wb = new XSSFWorkbook(EXCEL_FILE_PATH)){
            for (int t = 0; t < wb.getNumberOfSheets(); t++) {
                Sheet sheet = wb.getSheetAt(t);
                int lastRowNum = sheet.getLastRowNum();
                // 循环读取
                for (int i = 0; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        rowList.add(row);
                        System.out.println("第" + (i + 1) + "行：");
                        // 获取每一单元格的值
                        for (int j = 0; j < row.getLastCellNum(); j++) {
                            String value = getCellValue(row.getCell(j));
                            System.out.print(value + " | ");
                        }
                        System.out.println();
                    }
                }
            }
        }
    }

    private String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    result = cell.getStringCellValue();
                    break;
                case NUMERIC:
//                    result = cell.getNumericCellValue();
                    result = new DecimalFormat("#.#########").format(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    result = cell.getCellFormula();
                    break;
                case ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

}
