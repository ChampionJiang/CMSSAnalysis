package com.cmss.query;

//import java.util.LinkedList;

import com.cmss.object.ReportInstance;
import com.cmss.storage.RawTable;
import com.cmss.storage.ObjectAlreadyInitializedException;

public class Populator {
	private RawTable table;

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
			//LinkedList<Object> row = st.getRow(i);
			
		}
		} catch (ObjectAlreadyInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
