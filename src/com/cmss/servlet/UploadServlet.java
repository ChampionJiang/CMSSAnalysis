package com.cmss.servlet;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cmss.connector.Connector;
import com.cmss.connector.ConnectorFactory;
import com.cmss.storage.RawTable;
import com.cmss.storage.ObjectAlreadyInitializedException;
import com.jsp.smart.SmartUploadException;

@WebServlet(name = "upload", urlPatterns = { "/upload" })
public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ExcelImport mEI;
	
	//private static Map<UUID, RawTable> tables = new HashMap<UUID, RawTable>();

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//mEI = new ExcelImport(null);
	}

	private void saveJSON(String root, RawTable table) throws IOException {
		
		String saveurl = root+"app/asset/test/"+table.getUUID().toString()+ ".json";
		
		FileOutputStream writerStream = new java.io.FileOutputStream(saveurl);

		BufferedWriter writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(writerStream, "UTF-8"));

		writer.write(table.toJSON());

		System.out.println(saveurl);
		writer.flush();
		writer.close();
		writerStream.close();
	}
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
		if (true) {
			this.jspVersion(req, resp);
		}
	}
	
	private void jspVersion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
		try {			
				HttpSession session = req.getSession();
				@SuppressWarnings("unchecked")
				Map<UUID, RawTable> tables = (Map<UUID, RawTable>) session.getAttribute("tables");
				
				if (tables == null) {
					tables = new HashMap<UUID, RawTable>();
					session.setAttribute("tables", tables);
				}
				
				Connector connector = ConnectorFactory.createConnectorFromRequest(req, resp);

				RawTable table = connector.Transform();
				
				req.setAttribute("cns", table.getColumnNames());
				
				UUID uuid = table.getUUID();
				tables.put(uuid, table);
				
				req.setAttribute("uuid", uuid);
				
				req.getRequestDispatcher( "Import.jsp").forward(req,resp); 
				req.setAttribute("table1", table.toJSON());
				String root = req.getSession().getServletContext().getRealPath("/") ;
						
				this.saveJSON(root, table);

		} catch (ObjectAlreadyInitializedException | SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
