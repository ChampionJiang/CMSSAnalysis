package com.Storage;


import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MyColumn extends Column implements Serializable{
	public static enum DataType {
		INTEGER,
		DOUBLE,
		FLOAT,
		STRING,
		DATETIME
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -7587427339442381329L;
	DataType type;
	Object [] data;
	
	
	public MyColumn(int r, DataType t)
	{
		data = new Object[r];
		type = t;
	}
	
	public void SetData(int r, Object v)
	{
		data[r] = v;
	}

	@Override
	public <T> void setData(int r, T val) {
		// TODO Auto-generated method stub
		data[r] = val;
	}

	@Override
	public void Print(int r, PrintStream s) {
		// TODO Auto-generated method stub
		s.print(data[r]);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for (Object o: data){
			try{
				sb.append(o.toString()+ " ");
			} catch (Exception e)
			{
				sb.append("null ");
			}
		}
		
		return sb.toString();
	}
}
