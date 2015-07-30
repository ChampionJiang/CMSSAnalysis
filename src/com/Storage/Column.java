package com.Storage;

import java.io.PrintStream;
import java.util.UUID;

public abstract class Column {
	enum ColumnType{
		ATTRIBUTE,
		METRIC
	}
	private String name;
	private ColumnType type;
	private UUID id;
	
	public abstract <T> void setData(int r, T val);
	public abstract void Print(int r, PrintStream s);
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
}
