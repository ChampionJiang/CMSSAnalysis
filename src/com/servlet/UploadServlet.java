package com.servlet;

import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Import.ExcelImport;
import com.connector.Connector;
import com.connector.ConnectorFactory;
import com.storage.MyTable;
import com.storage.ObjectAlreadyInitializedException;

@WebServlet(name="upload"
	, urlPatterns={"/upload"})
public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExcelImport mEI;
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		mEI = new ExcelImport();
		
	}
	
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException
	{
		Connector connector = ConnectorFactory.getConnector(Connector.ConnectorType.EXCEL);
		connector.initialize(req, resp);
		
		try {
			MyTable table = connector.Transform();
			//System.out.println(table.toJSON());
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("application/json;charset=utf-8");
			resp.setHeader("Cache-Control", "no-cache");
			PrintWriter out = resp.getWriter();
			out.println(table.toJSON());

			
			
			
		} catch (ObjectAlreadyInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
