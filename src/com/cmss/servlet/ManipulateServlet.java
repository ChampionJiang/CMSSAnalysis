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

	private void jspVersion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
//		String[] s = req.getParameterValues("ttttt");
//		
//		for (String ss:s)System.out.println(ss);
//		
//		if (1>0)
//		return;
		
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
			
			String[] attributes = req.getParameterValues("ats");
			String[] metrics = req.getParameterValues("mts");
				
			Integer[] attrs = new Integer[attributes == null ? 0 :attributes.length];
			for (int i = 0; i < attrs.length; i++) {
				attrs[i] = table.findColumnByName(attributes[i]);
			}
			
			Integer []mts = new Integer[metrics == null ? 0 :metrics.length];
			for (int i = 0; i < mts.length; i++) {
				mts[i] = table.findColumnByName(metrics[i]);
			}
			
//			
//			Integer []metrics = new Integer[m.size()];
//			metrics = m.toArray(metrics);
//			
			result = se.subset(table, attrs, mts);
//			
			req.setAttribute("table1", table.toJSON());
			req.setAttribute("table2", result.toJSON());
			req.getRequestDispatcher( "Import.jsp").forward(req,resp); 
		} else {
			req.setAttribute("table1", "original talbe is not available");
		}
	}
	
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (true) {
			this.jspVersion(req, resp);
		}
		
	}
}
