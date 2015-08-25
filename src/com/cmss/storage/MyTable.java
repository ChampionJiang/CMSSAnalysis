package com.cmss.storage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cmss.engine.SubsetEngine;

public class MyTable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4054049728149338294L;
	private ArrayList<Column> columns;
	private boolean initialized;
	
	private int rowCount;
	
	public MyTable(int r)
	{
		initialized = false;
		try {
			init(r);
		} catch (ObjectAlreadyInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MyTable()
	{
		initialized = false;
		rowCount = -1;
		columns = null;
	}
	
	public void init(int r) throws ObjectAlreadyInitializedException
	{
		if (initialized)
			throw new ObjectAlreadyInitializedException("MyTable");
			
		rowCount = r;
		columns = new ArrayList<Column>();
		
		initialized = true;
	}
	
	public int getNumOfRows() {
		return this.rowCount;
	}
	
	public void Print()
	{
		for (Column c: columns)
		{
			System.out.print(c.getName()+ " ");
		}
		System.out.println();
		
		int r = Math.min(100, rowCount);
		for (int i = 0; i < r; i++) {
			for(Column c: columns)
			{
				c.Print(i, System.out);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public Column AddColumn(SimpleColumn.DataType dt)
	{
		Column col = ColumnFactory.CreateColumn(dt, rowCount);
		
		columns.add(col);
		
		return col;
	}
	
	public Column getColumn(int idx)
	{
		return columns.get(idx);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for(Column c: columns) {
			sb.append(c.getName() + " ");
			sb.append(c.toString());
		}
		
		return sb.toString();
	}
	
	public String toJSON()
	{
		JSONArray table = new JSONArray();
		
		
		for (int r = 0; r < rowCount; r++){
			JSONObject row = new JSONObject();
			for (int c = 0; c < columns.size(); c++) {
				Column col = columns.get(c);
				
				row.put(col.getName(), col.getData(r));
				
			}
			table.put(row);
		}
		
		return table.toString();
	}
	
	public static void main(String args[]) throws IOException{
		
//		Object o[] = new Object[4];
//		o[0] = "abc";
//		o[1] = 10;
//		
//		System.out.println(o[0].toString()+ "  " + o[1].toString());
//		return;
		MyTable mt = new MyTable(10);
		
		Random rdm = new Random();
		SimpleColumn.DataType dt[] = {SimpleColumn.DataType.STRING, SimpleColumn.DataType.DOUBLE, SimpleColumn.DataType.INTEGER};
		
		for (int i = 0; i < 3; i++)
		{
			Column col = mt.AddColumn(dt[i]);
			
			col.setName("col"+i);
			for(int j = 0; j < 10; j++)
			{
				col.setData(j, rdm.nextDouble()*100);
			}
		}
		
		SubsetEngine se = new SubsetEngine();
		
		int attrs[] = {0,1};
		int metrics[] = {2};
		
		MyTable result = se.subset(mt, attrs, metrics);
		
		System.out.println(mt.toJSON());
		System.out.println(result.toJSON());
		
//		FileWriter fw = new FileWriter("WebContent\\app\\asset\\test\\test.json");
//		BufferedWriter bw = new BufferedWriter(fw);
//		
//		bw.write(mt.toJSON());
//		
//		bw.flush();
//		bw.close();
//		fw.close();
		
	}
}
