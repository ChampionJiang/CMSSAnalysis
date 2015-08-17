package com.storage;

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
	
	public abstract <E> void setData(int r, E val);
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
}
