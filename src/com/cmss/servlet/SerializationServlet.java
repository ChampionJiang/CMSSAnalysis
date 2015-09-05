package com.cmss.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cmss.storage.RawTable;

@WebServlet(name = "serialize", urlPatterns = { "/serialize" })
public class SerializationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void saveTable(final String root, String name, final RawTable table) throws IOException {
		String saveurl = root+"caches/"+(name==null ? table.getName():name)+".tbl";
		System.out.println(saveurl);
		FileOutputStream fos = new FileOutputStream(saveurl);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(table);
		
		oos.flush();
		oos.close();
	}
	
	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
			String root = req.getSession().getServletContext().getRealPath("/") ;
			this.saveTable(root, name, table);
		}
	}
}
