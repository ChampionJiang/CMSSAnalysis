package com.cmss.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cmss.engine.SubsetEngine;
import com.cmss.storage.RawTable;

@WebServlet(name = "manipulate", urlPatterns = { "/manipulate" })
public class ManipulateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
				
		@SuppressWarnings("unchecked")
		Map<UUID, RawTable> tables = (Map<UUID, RawTable>) session.getAttribute("tables");
		
		UUID uuid = (UUID)req.getSession().getAttribute("uuid");
		
		RawTable table = null;
		if (tables.containsKey(uuid)) {
			table = tables.get(uuid);
		}
		
		RawTable result = null;
		if (table != null) {
			
			SubsetEngine se = new SubsetEngine();
			
			int columnCount = (int)session.getAttribute("cc");
			
			ArrayList<Integer> a = new ArrayList<Integer>();
			ArrayList<Integer> m = new ArrayList<Integer>();
			for (int i = 0; i < columnCount; i++) {
				String s = req.getParameter("sel"+i);
				
				if (s.equals("A")) {
					a.add(i);
				}
				
				if (s.equals("M")) {
					m.add(i);
				}
			}
			Integer[] attrs = new Integer[a.size()];
			attrs = a.toArray(attrs);
			
			Integer []metrics = new Integer[m.size()];
			metrics = m.toArray(metrics);
			
			result = se.subset(table, attrs, metrics);
			
			req.setAttribute("table1", table.toJSON());
			req.setAttribute("table2", result.toJSON());
			req.getRequestDispatcher( "Import.jsp").forward(req,resp); 
		} else {
			req.setAttribute("table1", "original talbe is not available");
		}
		
	}
}
