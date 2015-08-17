package com.connector;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.storage.MyTable;
import com.storage.ObjectAlreadyInitializedException;

public interface Connector {
	enum ConnectorType{EXCEL, RMDB}
	
	public List<String> getColumnNames();
	public MyTable Transform() throws ObjectAlreadyInitializedException;
	public void initialize(PageContext pagecontext) throws ServletException;
}
