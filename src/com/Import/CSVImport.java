package com.Import;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.connector.Connector;
import com.jsp.smart.File;
import com.opencsv.CSVReader;
import com.storage.MyTable;
import com.storage.ObjectAlreadyInitializedException;

public class CSVImport implements Connector {
	
	public CSVImport(File file) throws IOException {
		CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
		// if the first line is the header
		String[] header = reader.readNext();

		// iterate over reader.readNext until it returns null
		String[] line = reader.readNext();
		
		
		
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
