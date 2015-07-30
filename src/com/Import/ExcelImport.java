package com.Import;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.Object.ReportInstance;

import jspSmart.File;
import jspSmart.SmartUpload;

public class ExcelImport {
	protected byte m_binArray[];
	protected HttpServletRequest m_request;
	protected HttpServletResponse m_response;
	protected ServletContext m_application;
	protected XSSFWorkbook m_workbook;
	XSSFSheet m_currentsheet;
	protected SmartUpload m_su;
	
	private ReportInstance reportInstance;
	
	public final void initialize(PageContext pagecontext) throws ServletException {
		m_application = pagecontext.getServletContext();
		m_request = (HttpServletRequest) pagecontext.getRequest();
		m_response = (HttpServletResponse) pagecontext.getResponse();
		
		m_su = new SmartUpload();
		
		m_su.initialize(pagecontext);
		
		try {
			m_su.setAllowedFilesList("xls,xlsx");
			m_su.upload();
			
			File file = m_su.getFiles().getFile(0);
			
			m_workbook = new XSSFWorkbook(file.getInputStream());
			
			} catch (Exception e){
			}
	}
	
	public int getNumOfSheets()
	{
		return m_workbook.getNumberOfSheets();
	}
	
	public void setCurrentSheet(int sheet)
	{
		m_currentsheet = m_workbook.getSheetAt(sheet);
	}
	
	public int getNumOfRows()
	{
		return this.m_currentsheet.getPhysicalNumberOfRows();
	}
	
	public LinkedList<String> getRow(int r)
	{
		LinkedList<String> res = new LinkedList<String>();
		XSSFRow row = m_currentsheet.getRow(r);  
		
		//int first = row.getFirstCellNum();
		int last = row.getLastCellNum();
		
		String cellNovalue="";
		for (int i = 0; i < last; i++)
		{
			XSSFCell cell = row.getCell(i);
			try {  
	               cellNovalue = cell.getStringCellValue();  
	           } catch (IllegalStateException e) {  
	                try {  
	                    double dcellNovalue = cell.getNumericCellValue();  
	                    cellNovalue = String.valueOf(dcellNovalue);  
	                } catch (IllegalStateException e2) {  
	                    cellNovalue = "";  
	                    //e.printStackTrace();  
	                }  
	            } catch (Exception e3) {  
	                cellNovalue = "";  
	                //e3.printStackTrace();  
	            }  

	           res.add(cellNovalue);				
		}
		
			
		return res;
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
	}
}
