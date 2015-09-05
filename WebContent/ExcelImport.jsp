<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.LinkedList, com.cmss.storage.*, com.cmss.connector.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
Connector connector = ConnectorFactory.createConnectorFromRequest((HttpServletRequest)pageContext.getRequest(), (HttpServletResponse)pageContext.getResponse());

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%

String s = (String)request.getAttribute("fff");

//RawTable table = connector.Transform();%>
<%=s
	
%>


</body>
</html>