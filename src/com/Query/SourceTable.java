package com.Query;

import java.util.LinkedList;

import com.Import.ExcelImport;

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
