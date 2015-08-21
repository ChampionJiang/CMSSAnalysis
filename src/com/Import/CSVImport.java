package com.Import;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.connector.Connector;
import com.storage.MyTable;
import com.storage.ObjectAlreadyInitializedException;

public class CSVImport implements Connector {

	@Override
	public List<String> getColumnNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyTable Transform() throws ObjectAlreadyInitializedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(PageContext pagecontext) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
