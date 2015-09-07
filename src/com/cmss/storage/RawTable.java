package com.cmss.storage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cmss.engine.SubsetEngine;

public class RawTable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4054049728149338294L;
	private ArrayList<Column> columns;
	private boolean initialized;
	
	private int rowCount;
	private UUID uuid;
	private String name;
	
	public RawTable(int r)
	{
		initialized = false;
		try {
			init(r);
		} catch (ObjectAlreadyInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public RawTable()
	{
		initialized = false;
		rowCount = -1;
		columns = null;
		
		uuid = UUID.randomUUID();
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
	
	public int getNumOfColumns() {
		return this.columns.size();
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
	
	public List<String> getColumnNames() {
		ArrayList<String> res = new ArrayList<String>();
		
		for (Column c: columns) {
			res.add(c.getName());
		}
		
		return res;
	}
	
	public static void main(String args[]) throws IOException{
		String filename = "test1.json";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		
		StringBuilder sb = new StringBuilder();
		while (true) {
			String line = br.readLine();	
			if (line == null)
				break;
			
			sb.append(line);
		}
		
		String jsonstr = sb.toString();
		System.out.println(jsonstr);
		JSONArray jsonobj = new JSONArray(jsonstr);
		
		int len = jsonobj.length();
		for (int i = 0; i < len; i++) {
			JSONObject obj = jsonobj.getJSONObject(i);
			System.out.println(obj.get("Date"));
		}
		
		br.close();
//		Object o[] = new Object[4];
//		o[0] = "abc";
//		o[1] = 10;
//		
//		System.out.println(o[0].toString()+ "  " + o[1].toString());
//		return;
//		RawTable mt = new RawTable(10);
//		
//		Random rdm = new Random();
//		SimpleColumn.DataType dt[] = {SimpleColumn.DataType.STRING, SimpleColumn.DataType.DOUBLE, SimpleColumn.DataType.INTEGER};
//		
//		for (int i = 0; i < 3; i++)
//		{
//			Column col = mt.AddColumn(dt[i]);
//			
//			col.setName("col"+i);
//			for(int j = 0; j < 10; j++)
//			{
//				col.setData(j, rdm.nextDouble()*100);
//			}
//		}
//		
//		SubsetEngine se = new SubsetEngine();
//		
//		Integer attrs[] = {0,1};
//		Integer metrics[] = {2};
//		
////		RawTable result = se.subset(mt, attrs, metrics);
////		
////		System.out.println(mt.toJSON());
////		System.out.println(result.toJSON());
//		FileInputStream fis = new FileInputStream("table");
//		ObjectInputStream ois = new ObjectInputStream(fis);
//		
//		System.out.println(mt.toJSON());
//		try {
//			RawTable rt = (RawTable)ois.readObject();
//			System.out.println(rt.toString());
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		FileOutputStream fos = new FileOutputStream("table");
//		ObjectOutputStream oos = new ObjectOutputStream(fos);
//		
//		oos.writeObject(mt);
//		
//		oos.flush();
//		oos.close();
		System.out.println("finished");
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
