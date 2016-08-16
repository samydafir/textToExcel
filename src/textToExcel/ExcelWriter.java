package textToExcel;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelWriter {
	
	private Workbook wb;
	private Sheet eval;
	private Row currRow;
	private int rowCount;
	private int cellCount;
	
	public ExcelWriter(){
		wb = new HSSFWorkbook();
		eval = wb.createSheet("Rechnung");
		cellCount = 9;
		rowCount = 4;
		currRow = eval.createRow(0);
		currRow.createCell(0).setCellValue("Auswertung Canon Rechnung");
		currRow = eval.createRow(3);
		currRow.createCell(0).setCellValue("Location");
		currRow.createCell(1).setCellValue("Serial Number");
		currRow.createCell(2).setCellValue("Before");
		currRow.createCell(6).setCellValue("After");
		
		currRow = eval.createRow(rowCount);
		currRow.createCell(2).setCellValue("A3 Black");
		currRow.createCell(3).setCellValue("A4 Black");
		currRow.createCell(4).setCellValue("A3 Color");
		currRow.createCell(5).setCellValue("A4 Color");
		currRow.createCell(6).setCellValue("A3 Black");
		currRow.createCell(7).setCellValue("A4 Black");
		currRow.createCell(8).setCellValue("A3 Color");
		currRow.createCell(9).setCellValue("A4 Color");
	}
	
	public void writeInvoiceNumber(String number){
		currRow = eval.createRow(1);
		currRow.createCell(0).setCellValue("Invoice Number");
		currRow.createCell(1).setCellValue(number);
	}
	
	public void writeInvoiceDate(String date){
		currRow = eval.createRow(2);
		currRow.createCell(0).setCellValue("Invoice Date");
		currRow.createCell(1).setCellValue(date);
	}
	
	public void writePrinter(String printer){
		rowCount++;
		currRow = eval.createRow(rowCount);
		currRow.createCell(1).setCellValue(printer);
	}
	
	public void writeLocation(String location){
		currRow.createCell(0).setCellValue(location);
	}
	
	public void writeDetails(int type, double before, double after){
		switch (type) {
		case 112:
			createCounterCell(2, before, after);
			break;
		case 113:
			createCounterCell(3, before, after);
			break;
		case 122:
			createCounterCell(4, before, after);
			break;
		case 123:
			createCounterCell(5, before, after);
			break;
		}
	}
	
	private void createCounterCell(int start, double before, double after) {
		currRow.createCell(start).setCellValue(before);
		currRow.createCell(start + 4).setCellValue(after);
	}
	
	public void clearEmptyCells(){
		boolean rowEmpty = true;
		Cell currCell;
		for(int i = 5; i <= rowCount; i++){
			rowEmpty = true;
			for(int j = 2; j <= cellCount; j++){
				currCell = eval.getRow(i).getCell(j);
				if(currCell != null && currCell.getCellType() != Cell.CELL_TYPE_BLANK){
					rowEmpty = false;
					break;
				}
			}
			if(rowEmpty){
				eval.removeRow(eval.getRow(i));
			}
		}
	}
	
	public void export(String path) throws IOException{		
		FileOutputStream evaluation = new FileOutputStream(path + ".xls");
		wb.write(evaluation);
		evaluation.close();
	}
}
