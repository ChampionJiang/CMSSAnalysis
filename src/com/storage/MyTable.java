package com.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	public Column AddColumn(MyColumn.DataType dt)
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
		JSONObject table = new JSONObject();
		table.put("name", "table upload");
		JSONArray cols = new JSONArray();
		for (int i = 0; i < columns.size(); i++){
			Column c = columns.get(i);
			JSONObject col = c.toJSON();
			cols.put(col);
		}
		table.put("columns", cols);
		JSONObject result = new JSONObject();
		result.put("tb", table);
		return result.toString();
	}
	
	public static void main(String args[]){
		
//		Object o[] = new Object[4];
//		o[0] = "abc";
//		o[1] = 10;
//		
//		System.out.println(o[0].toString()+ "  " + o[1].toString());
//		return;
		MyTable mt = new MyTable(10);
		
		Random rdm = new Random();
		MyColumn.DataType dt[] = {MyColumn.DataType.STRING, MyColumn.DataType.DOUBLE, MyColumn.DataType.INTEGER};
		
		for (int i = 0; i < 3; i++)
		{
			Column col = mt.AddColumn(dt[i]);
			
			for(int j = 0; j < 10; j++)
			{
				col.setData(j, rdm.nextDouble()*100);
			}
		}
		
		System.out.println(mt.toJSON());
		
	}
}