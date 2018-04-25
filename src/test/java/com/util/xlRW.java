package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class xlRW {
	
	static String XLData[][];
	public static int XLRows;
	public static int XLCols;
	public int count;
	String Read = "D:/workspace/ExcelReports/TestCases/testCases.xlxs";
	String Write = "D:/workspace/ExcelReports/TestCases/Results/testResults.xlxs";

	public void read() throws Exception {
		xlRead(Read);
	}
	
	public void write() throws Exception {
		xlwrite(Write,XLData);
	}

	
	public static void xlRead(String path) throws Exception
	{
		File myxl = new File(path);
		FileInputStream myStream = new FileInputStream(myxl);
		XSSFWorkbook myworkbook = new XSSFWorkbook(myStream);
		
		
		XSSFSheet sheet = myworkbook.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();
		
		XLRows = sheet.getLastRowNum() + 1;
		XLCols = sheet.getRow(0).getLastCellNum();
		
		System.out.println("My Rows are "+XLRows);
		System.out.println("My Columns are "+XLCols);
		XLData = new String[XLRows][XLCols];
		int i = 0;
		int j = 0;

		Myloop:
		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
		
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
				{
					XLData[i][j] = cell.getStringCellValue();
					System.out.print(XLData[i][j]+" ");
				}
				else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
				{
					XLData[i][j] = Double.toString(cell.getNumericCellValue());
					System.out.print(XLData[i][j]+" ");
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
					System.out.println();
					System.out.println("Row "+(i+1)+" and Column "+(j+1)+" has unknow data type");
					break Myloop;
				}
				
				j++;
			} 
			
			i++;
			j=0;
			System.out.println();
			
		}
	
	}
	
	public static void xlwrite(String XLWriteOn, String[][] xldata) throws Exception{
	
		
		File outFile = new File(XLWriteOn);
		XSSFWorkbook myworkbook = new XSSFWorkbook();
		XSSFSheet osheet = myworkbook.createSheet("Results");
		
		

		//iterating r number of rows
		for (int r=0;r < XLRows; r++ )
		{
			XSSFRow row = osheet.createRow(r);

			//iterating c number of columns
			for (int c=0;c < XLCols; c++ )
			{
				XSSFCell cell = row.createCell(c);
	
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(xldata[r][c]);
				System.out.println("Writting row "+(r+1)+" and column "+(c+1));
				//osheet.autoSizeColumn(c);
				
				
			} 
		}
		
		FileOutputStream fOut = new FileOutputStream(outFile);

		//Set column width
		for(int i=0;i<XLCols;i++) {
		osheet.setColumnWidth(i, 5000);	
		}
		
		//write this workbook to an Outputstream.
		myworkbook.write(fOut);
		fOut.flush();
		fOut.close();
		
	}

	
}
