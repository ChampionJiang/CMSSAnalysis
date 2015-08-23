package com.connector;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Import.CSVImport;
//import com.Import.DBImport;
import com.Import.ExcelImport;
import com.connector.Connector.ConnectorType;
import com.jsp.smart.File;
import com.jsp.smart.SmartUpload;
import com.jsp.smart.SmartUploadException;

public class ConnectorFactory {
	public static Connector createConnectorFromRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SmartUploadException
	{
		SmartUpload su = new SmartUpload();
		su.init(req, resp);
		su.setAllowedFilesList("xls,xlsx,csv");
		su.upload();
		
		// do we support upload multiple files at the same time?
		File file = su.getFiles().getFile(0);
		
		String fileExt = file.getFileExt();
		
		Connector.ConnectorType type = ConnectorType.RRESERVED;
		if (fileExt.equals("xls") || fileExt.equals("xlsx")) {
			type = ConnectorType.EXCEL;
		}
		else if (fileExt.equals("csv")){
			type = ConnectorType.CSV;
		}
		
		return getConnector(type, file);
	}
	
	private static Connector getConnector(Connector.ConnectorType type, File file) throws IOException
	{
		switch(type)
		{
		case EXCEL:
			return new ExcelImport(file);
		case CSV:
			return new CSVImport(file);
			
		// should handle DB in DB connector
		//case RMDB:
		//	return new DBImport();
		default:
			break;
		}
		return null;
	}
}
