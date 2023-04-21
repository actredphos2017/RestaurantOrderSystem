<%--
  Created by IntelliJ IDEA.
  User: actre
  Date: 4/21/2023
  Time: 11:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
  String msg = request.getParameter("msg");
  response.getWriter().println("<html><body>");
  response.getWriter().println("<h1>" + msg + "</h1>");
  response.getWriter().println("</body></html>");
%>
</body>
</html>
