<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.UUID, java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Please select the file to upload
<form id="upload" method="post" action="upload" enctype="multipart/form-data" name="form1">
<input type="file" name="file"/><br/>
<input type="submit" name="Import" value="upload"/>

</form>

<form id="save" action="serialize" name="form2">
<%

List<String> columnNames = (List<String>) request.getAttribute("cns");
if (columnNames != null) {
	%>
	Columns:<br/>
	<%for (int i = 0; i < columnNames.size(); i++) { 
		String s = columnNames.get(i);%>
	<select id = <%="sel"+i %> name = <%="sel"+i %>>
	<option value="A">Attribute</option>
	<option value="M">Metric</option>
	</select><%=s %><br/>	
	<%}
	
	session.setAttribute("cc", columnNames.size());
}
%>
Name:<input type="text" name="saveAs">
<input type="submit" name="Save" value="publish"/>
</form>

<form id="cac" action="deserialize" name="form3">
<input type="submit" name="Check Available Caches" value="Check Available Caches"/>
</form>

<form id="load" action="deserialize" name = "form4">
<%
List<String> fileNames = (List<String>) request.getAttribute("files");%>
existing files:<br/>
<% 
	if (fileNames != null) {
		for (int i = 0; i < fileNames.size(); i++) {
		%>
		<input type = "radio" name="fls" id=<%="file"+i %> value=<%=fileNames.get(i) %>><%=fileNames.get(i) %><br/>
<%		}
	}
%>
<input type="submit" name="load" value="load"/>
</form>

<form id="subset" method="post" action="manipulate" name="form5">
<%
List<String> attrs = (List<String>)request.getAttribute("attrs");
List<String> metrics = (List<String>)request.getAttribute("metrics");

if (attrs != null && attrs.size() > 0) {
	%>
	attributes:<br/>
	
	<%
		for (String s: attrs) {
			%>	
			<input type = "checkbox" name="ats" id=<%=s %> value=<%=s %>><%=s %><br/>		
			<%
		}
	%>
	
	<%
}

if (metrics != null && metrics.size() > 0) {
	%>
	metrics:<br/>
	
	<%
		for (String s: metrics) {
			%>	
			<input type = "checkbox" name="mts" id=<%=s %> value=<%="\""+s+"\"" %>><%=s %><br/>		
			<%
		}
	%>
	
	<%
}

UUID uuid = (UUID)request.getAttribute("uuid");
if (uuid != null) {
	out.println("table id: " + uuid.toString()+"<br/>");
	session.setAttribute("uuid", uuid);
}
	

String json1 = (String) request.getAttribute("table1");
if (json1 != null)
{
	out.println("original table:<br/>");
	out.println(json1+"<br/>");
}
	


String json2 = (String) request.getAttribute("table2");
if (json2 != null) {
	out.println("after aggregate:<br/>");
	out.println(json2+"<br/>");
}


%>
<input type="submit" name="aggregate" value="aggregate"/>
</form>


</body>
</html>