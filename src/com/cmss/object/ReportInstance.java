package com.cmss.object;

import com.cmss.storage.RawTable;

public class ReportInstance {
	private RawTable table;

	public RawTable getTable() {
		
		if (table == null)
		{
			table = new RawTable();
		}
		return table;
	}

	public void setTable(RawTable table) {
		this.table = table;
	}
}
