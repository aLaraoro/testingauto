package data;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalTime;
import java.util.*;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
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
	private List<Sheet> sheets = new ArrayList<Sheet>();
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
			System.out.println(i);
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
		int count = workbook.getNumberOfSheets();
		DataFormatter dataFormatter = new DataFormatter();
		FileOutputStream output = new FileOutputStream(data);
		Sheet sheet = null;
		for(int i=0;i<numSheets;i++) {
			
			Sheet sheet1 = newWorkBook.createSheet();
			Sheet oldSheet = workbook.getSheetAt(i);
			createSheetContent(oldSheet,sheet1);
			
		}
		if(count<2) {
			
			sheet = newWorkBook.createSheet("Result");

			List<Object> names = new ArrayList<Object>();
			names.add("TC Name");
			names.add("Success");
			names.add("Day");
			names.add("Time");
			
			this.createRow(0, names, sheet);
			this.createRow(1, list, sheet);
			
			
			
			System.out.println("Numero hojas: "+newWorkBook.getNumberOfSheets());
			

			
		}else {
			
			sheet = newWorkBook.getSheet("Result");
			int n = sheet.getLastRowNum();
			System.out.println(n);
			
		}
		
		newWorkBook.write(output);
		output.close();
		newWorkBook.close();
		
		
	}
	
	public void createRow(int a, List<Object> arrayList, Sheet sheet) {
		
		sheet.createRow(a);
		
		Row row = sheet.getRow(a);
		
		for(int i=0;i<arrayList.size();i++) {
			
			row.createCell(i);
			Cell cell = row.getCell(i);
			int widt = 0;
			if(a==0 || i==0) {
				String cellValue = (String) arrayList.get(i);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(cellValue);
				widt += cellValue.length();
				
			}else {
				
				if(i==1) {
					Boolean cellValue = (Boolean) arrayList.get(i);
					cell.setCellType(CellType.BOOLEAN);
					cell.setCellValue(cellValue);
					widt += cellValue.toString().length();
					
				}else{
					String cellValue = (String) arrayList.get(i);
					cell.setCellType(CellType.NUMERIC);
					cell.setCellValue(cellValue);
					widt += cellValue.length();
					
				}
				
			}
			
			
			
			int width = sheet.getColumnWidth(i);
			
			System.out.println(width + " vs " + widt);
			if(widt > width) {
				System.out.println("Texto es mayor que celda");
				width = widt;
								
			}
			
			sheet.setColumnWidth(i, width+40);
			
			
			
			
			
			
			
			
		}
		
		
	}
	
	public void createSheetContent(Sheet oldSheet, Sheet newSheet) {
		
		DataFormatter dataFormatter = new DataFormatter();
		Iterator<Row> rowIterator = oldSheet.rowIterator();
		int rowNum= oldSheet.getLastRowNum()+1;
		for(int i=0;i<rowNum;i++) {
			
			Row row = oldSheet.getRow(i);
			newSheet.createRow(i);
			int colNum = row.getLastCellNum();
			for(int j=0;j<colNum;j++) {
				Cell cell = row.getCell(j);
				String cellValue = dataFormatter.formatCellValue(cell);
				
				newSheet.getRow(i).createCell(j);
				newSheet.getRow(i).getCell(j).setCellValue(cellValue);
				
			}
			
			
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
