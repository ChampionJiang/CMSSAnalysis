package com.cmss.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cmss.storage.Column;
import com.cmss.storage.RawTable;

@WebServlet(name = "serialize", urlPatterns = { "/serialize" })
public class SerializationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void saveTable(final String root, String name, final RawTable table) throws IOException {
		String saveurl = root+"caches/"+(name==null ? table.getName():name)+".cache";
		System.out.println(saveurl);
		FileOutputStream fos = new FileOutputStream(saveurl);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(table);
		
		oos.flush();
		oos.close();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)  throws IOException, ServletException {
		if (true) {
			this.jspVersion(req, resp);
		}
	}
	
	private void jspVersion(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();
		
		@SuppressWarnings("unchecked")
		Map<UUID, RawTable> tables = (Map<UUID, RawTable>) session.getAttribute("tables");
		
		
		UUID uuid = (UUID)req.getSession().getAttribute("uuid");
		
		RawTable table = null;
		if (tables.containsKey(uuid)) {
			table = tables.get(uuid);
		}	
		
		String name = req.getParameter("saveAs");
		
		if (table != null) {
			
			int columnCount = (int)session.getAttribute("cc");
			
			for (int i = 0; i < columnCount; i++) {
				String s = req.getParameter("sel"+i);
				
				Column.ColumnType type = null;
				if (s.equals("A"))
					type = Column.ColumnType.ATTRIBUTE;
				else if (s.equals("M"))
					type = Column.ColumnType.METRIC;
				
				table.setColumnType(i, type);
			}
			
			String root = req.getSession().getServletContext().getRealPath("/") ;
			this.saveTable(root, name, table);
			
			List<String> attrs = table.getAttributes();
			List<String> metrics = table.getMetrics();
			
			req.setAttribute("attrs", attrs);
			req.setAttribute("metrics", metrics);
			
			req.getRequestDispatcher( "Import.jsp").forward(req,resp); 
		}
	}
}
