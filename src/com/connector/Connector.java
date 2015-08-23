package com.connector;

import java.util.List;

import com.storage.MyTable;
import com.storage.ObjectAlreadyInitializedException;

public interface Connector {
	enum ConnectorType{RRESERVED, EXCEL, CSV, RMDB}
	
	public List<String> getColumnNames();
	public MyTable Transform() throws ObjectAlreadyInitializedException;
}
