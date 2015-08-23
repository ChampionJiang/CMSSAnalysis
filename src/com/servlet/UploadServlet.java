package com.servlet;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.Import.ExcelImport;
import com.connector.Connector;
import com.connector.ConnectorFactory;
import com.jsp.smart.SmartUploadException;
import com.storage.MyTable;
import com.storage.ObjectAlreadyInitializedException;

@WebServlet(name = "upload", urlPatterns = { "/upload" })
public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ExcelImport mEI;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//mEI = new ExcelImport(null);

	}

	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
		try {
			Connector connector = ConnectorFactory.createConnectorFromRequest(req, resp);

			MyTable table = connector.Transform();
			// System.out.println(table.toJSON());
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("application/json;charset=utf-8");
			resp.setHeader("Cache-Control", "no-cache");
			PrintWriter out = resp.getWriter();
			out.println(table.toJSON());

			String saveurl = req.getSession().getServletContext().getRealPath("/") + "app/asset/test/test.json";

			FileOutputStream writerStream = new java.io.FileOutputStream(saveurl);

			BufferedWriter writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(writerStream, "UTF-8"));

			writer.write(table.toJSON());

			System.out.println(saveurl);
			writer.flush();
			writer.close();
			writerStream.close();

		} catch (ObjectAlreadyInitializedException | SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
