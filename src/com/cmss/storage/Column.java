package com.cmss.storage;

import java.io.PrintStream;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Column {
	enum ColumnType{
		ATTRIBUTE,
		METRIC
	}
	private String name;
	private ColumnType type;
	private UUID id;
	
	public abstract void setData(int r, Object val);
	public abstract void Print(int r, PrintStream s);
	
	public abstract String toString();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ColumnType getType() {
		return type;
	}
	public void setType(ColumnType type) {
		this.type = type;
	}
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public abstract SimpleColumn.DataType getDataType();
	public abstract String getDataAsString(int r);
	
	public abstract Object getData(int r);
}
