<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.Import.*, java.util.LinkedList, com.Storage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
ExcelImport ei = new ExcelImport();
ei.initialize(pageContext);

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
int sheets = ei.getNumOfSheets();
for (int i = 0; i < sheets; i++)
{%>
<%ei.setCurrentSheet(i);
MyTable table = new MyTable();
ei.Transform(table);
table.Print();%>
	<%-- <table border='1' cellpadding='3' cellspacing='0'>
	<%ei.setCurrentSheet(i);
	
	int row = ei.getNumOfRows();

	for (int r = 0; r < row; r++)
	{
		LinkedList<String> list = ei.getRow(r);
		%>
		<tr>
		<% for (String c: list){
		%>
		<td><%=c %></td>
		<%} %>
		</tr>
	<%}%>
	
	<%--</table> --%>
	<hr/>
<%}
%>

</body>
</html>