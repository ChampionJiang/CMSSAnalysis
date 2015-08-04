package com.Connector;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.Storage.MyTable;
import com.Storage.ObjectAlreadyInitializedException;

public interface Connector {
	enum ConnectorType{EXCEL, RMDB}
	
	public List<String> showColumnNames();
	public MyTable Transform() throws ObjectAlreadyInitializedException;
	public void initialize(PageContext pagecontext) throws ServletException;
}
