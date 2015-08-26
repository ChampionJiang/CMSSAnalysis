package com.cmss.Import;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cmss.connector.Connector;
import com.cmss.storage.Column;
import com.cmss.storage.MyTable;
import com.cmss.storage.ObjectAlreadyInitializedException;
import com.cmss.storage.SimpleColumn;
import com.jsp.smart.File;

public class ExcelImport implements Connector {
	//protected byte m_binArray[];
	protected XSSFWorkbook workbook;
	XSSFSheet currentsheet;
	protected String sheetName;
	//File file;
	String fileName;
	InputStream inputStream;
	//DbDao dbdao;
	
	public ExcelImport(File file) throws IOException {
		fileName = file.getFileName();
		inputStream = file.getInputStream();
		init();
	}
		
	public ExcelImport(String fileName) throws IOException {
		this.fileName = fileName;
		inputStream = new FileInputStream(fileName);
		
		init();
	}
	private void init() throws IOException
	{
		workbook = new XSSFWorkbook(inputStream);
		setCurrentSheet(0);
	}
	
	public int getNumOfSheets()
	{
		return workbook.getNumberOfSheets();
	}
	
	public void setCurrentSheet(int sheet)
	{
		currentsheet = workbook.getSheetAt(sheet);
		sheetName = fileName + currentsheet.getSheetName();
	}
	
	public int getNumOfRows()
	{
		return currentsheet.getPhysicalNumberOfRows();
	}	
	
	private static void ReadAndPrintExcelFile(String filePath, String sSheetName) {  
        try {  
            FileInputStream fis = new FileInputStream(filePath);  
            XSSFWorkbook wb = new XSSFWorkbook(fis);  
            XSSFSheet sheet = wb.getSheet(sSheetName);  
            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {  
                String cellNovalue = "";  
                XSSFRow row = sheet.getRow(i);  
                Iterator<Cell> it = row.cellIterator();  
                while (it.hasNext()) {  
                    XSSFCell cell = (XSSFCell) it.next();  
                    try {  
                        cellNovalue = cell.getStringCellValue();  
                    } catch (IllegalStateException e) {  
                        try {  
                            double dcellNovalue = sheet.getRow(i).getCell(0)  
                                    .getNumericCellValue();  
                            cellNovalue = String.valueOf(dcellNovalue);  
                        } catch (IllegalStateException e2) {  
                            cellNovalue = "";  
                            e.printStackTrace();  
                        }  
                    } catch (Exception e3) {  
                        cellNovalue = "";  
                        e3.printStackTrace();  
                    }  
  
                    System.out.print(cellNovalue);
                    if (it.hasNext())
                    	System.out.print(",");
                }
                System.out.println();
            }  
            
            wb.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
	
	public static void main(String[] args)
	{
		ReadAndPrintExcelFile("test.xlsx","sheet1");  
		//ExcelImport ei = new ExcelImport();
	}

	@Override
	public MyTable Transform() throws ObjectAlreadyInitializedException {
		// TODO Auto-generated method stub
		int rows = this.getNumOfRows();
		MyTable table = new MyTable();
		table.init(rows-1);
		
		if (rows <= 1)
			return table;
		
		// assume the first row defines the header, i.e. the names of each column
		XSSFRow row = currentsheet.getRow(0);
		int cols = row.getLastCellNum();
		
		SimpleColumn.DataType dt[] = new SimpleColumn.DataType[cols];
		// need a way to figure out the datatypes of each column
		for (int c = 0; c < cols; c++)
		{
			Column col = table.AddColumn(dt[c]);
			col.setName(row.getCell(c).getStringCellValue());
			
			dt[c] = SimpleColumn.DataType.DOUBLE;
		}
		
		// ok, we've created the columns, let's fill in the data
		
		for (int r = 1; r < rows; r++)
		{
			row = currentsheet.getRow(r);
			
			for (int c = 0; c < cols; c++)
			{
				Column col = table.getColumn(c);
				String sVal = null;
				double dVal = 0.0;
				XSSFCell cell = row.getCell(c);
				try{
					int cellType = cell.getCellType();
					switch(cellType)
					{
					case HSSFCell.CELL_TYPE_STRING:
						{
							sVal = cell.getStringCellValue();
							col.setData(r-1, sVal);
						}
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						col.setData(r-1, null);
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						{
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
						
								String str = sdf.format(date);
							//	System.out.println(str);
								col.setData(r-1, str);
							} else {
								dVal = cell.getNumericCellValue();
								col.setData(r-1, dVal);
							}
						}
						break;
					default:
						break;
					}
				}
				catch (Exception e)
				{
					col.setData(r-1, null);
				}
			} 
		}
		
		flushToDB(table);
		
		return table;
	}

	private void flushToDB(MyTable table)
	{
		//dbdao.modify(sql, args);
	}
	@Override
	public List<String> getColumnNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
