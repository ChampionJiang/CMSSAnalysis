package com.cmss.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = req.getSession();
		//session.getAttribute(arg0)
		String root = session.getServletContext().getRealPath("/") ;
		req.setAttribute("files", this.getFiles(root));
		req.getRequestDispatcher( "Import.jsp").forward(req,resp);
	}
	
}
