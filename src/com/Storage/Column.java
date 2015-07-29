package com.Storage;

import java.io.PrintStream;

public abstract class Column {
	public abstract <T> void setData(int r, T val);
	public abstract void Print(int r, PrintStream s);
}
