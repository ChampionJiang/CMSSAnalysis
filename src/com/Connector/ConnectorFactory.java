package com.Connector;

import com.Import.DBImport;
import com.Import.ExcelImport;

public class ConnectorFactory {
	public static Connector getConnector(Connector.ConnectorType type)
	{
		switch(type)
		{
		case EXCEL:
			return new ExcelImport();
		case RMDB:
			return new DBImport();
		default:
			break;
		}
		return null;
	}
}
