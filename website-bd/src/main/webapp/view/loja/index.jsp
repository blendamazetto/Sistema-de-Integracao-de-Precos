<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/include/header.jsp"  %>
    <title>Notebooks</title>
</head>
<body>  
    <%@include file="/view/include/navbar.jsp" %>
    <br>
    <div class="container">
        <table class="table" align="center" border="0" cellpadding="20">
            <thead class="#87CEFA" align="center">
                <tr>
                    <th scope="col" align="center">ID</th>
                    <th scope="col" align="center">MODELO</th>
                    <th scope="col" align="left">DESCRIÇÃO</th>
                    <th scope="col" align="center">MARCA</th>
                </tr>
            </thead>
            <tbody align="center">
                <c:forEach var="notebook" items="${requestScope.lista_notebook}">
                    <tr>
                        <td align="center">
                            <span><c:out value="${notebook.id_notebook}"/></span>
                        </td>
                        <td align="center">
                            <span><c:out value="${notebook.modelo}"/></span>
                        </td>
                        <td align="left">
                            <span><c:out value="${notebook.descricao}"/></span>
                        </td>
                        <td align="center">
                            <span><c:out value="${notebook.marca}"/></span>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <%@include file="/view/include/scripts.jsp"%>
</body>
</html>
