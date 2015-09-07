package com.cmss.Import;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.cmss.connector.Connector;
import com.cmss.storage.Column;
import com.cmss.storage.RawTable;
import com.cmss.storage.ObjectAlreadyInitializedException;
import com.cmss.storage.Column.DataType;
import com.jsp.smart.File;
import com.opencsv.CSVReader;

public class CSVImport implements Connector {
	
	private final CSVReader reader;
	
	public CSVImport(File file) {
		reader = new CSVReader(new InputStreamReader(file.getInputStream()));
		// iterate over reader.readNext until it returns null	
	}

	public CSVImport(String file) throws FileNotFoundException {
		reader = new CSVReader(new FileReader(file));
	}
	public void readAndPrint() throws IOException {
		String[] line = reader.readNext();
		while (line != null) {
			for (String s: line) {
				System.out.print(s + "  ");
			}
			
			System.out.println();
			line = reader.readNext();
		}
	}
	@Override
	public List<String> getColumnNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RawTable Transform() throws ObjectAlreadyInitializedException {
		// TODO Auto-generated method stub
		RawTable table = new RawTable();
		try {
			// it's inefficient to read all data before we can compose the table
			// to do: to use composite table
			List<String[]> lines = reader.readAll();
			int rowCount = lines.size();
			
			if (rowCount < 1)
				return table;
			
			table.init(rowCount-1);
			
			boolean headInitialized = false;
			int r = 0;
			for (String[] line: lines) {
				if(!headInitialized) {
					headInitialized = true;
					for (String name: line) {
						Column c = table.AddColumn(DataType.DOUBLE);
						c.setName(name);
					}
				}
				else {
					for (int idx = 0; idx < line.length; idx++) {
						Column c = table.getColumn(idx);
						
						String cell = line[idx];
						Object obj = null;
						try {
							int iVal = Integer.parseInt(cell);
							obj = iVal;
						} catch (Exception e1) {
							try {
								double dVal = Double.parseDouble(cell);
								obj = dVal;
							} catch (Exception e2) {
								obj = cell;
							}
						}
						c.setData(r, obj);
					}
					r++;
				}
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;
	}
	
	public static void main(String[] args) {
		try {
			CSVImport csvimpt = new CSVImport("test.csv");
			RawTable table = csvimpt.Transform();
			System.out.println(table.toJSON());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ObjectAlreadyInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}