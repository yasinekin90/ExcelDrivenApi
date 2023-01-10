import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReusableMethods {

    public static JsonPath rawToJson(Response response){
        JsonPath js=new JsonPath(response.asString());

        return js;
    }

    public static Map<String,Object> addBodyToMap(String name,String isbn,String aisle,String author){
        Map<String,Object> map=new HashMap<>();

        map.put("name",name);
        map.put("isbn",isbn);
        map.put("aisle",aisle);
        map.put("author",author);

        return map;

    }

    public static ArrayList<String> getData(String testcaseName,String sheetname) throws IOException {

        ArrayList<String> a = new ArrayList<>();
        FileInputStream fis = new FileInputStream("C:\\Users\\user\\IdeaProjects\\ExcelDriven\\apiExcel.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int numberOfSheets = workbook.getNumberOfSheets();

        for (int i = 0; i < numberOfSheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetname)) {
                XSSFSheet sheet = workbook.getSheetAt(i);//sheet is collction of rows
                //Identfy Testcases column by scanning the entire first row
                Iterator<Row> rows = sheet.iterator();//row is collection of cells
                Row firstRow = rows.next();

                Iterator<Cell> cellIterator = firstRow.cellIterator();
                int k = 0;
                int column = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getStringCellValue().equalsIgnoreCase("TestCases")) {
                        //desired column
                        column = k;
                    }
                    k++;
                }

                //once column is identified then scan entire test cases column to identfy purchase test case row
                while (rows.hasNext()) {
                    Row r = rows.next();
                    if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testcaseName)) {
                        //after you grab purchase testcase row=pull all the data of that row and feed into test
                        Iterator<Cell> cv = r.cellIterator();
                        while (cv.hasNext()) {
                            Cell c = cv.next();
                            if(c.getCellType()== CellType.STRING){
                                a.add(c.getStringCellValue());
                            }else{
                                a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                            }

                        }

                    }
                }
            }

        }
        return a;
    }

}
