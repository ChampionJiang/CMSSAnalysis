package com.storage;


import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class SimpleColumn extends Column implements Serializable{
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
	
	
	public SimpleColumn(int r, DataType t)
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
	
	@Override
	public JSONObject toJSON()
	{
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < data.length; i++){
			array.put(data[i]);
		}
		
		JSONObject obj = new JSONObject();
		
		return obj.put(this.getName(), array);
	}

	@Override
	public String getDataAsString(int r) {
		// TODO Auto-generated method stub
		return data[r] == null ? null : data[r].toString();
	}

	@Override
	public Object getData(int r) {
		// TODO Auto-generated method stub
		return data[r];
	}
}
