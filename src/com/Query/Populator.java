package com.Query;

import java.util.LinkedList;

import com.Object.ReportInstance;
import com.Storage.MyTable;
import com.Storage.ObjectAlreadyInitializedException;

public class Populator {
	private MyTable table;

	public Populator(ReportInstance ri)
	{
		
	}
	
	public void populate(SourceTable st)
	{
		int r = st.getNumOfRows();
		try {
		table.init(r);
		
		for (int i = 0; i < r; i++)
		{
			LinkedList<Object> row = st.getRow(i);
			
		}
		} catch (ObjectAlreadyInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
