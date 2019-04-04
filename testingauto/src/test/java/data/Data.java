package data;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.*;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Data {

	public String data;
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
			sheets.add(workbook.getSheetAt(i));


		}
		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);

		// Create a DataFormatter to format and get each cell's value as String
		DataFormatter dataFormatter = new DataFormatter();

		// 1. You can obtain a rowIterator and columnIterator and iterate over them
		Row topRow = sheet.getRow(0);
		int lastRow = sheet.getLastRowNum();

		for(int i=1; i<=lastRow;i++) {

			Row row = sheet.getRow(i);
			/*String name = "";
			for(int a=0;a<row.getLastCellNum();a++) {

				name += row.getCell(a) + " ";

			}

			System.out.println(name);
*/
			// Now let's iterate over the columns of the current row
			Map<String,String> map = new HashMap<String,String>();

			for (int j =0;j<row.getLastCellNum();j++) {
				Cell cell = row.getCell(j);

				String cellValue = dataFormatter.formatCellValue(cell);
				map.put(topRow.getCell(j).toString(), cellValue);
			}


			data.add(map);    	  	

		}


		return data;




	}

	public List<String> getLoginData(List<Map<String,String>> list, Boolean isCorrect){

		List<String> loginList = new ArrayList<String>();


		for(int i=0;i<list.size();i++) {

			Boolean bool = Boolean.parseBoolean(list.get(i).get("assert"));

			if(isCorrect == bool) {
				loginList.add(list.get(i).get("userName"));
				loginList.add(list.get(i).get("password"));
				loginList.add(list.get(i).get("expectedResult"));
				break;

			}


		}

		return loginList;		


	}
	
	public List<Map<String,String>> autoList(List<String> map, int times){

		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(int i=0;i<times;i++) {

			Map<String,String> nwMap = new HashMap<String,String>();
			nwMap.put("userName",map.get(0));
			nwMap.put("password",map.get(1));
			list.add(nwMap);

		}

		return list;


	}
	
	public Workbook cloneData() throws FileNotFoundException {
		
		Workbook newWorkBook = new XSSFWorkbook();
		int numSheets = workbook.getNumberOfSheets();

		for(int i=0;i<numSheets;i++) {

			newWorkBook.createSheet();
			Sheet sheet1 = newWorkBook.getSheetAt(i);
			String name = workbook.getSheetName(i).toString();
			newWorkBook.setSheetName(i, name);
			Sheet oldSheet = workbook.getSheetAt(i);
			createSheetContent(i,oldSheet,sheet1);

		}
		
		return newWorkBook;
		
	}
	

	public void insertRowMap(Map<Integer,String[]> map) throws IOException {

		Workbook newWorkBook = this.cloneData();
		int numSheets = newWorkBook.getNumberOfSheets();
		DataFormatter dataFormatter = new DataFormatter();
		FileOutputStream output = new FileOutputStream(data);
		Sheet sheet = null;
		List<String> list =  this.toList(map, 1);
		
			
			if(numSheets<2) {

				newWorkBook.createSheet("Result");
				sheet = newWorkBook.getSheet("Result");
				List<String> names = this.toList(map, 0);
				this.createRow(0, names, sheet);
				this.createRow(1, list, sheet);



			}else {

				sheet = newWorkBook.getSheet("Result");
				int n = sheet.getLastRowNum();
				this.createRow(n+1, list, sheet);

			}
			

		newWorkBook.write(output);
		output.close();
		newWorkBook.close();


	}
	
	public void createSheetContent(int numSheet,Sheet oldSheet, Sheet newSheet) {
		
		System.out.println("createSheetContent");
		System.out.println("Nombre hoja: " + oldSheet.getSheetName());

		DataFormatter dataFormatter = new DataFormatter();
		int rowNum= oldSheet.getLastRowNum()+1;

		for(int i=0;i<rowNum;i++) {
			Row row = oldSheet.getRow(i);

			newSheet.createRow(i);
			Row newRow= newSheet.getRow(i);
			int colNum = row.getLastCellNum();

			for(int j=0;j<colNum;j++) {
				Cell cell = row.getCell(j);
				String cellValue = dataFormatter.formatCellValue(cell);
				newRow.createCell(j);
				newRow.getCell(j).setCellValue(cellValue);

			}

		}

	}

	public List<String> toList(Map<Integer,String[]> map, Integer isKey){

		int size = map.size();
		List<String> list = new ArrayList<String>();


		for(int i=0;i<size;i++) {
			String[] array = map.get(i);
			list.add(array[isKey]);


		}


		return list;


	}
	
	
	public void createSheetFlights() throws FileNotFoundException {
		
		Workbook newWorkBook = this.cloneData();
		int numSheets = newWorkBook.getNumberOfSheets();
	
		if(numSheets<3) {
			
			newWorkBook.createSheet("Flights");
			Sheet sheet = newWorkBook.getSheet("Flights");
			
		}

		
	}


	public void createRow(int a, List<String> arrayList, Sheet sheet) {
		//Crea fila en sheet
		sheet.createRow(a);

		//Selecciona la fila
		Row row = sheet.getRow(a);

		for(int i=0;i<arrayList.size();i++) {
			//Crea una celda
			row.createCell(i);

			//Selecciona celda, estilo de fuente
			Cell cell = row.getCell(i);


			//inicia variable tamaño celda
			int width = (int) sheet.getColumnWidth(i)/255;
			int widt=0;
			String cellValue =  arrayList.get(i);
			//Get value and insert into cell
			if(a>0 && (i>1 && i<4)) {


				//DateTime values
				cell.setCellType(CellType.NUMERIC);




			}else{

				//Titles, TC name cells and boolean

				cell.setCellType(CellType.STRING);


			}
			cell.setCellValue(cellValue);
			widt = cellValue.length();

			if(widt > width) {

				if(widt>=255) {

					width = 255;


				}else {

					width = widt+1;

				}

			}else if(widt == width) {

				width++;

			}



			sheet.setColumnWidth(i, width*255);
			sheet.setColumnHidden(i, false);


		}


	}





}
