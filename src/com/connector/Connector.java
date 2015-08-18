package com.connector;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.storage.MyTable;
import com.storage.ObjectAlreadyInitializedException;

public interface Connector {
	enum ConnectorType{EXCEL, RMDB}
	
	public List<String> getColumnNames();
	public MyTable Transform() throws ObjectAlreadyInitializedException;
	public void initialize(PageContext pagecontext) throws ServletException;
	public void initialize(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
