package com.object;

import com.storage.MyTable;

public class ReportInstance {
	private MyTable table;

	public MyTable getTable() {
		
		if (table == null)
		{
			table = new MyTable();
		}
		return table;
	}

	public void setTable(MyTable table) {
		this.table = table;
	}
}
