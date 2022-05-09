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
                    <th scope="col" align="center">DESCRIÇÃO</th>
                    <th scope="col" align="center">VALOR</th>
                    <th scope="col" align="left">CLASSIFICAÇÃO</th>
                    <th scope="col" align="center">LOJA</th>
                    <th scope="col" align="center">DATA</th>
                    <th scope="col" align="center">URL</th>
                </tr>
            </thead>
            <tbody align="center">
                    <tr>                      
                        <td align="left">
                            <%=request.getParameter("descricao")%>
                        </td>
                        <td align="center">
                            <%=request.getParameter("valor")%>
                        </td>
                        <td align="center">
                            <%=request.getParameter("classificacao")%>
                        </td>  
                        <td align="center">
                            <%=request.getParameter("loja")%>
                        </td>                     
                        <td align="center">
                            <%=request.getParameter("data")%>
                        </td>
                        <td align="center">
                            <a href="<%=request.getParameter("url")%>">Link</a>
                        </td>
                    </tr>
            </tbody>
        </table>
    </div>
    <br>
    <%@include file="/view/include/scripts.jsp"%>
</body>
</html>