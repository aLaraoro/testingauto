package data;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Data {
	
	public static String data;
	private Workbook workbook;
	private File file;
	private List<Sheet> sheets;
	private int numSheets;
	public Data(String data) throws EncryptedDocumentException, InvalidFormatException, IOException {
		
		this.data = data;
		file= new File(this.data);
		// Creating a Workbook from an Excel file (.xls or .xlsx)
		workbook = WorkbookFactory.create(file);
		
	}
	
	public List<Map<String,String>> getData() throws Exception, InvalidFormatException, IOException{
		
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		       

        // Retrieving the number of sheets in the Workbook
		numSheets = workbook.getNumberOfSheets();
		for(int i = 0;i<numSheets;i++) {
			
			sheets.add(workbook.getSheetAt(i));
			
		}
     // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // 1. You can obtain a rowIterator and columnIterator and iterate over them
        Iterator<Row> rowIterator = sheet.rowIterator();
        Row topRow = sheet.getRow(0);
        int firstRow = sheet.getFirstRowNum();
        int i = 0;
        while (rowIterator.hasNext()) {
        
        		
                Row row = rowIterator.next();
        		if(i>firstRow) {
                
                // Now let's iterate over the columns of the current row
                Iterator<Cell> cellIterator = row.cellIterator();
                int j =0;
                Boolean empty = this.RowEmptyValue(row, dataFormatter);
                if(!empty) {
                	Map<String,String> map = new HashMap<String,String>();
                	
                	while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        
                        String cellValue = dataFormatter.formatCellValue(cell);
                        
                        map.put(topRow.getCell(j).toString(), cellValue);
                        j++;
                    }
                    data.add(map);
                	
                	
                }
                
        		
        		}
        	
        	

            i++;
        }
		return data;
		
		
		
		
	}
	
	public void insertRow(List<Object> list) throws FileNotFoundException, IOException {
		Workbook newWorkBook = new XSSFWorkbook();
		int count = newWorkBook.getNumberOfSheets();
		FileOutputStream output = new FileOutputStream(data);
		if(count<2) {
			
			for(int i=0;i<numSheets;i++) {
				
				Sheet sheet = newWorkBook.createSheet();			
				
			}
			
			Sheet sheet = newWorkBook.createSheet("Result");
			
			System.out.println(newWorkBook.getNumberOfSheets());
			
			newWorkBook.write(output);
			output.close();
			newWorkBook.close();
			
		}
		
		
	}
	
	
	public boolean RowEmptyValue(Row row,DataFormatter dataFormatter) {
		
		Boolean empty = false;
		Iterator<Cell> cellIterator = row.cellIterator();
		while(cellIterator.hasNext()) {
			
            Cell cell = cellIterator.next();

            String cellValue = dataFormatter.formatCellValue(cell);
            if(cellValue == "") {
            	
            	empty = true;
            	
            }
			
			
		}
		
		
		return empty;
		
		
		
		
	}

}
