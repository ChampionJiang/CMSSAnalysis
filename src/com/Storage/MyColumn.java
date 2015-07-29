package com.Storage;


import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MyColumn <Type> extends Column implements Serializable{
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
	ArrayList<Type> data;
	
	
	public MyColumn(int r, DataType t)
	{
		data = new ArrayList<Type>(r);
		type = t;
	}
	
	public void SetData(int r, Type v)
	{
		data.set(r, v);
	}

	@Override
	public <T> void setData(int r, T val) {
		// TODO Auto-generated method stub
		data.add(r, (Type) val);
	}

	@Override
	public void Print(int r, PrintStream s) {
		// TODO Auto-generated method stub
		s.print(data.get(r));
	}
	
}
