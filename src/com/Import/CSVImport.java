package com.Import;

import java.util.List;

import com.connector.Connector;
import com.jsp.smart.File;
import com.storage.MyTable;
import com.storage.ObjectAlreadyInitializedException;

public class CSVImport implements Connector {
	
	public CSVImport(File file) {
		System.out.println("csv file is not supported now");
	}

	@Override
	public List<String> getColumnNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyTable Transform() throws ObjectAlreadyInitializedException {
		// TODO Auto-generated method stub
		return null;
	}
}
