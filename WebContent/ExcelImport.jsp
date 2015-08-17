<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.Import.*, java.util.LinkedList, com.storage.*, com.connector.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
Connector connector = ConnectorFactory.getConnector(Connector.ConnectorType.EXCEL);

connector.initialize(pageContext);

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%

MyTable table = connector.Transform();%>
<%=table.toJSON()
	
%>

</body>
</html>