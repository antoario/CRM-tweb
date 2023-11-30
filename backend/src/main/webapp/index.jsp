<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<body>
<h1>Hello Jakarta EE 9 with Java 17 on Azure</h1>
<p>This is a JSP page</p>
<a href="./helloservlet">Hit the /hello Servlet</a>

<h2>Results</h2>

<sql:setDataSource var="myDataSource" dataSource="jdbc/postgres" />
<sql:query dataSource="jdbc/postgres" var="testQuery">
    SELECT 1
</sql:query>

<c:forEach var="row" items="${rs.rows}">
    connesso al server
</c:forEach>

</body>

</html>
