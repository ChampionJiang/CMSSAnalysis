package com.cmss.query;

import java.util.LinkedList;

import com.cmss.Import.ExcelImport;

public class SourceTable {
	
	private ExcelImport excelImport;
	
	public int getNumOfRows()
	{
		return excelImport.getNumOfRows();
	}
	
	public LinkedList<Object> getRow(int r)
	{
		LinkedList<Object> result = new LinkedList<Object>();
		
		return result;
	}
}
