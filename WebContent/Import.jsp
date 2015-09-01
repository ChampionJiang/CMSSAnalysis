<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Please select the picture to upload
<form id="upload" method="post" action="upload" enctype="multipart/form-data" name="form1">
<input type="file" name="file"/><br/>
<input type="submit" name="Import" value="upload"/>
</form>

<form id="subset" method="post" action="upload" name="form1">
<input type="submit" name="aggregate" value="aggregate"/>
</form>

</body>
</html>