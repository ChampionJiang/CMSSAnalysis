package com.cmss.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cmss.storage.RawTable;

@WebServlet(name = "deserialize", urlPatterns = { "/deserialize" })
public class DeserializationServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> getFiles(String root) {
		List<String> list = new ArrayList<String>();
		File dir = new File(root+"caches");
		
		File[] files = dir.listFiles();
		
		for (File f: files) {
			list.add(f.getName());
		}
		return list;
	}
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (true) {
			this.jspVersion(req, resp);
		}
	}
	
	public void jspVersion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String filename = req.getParameter("fls");
		
		//session.getAttribute(arg0)
		String root = session.getServletContext().getRealPath("/") ;
		
		if (filename != null) {
			System.out.println(filename);
			
			FileInputStream fis = new FileInputStream(root+"caches/"+filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			try {
				RawTable table = (RawTable) ois.readObject();
				
				req.setAttribute("attrs", table.getAttributes());
				req.setAttribute("metrics", table.getMetrics());
				//req.setAttribute("cns", table.getColumnNames());
				
				@SuppressWarnings("unchecked")
				Map<UUID, RawTable> tables = (Map<UUID, RawTable>) session.getAttribute("tables");
				
				if (tables == null) {
					tables = new HashMap<UUID, RawTable>();
					session.setAttribute("tables", tables);
				}
				
				UUID uuid = table.getUUID();
				tables.put(uuid, table);
				
				req.setAttribute("uuid", uuid);
				
				req.getRequestDispatcher( "Import.jsp").forward(req,resp); 
				req.setAttribute("table1", table.toJSON());
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ois.close();
		} else {
			
			req.setAttribute("files", this.getFiles(root));
			req.getRequestDispatcher( "Import.jsp").forward(req,resp);
		}
	}
	
}
