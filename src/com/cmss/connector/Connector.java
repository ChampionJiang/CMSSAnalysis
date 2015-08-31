package com.cmss.connector;

import java.util.List;

import com.cmss.storage.RawTable;
import com.cmss.storage.ObjectAlreadyInitializedException;

public interface Connector {
	enum ConnectorType{RRESERVED, EXCEL, CSV, RMDB}
	
	public List<String> getColumnNames();
	public RawTable Transform() throws ObjectAlreadyInitializedException;
}
