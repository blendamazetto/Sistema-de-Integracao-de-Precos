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
        <table class="table" align="center" border="0" cellpadding="0">
            <thead class="#87CEFA" align="center">
                <tr>
                    <th scope="col" align="left">CLASSIFICAÇÃO</th>
                    <th scope="col" align="center">DESCRIÇÃO</th>
                    <th scope="col" align="center">VALOR</th>
                    <th scope="col" align="center">LOJA</th>
                    <th scope="col" align="center">DATA</th>
                </tr>
            </thead>
            <tbody align="center">
                <c:forEach var="produto" items="${requestScope.lista_produtos}">
                    <tr>
                        <td align="center">
                            <span><c:out value="${produto.getClassificacaoAsString()}"/></span>
                        </td>                        
                        <td align="left">
                            <span><c:out value="${produto.descricao}"/></span>
                        </td>
                        <td align="center">
                            <span><c:out value="${produto.getPrecoAsString()}"/></span>
                        </td>
                        <td align="center">
                            <span><c:out value="${produto.nomeLoja}"/></span>
                        </td>                     
                        <td align="center">
                            <span><c:out value="${produto.dataCrawling}"/></span>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <%@include file="/view/include/scripts.jsp"%>
</body>
</html>
