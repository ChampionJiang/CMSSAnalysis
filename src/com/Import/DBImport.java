package com.Import;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.connector.Connector;
import com.storage.MyTable;

public class DBImport implements Connector{

	
	
	@Override
	public MyTable Transform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getColumnNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(PageContext pagecontext) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
